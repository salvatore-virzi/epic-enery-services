package it.salvatorevirzi.spring.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Fattura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data;
	private Long numero;
	private Integer anno;
	private Double importo;
	@OneToOne
	private StatoFattura stato;
	@ManyToOne
	private Cliente cliente;
}
