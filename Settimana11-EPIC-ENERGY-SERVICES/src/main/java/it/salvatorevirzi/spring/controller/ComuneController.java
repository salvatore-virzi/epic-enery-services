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

import it.salvatorevirzi.spring.model.Comune;
import it.salvatorevirzi.spring.service.ComuneService;

@RestController
@RequestMapping("/comune")
public class ComuneController {
	@Autowired
	private ComuneService comuneService;
	
	@GetMapping("/saveallcomuni")
	public ResponseEntity<?> saveAllComune() {
		comuneService.saveComune();
		return new ResponseEntity<>("Tutti i comuni sono stati caricati correttamente", HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveComune(@RequestBody Comune comune) {
		comuneService.saveNewComune(comune);
		return new ResponseEntity<>(comune, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateComune(@RequestBody Comune comune) {
		comuneService.updateComune(comune);
		return new ResponseEntity<>(comune, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> eliminaComune(@PathVariable(required = true)Long id) {
		return comuneService.deleteComune(id);
	}
	
	  @GetMapping("/findby-all")
		public ResponseEntity<Page<Comune>> findComuneByAll (@RequestParam (required = false, defaultValue = "0") int pageNumber, //
				@RequestParam(required = false, defaultValue = "10") int pageSize){
		  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
	    	Page<Comune> comune = comuneService.findAllComune(pageInfo);
	    	if(comune.hasContent()) {
	        	return new ResponseEntity<>(comune, HttpStatus.OK);
	    	} else {
	    		 return new ResponseEntity<>(comune, HttpStatus.NOT_FOUND);
	    		}}
	  
	  @GetMapping("/findby-nome")
		public ResponseEntity<Page<Comune>> findComuneByNome(@RequestParam String nome, @RequestParam (required = false, defaultValue = "0") int pageNumber,
				@RequestParam(required = false, defaultValue = "10") int pageSize){
		  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
	    	Page<Comune> comune = comuneService.findComuneNome(nome, pageInfo);
			return new ResponseEntity<>(comune, HttpStatus.OK);
		}	
}