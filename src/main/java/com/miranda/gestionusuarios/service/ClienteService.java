package com.miranda.gestionusuarios.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miranda.gestionusuarios.entity.Cliente;
import com.miranda.gestionusuarios.repository.ClientreRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ClienteService implements GenericService<Cliente, Long> {
	
	private ClientreRepository clientreRepository;

	@Override
	public Optional<Cliente> findById(Long id) {
		return clientreRepository.findById(id);
	}

	@Override
	public List<Cliente> getAll() {
		return clientreRepository.findAllByOrderByCreateAtAsc();
	}
	

	@Override
	public void delete(Long id) {
		clientreRepository.deleteById(id);
	
		
	}
	
	@Override
	public Optional<Cliente> save(Cliente cliente) {
		return Optional.ofNullable(clientreRepository.save(cliente));
	}

	@Override
	public Page<Cliente> getAll(Pageable pageable) {
		return clientreRepository.findAllByOrderByCreateAtAsc(pageable);
	}

}
