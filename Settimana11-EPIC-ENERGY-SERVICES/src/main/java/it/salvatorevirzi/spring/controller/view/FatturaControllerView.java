package it.salvatorevirzi.spring.controller.view;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import it.salvatorevirzi.spring.model.Cliente;
import it.salvatorevirzi.spring.model.Fattura;
import it.salvatorevirzi.spring.model.StatoFattura;
import it.salvatorevirzi.spring.repository.ClienteRepository;
import it.salvatorevirzi.spring.repository.FatturaRepository;
import it.salvatorevirzi.spring.repository.StatoFatturaRepository;
import it.salvatorevirzi.spring.service.FatturaService;

@RestController
@RequestMapping("/fattura")
public class FatturaControllerView {

	@Autowired
	FatturaService fatturaService;

	@Autowired
	StatoFatturaRepository statoFatturaRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	FatturaRepository fatturaRepository;

	@GetMapping("/fatturaall")
	public ModelAndView viewAllFattura(@RequestParam(required = false, defaultValue = "0") int pageNumber, //
			@RequestParam(required = false, defaultValue = "5") int pageSize, //
			@RequestParam(required = false, defaultValue = "nome") String ordina) {
		ModelAndView m = new ModelAndView("allfattura");
		Pageable p = PageRequest.of(pageNumber, pageSize);
		if (ordina.equals("anno")) {
			m.addObject("list", fatturaService.orderByAnno(p));
		} else if (ordina.equals("data")) {
			m.addObject("list", fatturaService.orderByData(p));
		} else if (ordina.equals("importo")) {
			m.addObject("list", fatturaService.orderByImporto(p));
		} else {
			m.addObject("list", fatturaService.findAllFattura(p));
		}
		return m;
	}

	@GetMapping("/add")
	public ModelAndView addFattura() {
		ModelAndView m = new ModelAndView("addfattura");
		return m;
	}

	@PostMapping("/create")
	public ModelAndView creaCliente(@RequestParam Long numero, Integer anno, Double importo, Long stato, Long cliente) {
		Fattura fattura = new Fattura();
		fattura.setData(LocalDate.now());
		fattura.setNumero(numero);
		fattura.setAnno(anno);
		fattura.setImporto(importo);
		fattura.setStato(statoFatturaRepository.getById(stato));
		fattura.setCliente(clienteRepository.getById(cliente));
		fatturaService.save(fattura);
		return viewAllFattura(0, 5, "data");
	}

	@GetMapping("/view/{id}")
	public ModelAndView test(@PathVariable(required = true) Long id) {
		Fattura fattura = fatturaRepository.getById(id);
		ModelAndView m = new ModelAndView("fattura");
		m.addObject("fattura", fattura);
		return m;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteCliente(@PathVariable(required = true) Long id) {
		if (fatturaService.delete(id).getStatusCode().is2xxSuccessful()) {
			fatturaService.delete(id);
			return new ModelAndView("delete");
		}
		return new ModelAndView("notfoud");
	}

	@GetMapping("/modifica/{id}")
	public ModelAndView editFattura(@PathVariable(required = true) Long id) {
		ModelAndView m = new ModelAndView("editfattura");
		Fattura fattura = fatturaRepository.getById(id);
		m.addObject("fattura", fattura);
		return m;
	}

	@PostMapping("/edit")
	public ModelAndView editFattura(Fattura fattura) {
		fatturaService.updateFattura(fattura.getId(), fattura);
		return viewAllFattura(0, 5, "data");
	}
	

	@GetMapping("/ricerca")
	public ModelAndView ricercaa(String cliente, String stato, Integer anno, Double minimo, Double massimo) {
		ModelAndView view = new ModelAndView("searchf");
		Pageable p = PageRequest.of(0, 5);
		if (cliente != null) {
			Cliente c = clienteRepository.findByRagioneSocialeContainingIgnoreCase(cliente, p).getContent().get(0);
			view.addObject("fattura", fatturaService.findFattureByCliente(c.getId(), p));
		}
		if (stato != null) {
			StatoFattura s = statoFatturaRepository.findByNome(stato, p).getContent().get(0);
			view.addObject("fattura", fatturaService.findByStato(s.getId(), p));
		}
		if (anno != null) {
			view.addObject("fattura", fatturaService.findAnno(anno, p));
		}
		
		//Qua controllo i valori di massimo e minimo che arrivano.
		//se uno dei due valori e null il programma potrebbe entrare in errore. Per evitare ciò faccio il controllo
		if (minimo != null || massimo != null) {
			if (minimo == null) {
				minimo = 0.0;
			}
			if (massimo == null) {
				massimo=0.0;
				//grazie a questo ciclo se il massimo è impostato su "null" il suo valore sarà sempre l'importo massimo di tutte le fatture
				for (Fattura a : fatturaService.findAllFattura(p).getContent()) {
					if (massimo < a.getImporto()) {
						massimo = a.getImporto();
					}
				}
				view.addObject("fattura", fatturaService.findByImporto(minimo, massimo, p));
			}
		}
		return view;
	}
}