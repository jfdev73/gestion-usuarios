package com.miranda.gestionusuarios.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miranda.gestionusuarios.entity.Cliente;
import com.miranda.gestionusuarios.service.GenericService;
import com.miranda.gestionusuarios.service.IUploadFileService;
import com.miranda.gestionusuarios.util.PageRender;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/clientes")
@SessionAttributes("cliente")
public class ClienteController {
	
	
	private GenericService<Cliente, Long> clienteService;
	
	private IUploadFileService uploadFileService;
	
	
	@Value("${uploads.paths}")
	private String rootPath;
	
	public ClienteController( GenericService<Cliente, Long> clienteService, IUploadFileService uploadFileService) {
		this.clienteService = clienteService;
		this.uploadFileService = uploadFileService;
	}
	
	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Optional<Cliente> cliente = clienteService.findById(id);
		
		if(!cliente.isPresent()) {
			flash.addFlashAttribute("error","El cliente no existe");
			return "redirect:/clientes/listar";
		}
		model.addAttribute("cliente", cliente.get());
		model.addAttribute("title", "Detalle del cliente: "+cliente.get().getNombre());
		
		return "clientes/detalle";
	}
	
	@GetMapping("/listar")
	public String listar( @RequestParam(defaultValue = "0") int page ,Model model) {
		
		Pageable pageRequest = PageRequest.of(page,5);
		Page<Cliente> clientesPage = clienteService.getAll(pageRequest);
		
		//List<Cliente> clientes = clienteService.getAll();
		PageRender<Cliente> pageRender = new PageRender<>("/clientes/listar", clientesPage);
		model.addAttribute("title", "Listado de Clientes");
		model.addAttribute("clientes", clientesPage);
		model.addAttribute("page", pageRender);
		
		return "clientes/list";
	}
	
	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("title","Formulario de Clientes");
		model.addAttribute("value", "Crear Cliente");
		model.addAttribute("enviado", false);
		return "clientes/form";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id ,Model model,RedirectAttributes flash) {
		Optional<Cliente> cliente = null;
		if(id > 0) {
			cliente = clienteService.findById(id);
			if(!cliente.isPresent()) {
				flash.addFlashAttribute("error", "El cliente no existe");
				return "redirect:/clientes/listar";
				
			}
		}else {
			flash.addFlashAttribute("error", "El id es invalido");
			return "redirect:/clientes/listar";
			
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("title","Editar Cliente");
		model.addAttribute("value", "Editar Cliente");
		model.addAttribute("enviado", false);
		return "clientes/form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id ,Model model,RedirectAttributes flash) {
		
		if(id > 0) {
			Optional<Cliente> cliente = clienteService.findById(id);
			clienteService.delete(id);
			if(uploadFileService.delete(cliente.get().getFoto()))
				flash.addFlashAttribute("info", "foto eliminado con exito");
			
			flash.addFlashAttribute("success", "Cliente eliminado con exito");
		}
		
		return "redirect:/clientes/listar";
	}
	
	
	
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto,
			RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("title","Formulario de Clientes");
			model.addAttribute("value", "Crear Cliente");
			model.addAttribute("enviado", true);
			return "clientes/form";
		}
		if(!foto.isEmpty()) {
			
			if(cliente.getId() !=null && cliente.getId() > 0 && cliente.getFoto() !=null 
					&& !cliente.getFoto().isBlank()) {
				uploadFileService.delete(cliente.getFoto());
				
			}
			String uniqueFileName = null;
			
			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			if(cliente.getId() == null)
				flash.addFlashAttribute("info","Has subido correctamente: '"+foto.getOriginalFilename()+"'");
			cliente.setFoto(uniqueFileName);
			
			
		}
		clienteService.save(cliente);
		String msjFlash = (cliente.getId() ==null) ? "Cliente agregado con exito" : "Cliente editado con exito"; 
		status.setComplete();
		flash.addFlashAttribute("success", msjFlash);
		
		return "redirect:/clientes/listar";
	}
	
	

}
