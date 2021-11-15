package it.salvatorevirzi.spring.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import it.salvatorevirzi.spring.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
    Page<Cliente> findByRagioneSocialeContainingIgnoreCase(String nomeContatto, Pageable p);
    
    Page<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale, Pageable p);

    Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable p);
	
    Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable p);
	
    Page<Cliente> findByOrderByIndirizzoSedeOperativaComuneProvinciaNomeAsc(Pageable p);

    Page<Cliente> findByOrderByDataUltimoContattoAsc(Pageable p);

    Page<Cliente> findByOrderByDataInserimentoAsc(Pageable p);
    
    Page<Cliente> findByOrderByRagioneSocialeAsc(Pageable p);
    
    Page<Cliente> findByOrderByFatturatoAnnualeAsc(Pageable p);

}
