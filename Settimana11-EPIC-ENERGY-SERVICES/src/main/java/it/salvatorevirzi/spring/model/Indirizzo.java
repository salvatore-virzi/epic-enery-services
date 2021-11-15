package it.salvatorevirzi.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Indirizzo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String via;
	private String civico;
	private String cap;
	private String localita;
	@ManyToOne
	private Comune comune;
	@Override
	public String toString() {
		return via + " "+ civico + ", "+ comune.getNome() +" "+ cap + ",  "
				+ comune.getProvincia().getNome() +" "+comune.getProvincia().getSigla();
	}
	

}
