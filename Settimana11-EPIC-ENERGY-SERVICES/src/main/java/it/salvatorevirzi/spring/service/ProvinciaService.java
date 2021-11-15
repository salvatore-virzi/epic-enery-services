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
import it.salvatorevirzi.spring.model.Provincia;
import it.salvatorevirzi.spring.repository.ProvinciaRepository;

@Service
public class ProvinciaService {
	@Autowired
	ProvinciaRepository provinciaRepository;

	Common common = new Common();

	public void saveProvincia() {
		if(provinciaRepository.findAll().isEmpty()) {
		for (Provincia p : common.getListaProvincia()) {
			provinciaRepository.save(p);
		}}
	}
	
	
	public void saveNewProvincia(Provincia provincia) {
		provinciaRepository.save(provincia);
	}
	
	public ResponseEntity<?> deleteProvincia(Long id) {
		Optional<Provincia> provincia = provinciaRepository.findById(id);
		if(provincia.isPresent()) {
		provinciaRepository.deleteById(id);
		return new ResponseEntity<>("Provincia eliminata ", HttpStatus.OK);
	} else {
		return new ResponseEntity<>("Provincia non trovata ", HttpStatus.BAD_REQUEST);
		}
	}	
	
	public void updateProvincia(Provincia provincia) {
		Provincia p = provinciaRepository.getById(provincia.getId());
		if(p == null)
			throw new EntityNotFoundException("Il comune con id " + provincia.getId() + " non esiste");
		else 
			provinciaRepository.save(p);
	}
	
	public Page<Provincia> findAllProvincia(Pageable p){
		return provinciaRepository.findAll(p);
	}
	
	public Page<Provincia> findProvincia(String nome, Pageable p){
		return provinciaRepository.findByNome(nome, p);
	}
	
}
