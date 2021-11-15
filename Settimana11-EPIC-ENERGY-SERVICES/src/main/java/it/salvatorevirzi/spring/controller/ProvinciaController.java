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

import it.salvatorevirzi.spring.model.Provincia;
import it.salvatorevirzi.spring.service.ProvinciaService;

@RestController
@RequestMapping("/provincia")
public class ProvinciaController {
	@Autowired
	private ProvinciaService provinciaService;
	
	@GetMapping("/saveallprovincia")
	public ResponseEntity<?> saveProvincia() {
		provinciaService.saveProvincia();
		return new ResponseEntity<>("Tutte le provincie italiane sono state caricate correttamente", HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveProvincia(@RequestBody Provincia provincia) {
		provinciaService.saveNewProvincia(provincia);
		return new ResponseEntity<>(provincia, HttpStatus.OK);

	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateCliente(@RequestBody Provincia provincia) {
		provinciaService.updateProvincia(provincia);
		return new ResponseEntity<>(provincia, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> eliminaProvincia(@PathVariable(required = true)Long id) {
		return provinciaService.deleteProvincia(id);
	}
	
	@GetMapping("/findby-all")
	public ResponseEntity<Page<Provincia>> findClienteByAll (@RequestParam (required = false, defaultValue = "0") int pageNumber, //
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Provincia> provincia = provinciaService.findAllProvincia(pageInfo);
		if(provincia.hasContent()) {
    	return new ResponseEntity<>(provincia, HttpStatus.OK);
	} else {
		 return new ResponseEntity<>(provincia, HttpStatus.NOT_FOUND);
		}
	}	
	
	 @GetMapping("/findby-nome")
		public ResponseEntity<Page<Provincia>> findClienteByNomeContatto(@RequestParam String nome, @RequestParam (required = false, defaultValue = "0") int pageNumber,
				@RequestParam(required = false, defaultValue = "10") int pageSize){
		  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
	    	Page<Provincia> comune = provinciaService.findProvincia(nome,pageInfo);
			return new ResponseEntity<>(comune, HttpStatus.OK);
		}	
}
