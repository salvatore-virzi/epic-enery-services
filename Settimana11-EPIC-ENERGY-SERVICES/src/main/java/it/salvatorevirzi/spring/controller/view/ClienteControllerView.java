package it.salvatorevirzi.spring.controller.view;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import it.salvatorevirzi.spring.controller.ClienteController;
import it.salvatorevirzi.spring.model.Cliente;
import it.salvatorevirzi.spring.model.Indirizzo;
import it.salvatorevirzi.spring.model.TipoCliente;
import it.salvatorevirzi.spring.repository.ClienteRepository;
import it.salvatorevirzi.spring.repository.ComuneRepository;
import it.salvatorevirzi.spring.repository.IndirizzoRepository;
import it.salvatorevirzi.spring.service.ClienteService;
import it.salvatorevirzi.spring.service.ComuneService;
import it.salvatorevirzi.spring.service.IndirizzoService;

@RestController
@RequestMapping("/homepage")
public class ClienteControllerView {

	@Autowired
	IndexController indexController;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ClienteController clienteController;
	
	@Autowired
	ComuneService comuneService;
	
	@Autowired
	IndirizzoService indirizzoService;
	
	@Autowired
	ComuneRepository comuneRepository;
	
	@Autowired
	IndirizzoRepository indirizzoRepository;

	//Mi ritorna tutti i clienti impaginati e ordinati
	@GetMapping("/clientiall")
	public ModelAndView viewAllCliente(@RequestParam(required = false, defaultValue = "0") int pageNumber, //
			@RequestParam(required = false, defaultValue = "5") int pageSize, //
			@RequestParam(required = false, defaultValue = "nome") String ordina) {
		ModelAndView m = new ModelAndView("allcliente");
		Pageable p = PageRequest.of(pageNumber, pageSize);
		if (ordina.equals("provincia")) {
			m.addObject("list", clienteService.findBySedeLegale(p));
		} else if (ordina.equals("dataUltimoContatto")) {
			m.addObject("list", clienteService.orderByDataUltimoContatto(p));
		} else if (ordina.equals("datainserimento")) {
			m.addObject("list", clienteService.orderByDataInserimento(p));
		} else if (ordina.equals("fatturatoAnnuale")) {
			m.addObject("list", clienteService.orderByFatturato(p));
		} else if (ordina.equals("nome")) {
			m.addObject("list", clienteService.orderByNome(p));
		} else {
			m.addObject("list", clienteService.findAllCliente(p));
		}
		return m;
	}

	//RITORNA ALLA HOME
	@GetMapping()
	public ModelAndView getHome() {
		return indexController.getHome();
	}
	
	
	@GetMapping("/add")
	public ModelAndView addCliente() {
		ModelAndView m = new ModelAndView("addcliente");
		m.addObject("cliente", new Cliente());
		return m;
	}
	
	//Si prende i dati del form addcliente e crea il cliente
	@PostMapping("/create")
	public ModelAndView creaCliente(@RequestParam String ragioneSociale, 
			String partitaIva, TipoCliente tipoCliente, String email,String pec, 
			String telefono, Long operativa, Long legale,String nomeContatto,String cognomeContatto, 
			String telefonoContatto, String emailContatto,
			@DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dataUltimoContatto, 
			Double fatturatoAnnuale) {
		Cliente cliente= new Cliente();
		cliente.setRagioneSociale(ragioneSociale);
		cliente.setPartitaIva(partitaIva);
		cliente.setTipoCliente(tipoCliente);
		cliente.setEmail(email);
		cliente.setPec(pec);
		cliente.setTelefono(telefono);
		cliente.setNomeContatto(nomeContatto);
		cliente.setCognomeContatto(cognomeContatto);
		cliente.setTelefonoContatto(telefonoContatto);
		cliente.setEmailContatto(emailContatto);
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(dataUltimoContatto);
		cliente.setFatturatoAnnuale(fatturatoAnnuale);
		if(operativa != null) {
			for(Indirizzo i : indirizzoRepository.findAll()) {
				if(operativa == i.getId()) {
					cliente.setIndirizzoSedeOperativa(indirizzoRepository.getById(operativa));
				}
			}}
		if(legale != null) {
			for(Indirizzo i : indirizzoRepository.findAll()) {
				if(legale == i.getId()) {
					cliente.setIndirizzoSedeLegale(indirizzoRepository.getById(legale));
				}
			}
		
		}
		ResponseEntity<?> result = clienteController.salvaCliente(cliente);
		if (result.getStatusCode().isError()) {
			return new ModelAndView("errore");
		} else {
			return viewAllCliente(0, 5, "");
		}
	}
	
	
	@GetMapping("/delete/{id}")
	public ModelAndView deleteCliente(@PathVariable(required = true) Long id) {
		if (clienteService.deleteCliente(id).getStatusCode().is2xxSuccessful()) {
			clienteService.deleteCliente(id);
			return new ModelAndView("delete");
		}
		return new ModelAndView("notfoud");
	}

	@GetMapping("/modificacliente/{id}")
	public ModelAndView editCliente(@PathVariable(required = true) Long id) {
		ModelAndView m = new ModelAndView("editcliente");
		Cliente cliente = clienteRepository.getById(id);
		m.addObject("cliente", cliente);
		return m;
	}


	@GetMapping("/profilo/{nome}")
	public ModelAndView viewProfilo(@PathVariable(required = true) String nome) {
		Cliente cliente = clienteService.findByParteDelNome(nome, Pageable.unpaged()).getContent().get(0);
		ModelAndView m = new ModelAndView("profilo");
		m.addObject("cliente", cliente);
		return m;
	}

	//Ho usato vari form e in questo metodo mi "gestisco" i pulsanti dei vari form.
	//se sono diversi da null allora si attiva la ricerca relativa a quel pulsante
	@GetMapping("/ricerca")
	public ModelAndView ricerca(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInserimento,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto, Double fatturatoAnnuale,
			String nome) {
		ModelAndView view = new ModelAndView("search");
		Pageable p = Pageable.unpaged();
		if (nome != null) {
			view.addObject("cliente", clienteService.findByParteDelNome(nome, p));
		}
		if (fatturatoAnnuale != null) {
			view.addObject("cliente", clienteService.findByFatturatoAnnuale(fatturatoAnnuale, p));
		}
		if (dataInserimento != null) {
			view.addObject("cliente", clienteService.findByDataInserimento(dataInserimento, p));
		}
		if (dataUltimoContatto != null) {
			view.addObject("cliente", clienteService.findByDataUltimoContatto(dataUltimoContatto, p));
		}
		return view;
	}
	
	
	@PostMapping("/edit")
	public ModelAndView  updateCliente(long id, @RequestParam String ragioneSociale, 
			String partitaIva, TipoCliente tipoCliente, String email,String pec, 
			String telefono, String nomeContatto,String cognomeContatto, 
			String telefonoContatto, String emailContatto,
			@DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dataUltimoContatto, 
			Double fatturatoAnnuale ) {
		Cliente cliente= clienteRepository.findById(id).get();
		cliente.setRagioneSociale(ragioneSociale);
		cliente.setPartitaIva(partitaIva);
		cliente.setTipoCliente(tipoCliente);
		cliente.setEmail(email);
		cliente.setPec(pec);
		cliente.setTelefono(telefono);
		cliente.setNomeContatto(nomeContatto);
		cliente.setCognomeContatto(cognomeContatto);
		cliente.setTelefonoContatto(telefonoContatto);
		cliente.setEmailContatto(emailContatto);
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(dataUltimoContatto);
		cliente.setFatturatoAnnuale(fatturatoAnnuale);
		
		clienteRepository.save(cliente);
		
		return viewAllCliente(0, 5, "");
	}
	
	@GetMapping("/addIndirizzo")
	public ModelAndView addIndirizzo() {
		ModelAndView m = new ModelAndView("addindirizzo");
		m.addObject("indirizzo", new Indirizzo());
		return m;
	}
	
	@PostMapping("/indirizzoadd")
	public ModelAndView addAddress(@RequestParam String via, String civico, String cap, String localita, Long comune) {
		Indirizzo indirizzo = new Indirizzo();
		indirizzo.setVia(via);
		indirizzo.setCivico(civico);
		indirizzo.setCap(cap);
		indirizzo.setLocalita(localita);
		indirizzo.setComune(comuneRepository.getById(comune));
		indirizzoService.saveNewIndirizzo(indirizzo);
		ModelAndView m= new ModelAndView("indirizzoadd");
		m.addObject("indirizzo", indirizzoService.findAllIndirizzo(Pageable.unpaged()));
		return m;
	}

	@GetMapping("/mostrai")
	public ModelAndView showInd() {
		ModelAndView m= new ModelAndView("indirizzoadd");
		m.addObject("indirizzo", indirizzoService.findAllIndirizzo(Pageable.unpaged()));
		return m;
	}
	
}
