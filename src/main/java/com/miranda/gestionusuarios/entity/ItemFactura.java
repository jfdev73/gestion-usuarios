package com.miranda.gestionusuarios.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "factuas_items")
public class ItemFactura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Producto producto;
	
	
	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}

}
