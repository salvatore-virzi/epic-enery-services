package it.salvatorevirzi.spring.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import it.salvatorevirzi.spring.model.Cliente;
import it.salvatorevirzi.spring.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	// AGGIUNGI - MODIFICA - ELIMINA
	@PostMapping("/save")
	public ResponseEntity<?> salvaCliente(@RequestBody Cliente cliente) {
		clienteService.saveCLiente(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente) {
		clienteService.updateCliente(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> eliminaCliente(@PathVariable(required = true)Long id) {
		return clienteService.deleteCliente(id);
	}
	
		
	//ORDINAMENTI
    @GetMapping(value = "/orderby-nome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> orderByNome(@RequestParam(defaultValue = "0") 
    Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "ragioneSociale") String sort) {
      List<Cliente> list = clienteService.findAllClientiSizeSort(page, size, sort);
      return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
    
    
    @GetMapping(value = "/orderby-provincia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Cliente>> orderByProvincia(@RequestParam(defaultValue = "0") 
    Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
      	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Cliente> cliente =clienteService.findBySedeLegale(pageInfo);
    	return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
    
    @GetMapping(value = "/orderby-fatturato", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> orderBYFatturato(@RequestParam(defaultValue = "0") 
    Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "fatturatoAnnuale") String sort) {
      List<Cliente> list = clienteService.findAllClientiSizeSort(page, size, sort);
      return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
    
    @GetMapping(value = "/orderby-datainserimento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> orderByDataInserimento(@RequestParam(defaultValue = "0") 
    Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "dataInserimento") String sort) {
      List<Cliente> list = clienteService.findAllClientiSizeSort(page, size, sort);
      return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
	
    @GetMapping(value = "/orderby-dataultimocontatto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> orderByDataUltimoContatto(@RequestParam(defaultValue = "0") 
    Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "dataUltimoContatto") String sort) {
      List<Cliente> list = clienteService.findAllClientiSizeSort(page, size, sort);
      return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
	
    
    
	//RICERCHE
    @GetMapping("/findby-all")
	public ResponseEntity<Page<Cliente>> findClienteByAll(@RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Cliente> cliente = clienteService.findAllCliente(pageInfo);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
    
    @GetMapping("/findby-fatturatoannuale")
	public ResponseEntity<Page<Cliente>> findClienteByFatturatoAnnuale(@RequestParam Double fatturatoAnnuale, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Cliente> cliente = clienteService.findByFatturatoAnnuale(fatturatoAnnuale, pageInfo);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	
    @GetMapping("/findby-datainserimento")
	public ResponseEntity<Page<Cliente>> findClienteByDataInserimento(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dataInserimento, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Cliente> cliente = clienteService.findByDataInserimento(dataInserimento, pageInfo);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
    
    @GetMapping("/findby-dataultimocontatto")
	public ResponseEntity<Page<Cliente>> findClienteByUltimoContatto(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dataUltimoContatto, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Cliente> cliente = clienteService.findByDataUltimoContatto(dataUltimoContatto, pageInfo);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
    @GetMapping("/findby-nome")
	public ResponseEntity<Page<Cliente>> findClienteByNomeContatto(@RequestParam String nome, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Cliente> cliente = clienteService.findByParteDelNome(nome, pageInfo);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
}
