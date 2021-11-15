package it.salvatorevirzi.spring.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.salvatorevirzi.spring.common.Common;
import it.salvatorevirzi.spring.model.Comune;
import it.salvatorevirzi.spring.repository.ComuneRepository;
import it.salvatorevirzi.spring.repository.ProvinciaRepository;

@Service
public class ComuneService {
	@Autowired
	ComuneRepository comuneRepository;

	@Autowired
	ProvinciaRepository provinciaRepository;

	Common common = new Common();

	public void saveComune() {
		if(comuneRepository.findAll().isEmpty()) {
		for (Comune c : common.getListaComune()) {
			c.setProvincia(provinciaRepository.getByNome(c.getProvincia().getNome()));
			comuneRepository.save(c);
		}}
	}
	
	
	
	public void saveNewComune(Comune comune) {	
			comuneRepository.save(comune);
	}
	
	public ResponseEntity<?> deleteComune(Long id) {
		Optional<Comune> comune = comuneRepository.findById(id);
		if(comune.isPresent()) {
		comuneRepository.deleteById(id);
		return new ResponseEntity<>("Comune eliminato", HttpStatus.OK);
	} else {
		return new ResponseEntity<>("Comune non trovato", HttpStatus.BAD_REQUEST);
		}
	}	
	
	
	public void updateComune(Comune comune) {
		Comune c = comuneRepository.getById(comune.getId());
		if(c == null)
			throw new EntityNotFoundException("Il comune con id " + comune.getId() + " non esiste");
		else 
			comuneRepository.save(c);
	}
	
	
	public Page<Comune> findAllComune(Pageable p){
		return comuneRepository.findAll(p);
	}
	
	public Page<Comune> findComuneNome(String nome, Pageable p){
		return comuneRepository.findByNome(nome, p);
	}
}
