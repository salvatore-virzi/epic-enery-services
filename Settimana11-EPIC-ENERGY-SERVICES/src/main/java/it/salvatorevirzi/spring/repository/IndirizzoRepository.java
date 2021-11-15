package it.salvatorevirzi.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.salvatorevirzi.spring.model.Indirizzo;

public interface IndirizzoRepository  extends JpaRepository<Indirizzo, Long> {

}
