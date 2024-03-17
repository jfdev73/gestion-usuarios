package com.miranda.gestionusuarios.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "precio")
	private Double precio;
	
	@Column(name = "create_at")
	private LocalDateTime createAt;


	@PrePersist
	public void fechaRegistro() {
		createAt = LocalDateTime.now();
	}
}
