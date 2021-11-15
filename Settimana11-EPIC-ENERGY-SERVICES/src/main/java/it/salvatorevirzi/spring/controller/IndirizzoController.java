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

import it.salvatorevirzi.spring.model.Indirizzo;
import it.salvatorevirzi.spring.service.IndirizzoService;

@RestController
@RequestMapping("/indirizzo")
public class IndirizzoController {
	@Autowired
	private IndirizzoService indirizzoService;

	@PostMapping("/save")
	public ResponseEntity<?> saveIndirizzo(@RequestBody Indirizzo indirizzo) {
		indirizzoService.saveNewIndirizzo(indirizzo);
		return new ResponseEntity<>(indirizzo, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateIndirizzo(@RequestBody Indirizzo indirizzo) {
		indirizzoService.updateIndirizzo(indirizzo);
		return new ResponseEntity<>(indirizzo, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> eliminaComune(@PathVariable(required = true) Long id) {
		return indirizzoService.deleteIndirizzo(id);
	}

	@GetMapping("/findby-all")
	public ResponseEntity<Page<Indirizzo>> findIndirizzoByAll(@RequestParam (required = false, defaultValue = "0") int pageNumber,
			@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
		Page<Indirizzo> indirizzo = indirizzoService.findAllIndirizzo(pageInfo);
		if (indirizzo.hasContent()) {
			return new ResponseEntity<>(indirizzo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(indirizzo, HttpStatus.NOT_FOUND);
		}
	}
}