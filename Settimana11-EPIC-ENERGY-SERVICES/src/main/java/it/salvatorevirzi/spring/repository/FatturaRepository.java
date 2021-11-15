package it.salvatorevirzi.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.salvatorevirzi.spring.model.Cliente;
import it.salvatorevirzi.spring.model.Fattura;

public interface FatturaRepository  extends JpaRepository<Fattura, Long> {

	Page<Fattura> findById(Long id, Pageable p);
	
	Page<Fattura> findByClienteId(Long id, Pageable p);
	
	Page<Fattura> findByStatoId(Long id, Pageable p);
	
	Page<Fattura> findByData(LocalDate data, Pageable p);

	Page<Fattura> findByAnno(Integer anno, Pageable p);

	Page<Fattura> findByImportoBetween(Double importoMinimo, Double importoMassimo, Pageable p);
		
	Page<Fattura> findByOrderByDataAsc(Pageable p);
	
	Page<Fattura> findByOrderByAnnoAsc(Pageable p);
	
	Page<Fattura> findByOrderByImportoAsc(Pageable p);

	List<Fattura> findAllById(Long id);

	List<Fattura> findAllByCliente(Cliente cliente);
}
