package com.miranda.gestionusuarios.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.miranda.gestionusuarios.entity.Cliente;

public interface ClientreRepository extends JpaRepository<Cliente, Long>{
	
	List<Cliente> findAllByOrderByCreateAtAsc();
	Page<Cliente> findAllByOrderByCreateAtAsc(Pageable pageable);

}
