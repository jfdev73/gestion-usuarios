package com.miranda.gestionusuarios.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clientes")
@Setter
@Getter
@ToString
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "nombre",length = 30)
	private String nombre;
	
	@NotBlank
	@Column(name = "apellido_paterno",length = 30)
	private String apellidoPaterno;
	@NotBlank
	@Column(name = "apellido_materno",length = 30)
	private String apellidoMaterno;
	
	@NotBlank
	@Email
	@Column(name = "email",length = 50)
	private String email;
	
	@Column(name = "create_at")
	private LocalDateTime createAt;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	
	@Column(name = "foto")
	private String foto;
	
	@OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Factura> facturas;
	
	
	public Cliente() {
		facturas = new ArrayList<>();
	}
	
	public void addFactura(Factura factura) {
		facturas.add(factura);
		
	}
	
	@PrePersist
	public void fechaRegistro() {
		createAt = LocalDateTime.now();
	}
	
	private static final long serialVersionUID = 1L;

}
