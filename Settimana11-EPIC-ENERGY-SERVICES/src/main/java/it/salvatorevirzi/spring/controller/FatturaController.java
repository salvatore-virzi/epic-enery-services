package it.salvatorevirzi.spring.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import it.salvatorevirzi.spring.model.Fattura;
import it.salvatorevirzi.spring.service.FatturaService;

@RestController
@RequestMapping("/fattura")
public class FatturaController {
	@Autowired
	private FatturaService fatturaService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveFattura(@RequestBody Fattura fattura) {
		fatturaService.save(fattura);
		return new ResponseEntity<>(fattura, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateFattura(@RequestBody Fattura fattura) {
		fatturaService.update(fattura);
		return new ResponseEntity<>(fattura, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> eliminaCFattura(@PathVariable(required = true)Long id) {
		return fatturaService.delete(id);
	}
	
	@GetMapping("/findby-all")
	public ResponseEntity<Page<Fattura>> findComuneByAll (@RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Fattura> fattura = fatturaService.findAllFattura(pageInfo);
    	if(fattura.hasContent()) {
        	return new ResponseEntity<>(fattura, HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>(fattura, HttpStatus.NOT_FOUND);
    		}}
	
	@GetMapping("/findby-id")
	public ResponseEntity<Page<Fattura>> findFatturaById(@RequestParam Long id, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Fattura> fattura = fatturaService.findFatturaId(id, pageInfo);
    	if(fattura.hasContent()) {
        	return new ResponseEntity<>(fattura, HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>(fattura, HttpStatus.NOT_FOUND);
    		}}
	

	@GetMapping("/findby-cliente")
	public ResponseEntity<Page<Fattura>> findFatturaByCliente(@RequestParam Long id, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Fattura> fattura = fatturaService.findFattureByCliente(id, pageInfo);
    	if(fattura.hasContent()) {
        	return new ResponseEntity<>(fattura, HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>(fattura, HttpStatus.NOT_FOUND);
    		}}	
	
	@GetMapping("/findby-stato")
	public ResponseEntity<Page<Fattura>> findFatturaByStato(@RequestParam Long id, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Fattura> fattura = fatturaService.findByStato(id,pageInfo);
    	if(fattura.hasContent()) {
        	return new ResponseEntity<>(fattura, HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>(fattura, HttpStatus.NOT_FOUND);
    		}}	
	
	@GetMapping("/findby-data")
	public ResponseEntity<Page<Fattura>> findFatturaData(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd")LocalDate date, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Fattura> fattura = fatturaService.findFatturaByData(date,pageInfo);
    	if(fattura.hasContent()) {
        	return new ResponseEntity<>(fattura, HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>(fattura, HttpStatus.NOT_FOUND);
    		}}	
	
	@GetMapping("/findby-anno")
	public ResponseEntity<Page<Fattura>> findFatturaAnno(@RequestParam Integer anno, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Fattura> fattura = fatturaService.findAnno(anno, pageInfo);
    	if(fattura.hasContent()) {
        	return new ResponseEntity<>(fattura, HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>(fattura, HttpStatus.NOT_FOUND);
    		}}	
	
	@GetMapping("/findby-importo")
	public ResponseEntity<Page<Fattura>> findFatturaImporto(@RequestParam Double importoMinimo, Double importoMassimo, @RequestParam (required = false, defaultValue = "0") int pageNumber,
		@RequestParam(required = false, defaultValue = "10") int pageSize){
	  	Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
    	Page<Fattura> fattura = fatturaService.findByImporto(importoMinimo, importoMassimo, pageInfo);
    	if(fattura.hasContent()) {
        	return new ResponseEntity<>(fattura, HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>(fattura, HttpStatus.NOT_FOUND);
    		}}	
}
	
	

