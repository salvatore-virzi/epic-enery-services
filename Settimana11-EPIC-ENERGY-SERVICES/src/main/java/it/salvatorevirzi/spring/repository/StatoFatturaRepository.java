package it.salvatorevirzi.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.salvatorevirzi.spring.model.StatoFattura;

public interface StatoFatturaRepository  extends JpaRepository<StatoFattura, Long>{
	
	Page<StatoFattura> findByNome(String nome, Pageable pageble);
}
