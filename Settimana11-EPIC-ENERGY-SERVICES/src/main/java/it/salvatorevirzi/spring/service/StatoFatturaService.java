package it.salvatorevirzi.spring.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.salvatorevirzi.spring.model.StatoFattura;
import it.salvatorevirzi.spring.repository.StatoFatturaRepository;

@Service
public class StatoFatturaService {
	@Autowired
	StatoFatturaRepository statoFatturaRepository;
	
	public void save(StatoFattura statoFattura) {	
		statoFatturaRepository.save(statoFattura);
}
	
	public ResponseEntity<?> deleteStatoFattura(Long id) {
		Optional<StatoFattura> stato = statoFatturaRepository.findById(id);
		if(stato.isPresent()) {
		statoFatturaRepository.deleteById(id);
		return new ResponseEntity<>("StatoFattura eliminato", HttpStatus.OK);
	} else {
		return new ResponseEntity<>("StatoFattura non trovato", HttpStatus.NOT_FOUND);
		}
	}	
	
	public void updateStatoFattura(StatoFattura statoFattura) {
		StatoFattura stato= statoFatturaRepository.getById(statoFattura.getId());
		if(stato == null)
			throw new EntityNotFoundException("Il comune con id " + statoFattura.getId() + " non esiste");
		else 
			statoFatturaRepository.save(stato);
	}
	
	public Page<StatoFattura> findAllStatoFattura(Pageable p){
		return statoFatturaRepository.findAll(p);
	}
	
	public Page<StatoFattura> findStatoFatturaNome(String nome, Pageable p){
		return statoFatturaRepository.findByNome(nome, p);
	}
}
