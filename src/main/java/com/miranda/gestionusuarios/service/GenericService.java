package com.miranda.gestionusuarios.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miranda.gestionusuarios.entity.Cliente;

public interface GenericService <T, ID> {
	
	Optional<T> findById(ID id);
	
	Page<Cliente> getAll(Pageable pageable);
	
	List<T> getAll();
	
	@Transactional
	void delete(ID id);
	
	@Transactional
	Optional<T> save(T t);

}
