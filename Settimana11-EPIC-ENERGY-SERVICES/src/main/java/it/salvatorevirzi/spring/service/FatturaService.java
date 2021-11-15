package it.salvatorevirzi.spring.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.salvatorevirzi.spring.model.Fattura;
import it.salvatorevirzi.spring.repository.FatturaRepository;

@Service
public class FatturaService {
	@Autowired
	FatturaRepository fatturaRepository;

	public void save(Fattura fattura) {
		fatturaRepository.save(fattura);
	}

	public ResponseEntity<?> delete(Long id) {
		Optional<Fattura> fattura = fatturaRepository.findById(id);
		if (fattura.isPresent()) {
			fatturaRepository.deleteById(id);
			return new ResponseEntity<>("Fattura eliminata", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Fattura non trovata", HttpStatus.BAD_REQUEST);
		}
	}

	public void update(Fattura fattura) {
		Fattura f = fatturaRepository.getById(fattura.getId());
		if (f == null)
			throw new EntityNotFoundException("La Fattura con id " + fattura.getId() + " non esiste");
		else
			fatturaRepository.save(f);
	}

	public Page<Fattura> findAllFattura(Pageable p) {
		return fatturaRepository.findAll(p);
	}

	public Page<Fattura> findFatturaId(Long id, Pageable p) {
		return fatturaRepository.findById(id, p);
	}

	public Page<Fattura> findFattureByCliente(Long id, Pageable p) {
		return fatturaRepository.findByClienteId(id, p);
	}

	public Page<Fattura> findFatturaByData(LocalDate date, Pageable p) {
		return fatturaRepository.findByData(date, p);
	}

	public Page<Fattura> findByStato(Long id, Pageable p) {
		return fatturaRepository.findByStatoId(id, p);
	}

	public Page<Fattura> findAnno(Integer anno, Pageable p) {
		return fatturaRepository.findByAnno(anno, p);
	}

	public Page<Fattura> findByImporto(Double importoMinimo, Double importoMassimo, Pageable p) {
		return fatturaRepository.findByImportoBetween(importoMinimo, importoMassimo, p);
	}
	
	public Page<Fattura> orderByAnno(Pageable p){
		return fatturaRepository.findByOrderByAnnoAsc(p);
	}
	
	public Page<Fattura> orderByData(Pageable p){
		return fatturaRepository.findByOrderByDataAsc(p);
	}
	
	public Page<Fattura> orderByImporto(Pageable p){
		return fatturaRepository.findByOrderByImportoAsc(p);
	}

	public void updateFattura(long id, Fattura fattura) {
		Fattura f= fatturaRepository.getById(id);
		delete(id);
		fattura.setId(id);
		fattura.setCliente(f.getCliente());
		fattura.setStato(f.getStato());
		fatturaRepository.save(fattura);
	}
}
