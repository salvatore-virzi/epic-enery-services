package it.salvatorevirzi.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.salvatorevirzi.spring.model.Provincia;

public interface ProvinciaRepository  extends JpaRepository<Provincia, Long> {
	
	Provincia getByNome(String nome);
	
	Page<Provincia> findByNome(String nome, Pageable p);

}
