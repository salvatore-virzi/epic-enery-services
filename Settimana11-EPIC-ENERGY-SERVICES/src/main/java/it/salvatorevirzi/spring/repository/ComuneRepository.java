package it.salvatorevirzi.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.salvatorevirzi.spring.model.Comune;

public interface ComuneRepository extends JpaRepository<Comune, Long>{
	
	Page<Comune> findByNome(String nome, Pageable pageble);
}
