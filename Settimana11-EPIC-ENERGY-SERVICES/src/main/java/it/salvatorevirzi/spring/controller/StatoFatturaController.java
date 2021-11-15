package it.salvatorevirzi.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.salvatorevirzi.spring.model.StatoFattura;
import it.salvatorevirzi.spring.service.StatoFatturaService;

@RestController
@RequestMapping("/statofattura")
public class StatoFatturaController {
	@Autowired
	private StatoFatturaService statoFatturaService;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody StatoFattura statoFattura) {
		statoFatturaService.save(statoFattura);
		return new ResponseEntity<>(statoFattura, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody StatoFattura statoFattura) {
		statoFatturaService.updateStatoFattura(statoFattura);
		return new ResponseEntity<>(statoFattura, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> elimina(@PathVariable(required = true)Long id) {
		return statoFatturaService.deleteStatoFattura(id);
	}
	
	 @GetMapping("/findby-all")
		public ResponseEntity<Page<StatoFattura>> findStatoFatturaByAll (@RequestParam (required = false, defaultValue = "0") int pageNumber,
				@RequestParam(required = false, defaultValue = "10") int pageSize){
		  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
	    	Page<StatoFattura> stato = statoFatturaService.findAllStatoFattura(pageInfo);
	    	if(stato.hasContent()) {
	        	return new ResponseEntity<>(stato, HttpStatus.OK);
	    	} else {
	    		 return new ResponseEntity<>(stato, HttpStatus.NOT_FOUND);
	    		}}
	
	 @GetMapping("/findby-nome")
		public ResponseEntity<Page<StatoFattura>> findClienteByNomeContatto(@RequestParam String nome, @RequestParam (required = false, defaultValue = "0") int pageNumber,
				@RequestParam(required = false, defaultValue = "10") int pageSize){
		  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
	    	Page<StatoFattura> stato = statoFatturaService.findStatoFatturaNome(nome, pageInfo);
			return new ResponseEntity<>(stato, HttpStatus.OK);
		}
}
