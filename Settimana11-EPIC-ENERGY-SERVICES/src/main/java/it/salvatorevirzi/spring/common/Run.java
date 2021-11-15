package it.salvatorevirzi.spring.common;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import it.salvatorevirzi.spring.model.Cliente;
import it.salvatorevirzi.spring.model.ERole;
import it.salvatorevirzi.spring.model.Fattura;
import it.salvatorevirzi.spring.model.Indirizzo;
import it.salvatorevirzi.spring.model.StatoFattura;
import it.salvatorevirzi.spring.model.TipoCliente;
import it.salvatorevirzi.spring.repository.ComuneRepository;
import it.salvatorevirzi.spring.service.ClienteService;
import it.salvatorevirzi.spring.service.ComuneService;
import it.salvatorevirzi.spring.service.FatturaService;
import it.salvatorevirzi.spring.service.IndirizzoService;
import it.salvatorevirzi.spring.service.ProvinciaService;
import it.salvatorevirzi.spring.service.RoleService;
import it.salvatorevirzi.spring.service.StatoFatturaService;

@Component
public class Run implements CommandLineRunner {

	@Autowired
	StatoFatturaService statoFatturaService;
	
	@Autowired
	FatturaService fatturaService;
	
	@Autowired
	ComuneService comuneService;

	@Autowired
	ClienteService clienteService;
	@Autowired
	ProvinciaService provinciaService;

	@Autowired
	RoleService roleService;

	@Autowired
	IndirizzoService indirizzoService;
	@Autowired
	ComuneRepository comuneRepository;

	public void start() {
		if (roleService.findAllRoles().isEmpty()) {
			roleService.saveRole(ERole.ROLE_ADMIN);
			roleService.saveRole(ERole.ROLE_USER);
		}
		provinciaService.saveProvincia();
		comuneService.saveComune();
		if(indirizzoService.findAllIndirizzo(Pageable.unpaged()) == null) {
		popolaDB();
		}
	}

	void popolaDB() {
		roleService.saveRole(ERole.ROLE_ADMIN);
		roleService.saveRole(ERole.ROLE_USER);
		provinciaService.saveProvincia();
		comuneService.saveComune();

		// Indirizzo
		Indirizzo i = new Indirizzo();
		i.setCap("98033");
		i.setCivico("1");
		i.setComune(comuneRepository.getById(20L));
		i.setLocalita("mare");
		i.setVia("Via Roma");
		indirizzoService.saveNewIndirizzo(i);
		
		Indirizzo i1 = new Indirizzo();
		i1.setCap("95034");
		i1.setCivico("2");
		i1.setComune(comuneRepository.getById(207L));
		i1.setLocalita("pianura");
		i1.setVia("Via Milano");
		indirizzoService.saveNewIndirizzo(i1);

		Indirizzo i2 = new Indirizzo();
		i2.setCap("80022");
		i2.setCivico("5");
		i2.setComune(comuneRepository.getById(247L));
		i2.setLocalita("montagna");
		i2.setVia("Via Palermo");
		indirizzoService.saveNewIndirizzo(i2);

		Indirizzo i3 = new Indirizzo();
		i3.setCap("76543");
		i3.setCivico("2");
		i3.setComune(comuneRepository.getById(401l));
		i3.setLocalita("pianura");
		i3.setVia("Via Po");
		indirizzoService.saveNewIndirizzo(i3);
		
		Indirizzo i4 = new Indirizzo();
		i4.setCap("12345");
		i4.setCivico("45");
		i4.setComune(comuneRepository.getById(1000L));
		i4.setLocalita("collina");
		i4.setVia("Via Genova");
		indirizzoService.saveNewIndirizzo(i4);
		
		Indirizzo i5 = new Indirizzo();
		i5.setCap("876543");
		i5.setCivico("123");
		i5.setComune(comuneRepository.getById(2000L));
		i5.setLocalita("grotta");
		i5.setVia("Via Monza");
		indirizzoService.saveNewIndirizzo(i5);
		
		Indirizzo i6 = new Indirizzo();
		i6.setCap("654323");
		i6.setCivico("7");
		i6.setComune(comuneRepository.getById(102L));
		i6.setLocalita("mare");
		i6.setVia("Via Castagnola");
		indirizzoService.saveNewIndirizzo(i6);
		
		Indirizzo i7 = new Indirizzo();
		i7.setCap("1234563");
		i7.setCivico("46");
		i7.setComune(comuneRepository.getById(392L));
		i7.setLocalita("west");
		i7.setVia("Via America");
		indirizzoService.saveNewIndirizzo(i7);

		
		
		Cliente c1 = new Cliente();
		c1.setRagioneSociale("Antonio");
		c1.setPartitaIva("AnotnioIva");
		c1.setTipoCliente(TipoCliente.PA);
		c1.setEmail("a@a");
		c1.setPec("a@a");
		c1.setTelefono("9878221");		
		c1.setNomeContatto("Antonio");
		c1.setCognomeContatto("Anto");
		c1.setTelefonoContatto("12345");
		c1.setEmailContatto("a@a");
		c1.setIndirizzoSedeLegale(i);
		c1.setIndirizzoSedeOperativa(i);
		c1.setDataInserimento(LocalDate.of(2021, 03, 10));
		c1.setDataUltimoContatto(LocalDate.of(2021, 05, 07));
		c1.setFatturatoAnnuale(10000.50);
		clienteService.saveCLiente(c1);
		
		Cliente c2 = new Cliente();
		c2.setRagioneSociale("Beatrice");
		c2.setPartitaIva("BeatriceIva");
		c2.setTipoCliente(TipoCliente.SAS);
		c2.setEmail("b@b");
		c2.setPec("b@b");
		c2.setTelefono("9878221");		
		c2.setNomeContatto("Beatrice");
		c2.setCognomeContatto("Beatrice");
		c2.setTelefonoContatto("212345");
		c2.setEmailContatto("b@b");
		c2.setIndirizzoSedeLegale(i);
		c2.setIndirizzoSedeOperativa(i1);
		c2.setDataInserimento(LocalDate.of(2020, 03, 10));
		c2.setDataUltimoContatto(LocalDate.of(2021, 05, 07));
		c2.setFatturatoAnnuale(104000.50);
		clienteService.saveCLiente(c2);
		
		
		Cliente c3 = new Cliente();
		c3.setRagioneSociale("Carla");
		c3.setPartitaIva("CarlaIva");
		c3.setTipoCliente(TipoCliente.SPA);
		c3.setEmail("c@c");
		c3.setPec("c@c");
		c3.setTelefono("9878221");		
		c3.setNomeContatto("Carla");
		c3.setCognomeContatto("Carla");
		c3.setTelefonoContatto("212345");
		c3.setEmailContatto("c@c");
		c3.setIndirizzoSedeLegale(i1);
		c3.setIndirizzoSedeOperativa(i1);
		c3.setDataInserimento(LocalDate.of(2019, 10, 10));
		c3.setDataUltimoContatto(LocalDate.of(2021, 05, 17));
		c3.setFatturatoAnnuale(104040.50);
		clienteService.saveCLiente(c3);
		
		Cliente c4 = new Cliente();
		c4.setRagioneSociale("Dario");
		c4.setPartitaIva("DarioIva");
		c4.setTipoCliente(TipoCliente.SRL);
		c4.setEmail("d@d");
		c4.setPec("d@d");
		c4.setTelefono("9878221");		
		c4.setNomeContatto("Dario");
		c4.setCognomeContatto("Dario");
		c4.setTelefonoContatto("212345");
		c4.setEmailContatto("d@d");
		c4.setIndirizzoSedeLegale(i2);
		c4.setIndirizzoSedeOperativa(i3);
		c4.setDataInserimento(LocalDate.of(2021, 10, 10));
		c4.setDataUltimoContatto(LocalDate.of(2021, 11, 17));
		c4.setFatturatoAnnuale(10440.50);
		clienteService.saveCLiente(c4);
		
		Cliente c5 = new Cliente();
		c5.setRagioneSociale("Franco");
		c5.setPartitaIva("FrancoIva");
		c5.setTipoCliente(TipoCliente.SAS);
		c5.setEmail("f@f");
		c5.setPec("f@f");
		c5.setTelefono("9878221");		
		c5.setNomeContatto("Franco");
		c5.setCognomeContatto("Franco");
		c5.setTelefonoContatto("212345");
		c5.setEmailContatto("f@f");
		c5.setIndirizzoSedeLegale(i3);
		c5.setIndirizzoSedeOperativa(i3);
		c5.setDataInserimento(LocalDate.of(2021, 07, 10));
		c5.setDataUltimoContatto(LocalDate.of(2021, 11, 17));
		c5.setFatturatoAnnuale(10440.50);
		clienteService.saveCLiente(c5);
		
		Cliente c6 = new Cliente();
		c6.setRagioneSociale("Gianni");
		c6.setPartitaIva("GianniIva");
		c6.setTipoCliente(TipoCliente.SRL);
		c6.setEmail("g@g");
		c6.setPec("g@g");
		c6.setTelefono("9878221");		
		c6.setNomeContatto("Gianni");
		c6.setCognomeContatto("Gianni");
		c6.setTelefonoContatto("212345");
		c6.setEmailContatto("g@g");
		c6.setIndirizzoSedeLegale(i4);
		c6.setIndirizzoSedeOperativa(i6);
		c6.setDataInserimento(LocalDate.of(2020, 07, 10));
		c6.setDataUltimoContatto(LocalDate.of(2021, 11, 21));
		c6.setFatturatoAnnuale(10420.50);
		clienteService.saveCLiente(c6);
		
		Cliente c7 = new Cliente();
		c7.setRagioneSociale("Ivana");
		c7.setPartitaIva("IvanaIva");
		c7.setTipoCliente(TipoCliente.SPA);
		c7.setEmail("i@i");
		c7.setPec("i@i");
		c7.setTelefono("98782221");		
		c7.setNomeContatto("Ivana");
		c7.setCognomeContatto("Ivana");
		c7.setTelefonoContatto("123212345");
		c7.setEmailContatto("i@i");
		c7.setIndirizzoSedeLegale(i5);
		c7.setIndirizzoSedeOperativa(i7);
		c7.setDataInserimento(LocalDate.of(2020, 07, 10));
		c7.setDataUltimoContatto(LocalDate.of(2021, 12, 23));
		c7.setFatturatoAnnuale(10420.50);
		clienteService.saveCLiente(c7);
		
		Cliente c8 = new Cliente();
		c8.setRagioneSociale("Luca");
		c8.setPartitaIva("LucaIva");
		c8.setTipoCliente(TipoCliente.SRL);
		c8.setEmail("l@l");
		c8.setPec("l@l");
		c8.setTelefono("9871282221");		
		c8.setNomeContatto("Luca");
		c8.setCognomeContatto("Luca");
		c8.setTelefonoContatto("13323212345");
		c8.setEmailContatto("l@l");
		c8.setIndirizzoSedeLegale(i6);
		c8.setIndirizzoSedeOperativa(i6);
		c8.setDataInserimento(LocalDate.of(2021, 04, 10));
		c8.setDataUltimoContatto(LocalDate.of(2021, 06, 20));
		c8.setFatturatoAnnuale(420.50);
		clienteService.saveCLiente(c8);
		
		Cliente c9 = new Cliente();
		c9.setRagioneSociale("Nando");
		c9.setPartitaIva("NandoIva");
		c9.setTipoCliente(TipoCliente.SRL);
		c9.setEmail("n@n");
		c9.setPec("n@n");
		c9.setTelefono("4551282221");		
		c9.setNomeContatto("Nando");
		c9.setCognomeContatto("Nando");
		c9.setTelefonoContatto("56323212345");
		c9.setEmailContatto("n@n");
		c9.setIndirizzoSedeLegale(i7);
		c9.setIndirizzoSedeOperativa(i6);
		c9.setDataInserimento(LocalDate.of(2019, 04, 10));
		c9.setDataUltimoContatto(LocalDate.of(2021, 07, 20));
		c9.setFatturatoAnnuale(42450.0);
		clienteService.saveCLiente(c9);
		
		Cliente c10 = new Cliente();
		c10.setRagioneSociale("Maria");
		c10.setPartitaIva("MariaIva");
		c10.setTipoCliente(TipoCliente.SPA);
		c10.setEmail("m@m");
		c10.setPec("m@m");
		c10.setTelefono("235123282221");		
		c10.setNomeContatto("Maria");
		c10.setCognomeContatto("Maria");
		c10.setTelefonoContatto("65432456");
		c10.setEmailContatto("m@m");
		c10.setIndirizzoSedeLegale(i5);
		c10.setIndirizzoSedeOperativa(i7);
		c10.setDataInserimento(LocalDate.of(2018, 04, 10));
		c10.setDataUltimoContatto(LocalDate.of(2021, 05, 25));
		c10.setFatturatoAnnuale(42220.50);
		clienteService.saveCLiente(c10);
		
		Cliente c11 = new Cliente();
		c11.setRagioneSociale("Rosa");
		c11.setPartitaIva("RosyIva");
		c11.setTipoCliente(TipoCliente.SPA);
		c11.setEmail("r@r");
		c11.setPec("r@r");
		c11.setTelefono("235123282221");		
		c11.setNomeContatto("Rosy");
		c11.setCognomeContatto("Rosy");
		c11.setTelefonoContatto("6523432456");
		c11.setEmailContatto("r@r");
		c11.setIndirizzoSedeLegale(i);
		c11.setIndirizzoSedeOperativa(i1);
		c11.setDataInserimento(LocalDate.of(2017, 04, 10));
		c11.setDataUltimoContatto(LocalDate.of(2021, 03, 25));
		c11.setFatturatoAnnuale(42220.50);
		clienteService.saveCLiente(c11);	
		
		Cliente c12 = new Cliente();
		c12.setRagioneSociale("Salvo");
		c12.setPartitaIva("SalvoIva");
		c12.setTipoCliente(TipoCliente.SPA);
		c12.setEmail("s@s");
		c12.setPec("s@s");
		c12.setTelefono("235123282221");		
		c12.setNomeContatto("Salvo");
		c12.setCognomeContatto("Salvo");
		c12.setTelefonoContatto("6523432456");
		c12.setEmailContatto("s@s");
		c12.setIndirizzoSedeLegale(i2);
		c12.setIndirizzoSedeOperativa(i);
		c12.setDataInserimento(LocalDate.of(2016, 04, 10));
		c12.setDataUltimoContatto(LocalDate.of(2021, 03, 05));
		c12.setFatturatoAnnuale(42220.50);
		clienteService.saveCLiente(c12);
		
		StatoFattura s1=new StatoFattura();
		s1.setNome("eseguita");
		statoFatturaService.save(s1);
		
		StatoFattura s2=new StatoFattura();
		s2.setNome("Non eseguita");
		statoFatturaService.save(s2);
		
		StatoFattura s3=new StatoFattura();
		s3.setNome("In esecuzione");
		statoFatturaService.save(s3);
		
		StatoFattura s4=new StatoFattura();
		s4.setNome("Fallita");
		statoFatturaService.save(s4);
		
		Fattura f1=new Fattura();
		f1.setData(LocalDate.of(2021, 02, 27));
		f1.setNumero(1l);
		f1.setAnno(2021);
		f1.setImporto(1400.0);
		f1.setStato(s1);
		f1.setCliente(c1);
		fatturaService.save(f1);
		
		Fattura f2=new Fattura();
		f2.setData(LocalDate.of(2021, 02, 24));
		f2.setNumero(2l);
		f2.setAnno(2020);
		f2.setImporto(1300.0);
		f2.setStato(s1);
		f2.setCliente(c2);
		fatturaService.save(f2);
		
		Fattura f3=new Fattura();
		f3.setData(LocalDate.of(2012, 02, 24));
		f3.setNumero(3l);
		f3.setAnno(2012);
		f3.setImporto(1430.0);
		f3.setStato(s2);
		f3.setCliente(c3);
		fatturaService.save(f3);

		Fattura f4=new Fattura();
		f4.setData(LocalDate.of(2013, 05, 20));
		f4.setNumero(5l);
		f4.setAnno(2015);
		f4.setImporto(1330.0);
		f4.setStato(s4);
		f4.setCliente(c1);
		fatturaService.save(f4);
		
		Fattura f5=new Fattura();
		f5.setData(LocalDate.of(2004, 12, 20));
		f5.setNumero(6l);
		f5.setAnno(2017);
		f5.setImporto(1760.0);
		f5.setStato(s2);
		f5.setCliente(c12);
		fatturaService.save(f5);
		
		Fattura f6=new Fattura();
		f6.setData(LocalDate.of(2014, 12, 20));
		f6.setNumero(7l);
		f6.setAnno(2019);
		f6.setImporto(15430.0);
		f6.setStato(s3);
		f6.setCliente(c4);
		fatturaService.save(f6);
		
		Fattura f7=new Fattura();
		f7.setData(LocalDate.of(2010, 12, 20));
		f7.setNumero(9l);
		f7.setAnno(2017);
		f7.setImporto(150.0);
		f7.setStato(s4);
		f7.setCliente(c11);
		fatturaService.save(f7);
		
		Fattura f8=new Fattura();
		f8.setData(LocalDate.of(2012, 12, 12));
		f8.setNumero(8l);
		f8.setAnno(2020);
		f8.setImporto(430.0);
		f8.setStato(s3);
		f8.setCliente(c10);
		fatturaService.save(f8);
	}

	@Override
	public void run(String... args) throws Exception {
		popolaDB();
	}

}