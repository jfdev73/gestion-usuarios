package com.miranda.gestionusuarios.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "factuas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "observacion")
	private String observacion;
	
	@Column(name = "create_at")
	private LocalDateTime createAt;
	
	 /*muchas facturas puede tener un cliente*/
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@OneToMany(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items;
	
	
	public Factura() {
		this.items = new ArrayList<>();
	}
	
	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
		
	}
	
	public Double getTotal() {
		Double total = 0.0;
		for (ItemFactura item : items) {
			total += item.calcularImporte();
		}
		
		return total;
	}

	
	@PrePersist
	public void fechaRegistro() {
		createAt = LocalDateTime.now();
	}
}
