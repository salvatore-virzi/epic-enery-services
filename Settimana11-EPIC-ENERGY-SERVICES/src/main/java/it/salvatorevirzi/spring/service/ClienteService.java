package it.salvatorevirzi.spring.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.salvatorevirzi.spring.model.Cliente;
import it.salvatorevirzi.spring.repository.ClienteRepository;
import it.salvatorevirzi.spring.repository.FatturaRepository;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	FatturaRepository fatturaRepository;
	
	@Autowired
	FatturaService fatturaService;
	
	public void saveCLiente(Cliente cliente) {
		clienteRepository.save(cliente);
	}
	
	public ResponseEntity<?> deleteCliente(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			fatturaRepository.findByClienteId(id, Pageable.unpaged()).getContent()
			.forEach(fattura -> fatturaRepository.deleteById(fattura.getId()));
			clienteRepository.deleteById(id);
			 return new ResponseEntity<>("Cliente eliminato", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cliente non trovato", HttpStatus.NOT_FOUND);
		}
		
	}
	
	public void updateCliente(Cliente cliente) {
		Cliente c = clienteRepository.getById(cliente.getId());
		if(c == null)
            throw new EntityNotFoundException("Il cliente con id " + cliente.getId() + " non esiste");
		else 
		clienteRepository.save(c);
	}

	
	
	
	public Page<Cliente> findAllCliente(Pageable p){
		return clienteRepository.findAll(p);
	}
	
	public Page<Cliente> findByParteDelNome(String nome, Pageable p){
        return clienteRepository.findByRagioneSocialeContainingIgnoreCase(nome, p);
    }

    public Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable  p){
        Page<Cliente> clienteDataInserimento = clienteRepository.findByDataInserimento(dataInserimento, p);
        return clienteDataInserimento;
        }

    public Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable  p){
        Page<Cliente> clienteDataUltimoContatto = clienteRepository.findByDataUltimoContatto(dataUltimoContatto, p);
        return clienteDataUltimoContatto;       
        }
	
    public Page<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale, Pageable p){
        Page<Cliente> clienteF = clienteRepository.findByFatturatoAnnuale(fatturatoAnnuale, p);
        return clienteF;
        }

    
	 public List<Cliente> findAllClientiSizeSort(Integer page, Integer size, String sort) {
	        Pageable paging = PageRequest.of(page, size, Sort.by(sort));
	        Page<Cliente> pagedResult = clienteRepository.findAll(paging);
	        if (pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<Cliente>();
	        }}

		public Page<Cliente> findBySedeLegale(Pageable p){
	        return clienteRepository.findByOrderByIndirizzoSedeOperativaComuneProvinciaNomeAsc(p);
	    }
		
		public Page<Cliente> orderByDataUltimoContatto(Pageable p){
			return clienteRepository.findByOrderByDataUltimoContattoAsc(p);
		}
		
		public Page<Cliente> orderByDataInserimento(Pageable p){
			return clienteRepository.findByOrderByDataInserimentoAsc(p);
		}
		
		public Page<Cliente> orderByNome(Pageable p){
			return clienteRepository.findByOrderByRagioneSocialeAsc(p);
		}
		
		public Page<Cliente> orderByFatturato(Pageable p){
			return clienteRepository.findByOrderByFatturatoAnnualeAsc(p);
		}
	 }
