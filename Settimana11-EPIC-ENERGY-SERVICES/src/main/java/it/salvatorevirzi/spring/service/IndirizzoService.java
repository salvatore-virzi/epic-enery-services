package it.salvatorevirzi.spring.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.salvatorevirzi.spring.model.Indirizzo;
import it.salvatorevirzi.spring.repository.IndirizzoRepository;

@Service
public class IndirizzoService {
	@Autowired
	IndirizzoRepository indirizzoRepository;
	
	public void saveNewIndirizzo(Indirizzo indirizzo) {	
		indirizzoRepository.save(indirizzo);
}
	
	public ResponseEntity<?> deleteIndirizzo(Long id) {
		Optional<Indirizzo> indirizzo = indirizzoRepository.findById(id);
		if(indirizzo.isPresent()) {
		indirizzoRepository.deleteById(id);
		return new ResponseEntity<>("Indirizzo eliminato", HttpStatus.OK);
	} else {
		return new ResponseEntity<>("Indirizzo non trovato", HttpStatus.BAD_REQUEST);
		}
	}	
	
	public void updateIndirizzo(Indirizzo indirizzo) {
		Indirizzo i = indirizzoRepository.getById(indirizzo.getId());
		if(i == null)
			throw new EntityNotFoundException("Il indirizzo con id " + indirizzo.getId() + " non esiste");
		else 
			indirizzoRepository.save(indirizzo);
	}
	
	public Page<Indirizzo> findAllIndirizzo(Pageable p){
		return indirizzoRepository.findAll(p);
	}
	
	
}
