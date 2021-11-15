package it.salvatorevirzi.spring;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import it.salvatorevirzi.spring.controller.ComuneController;
import it.salvatorevirzi.spring.model.Cliente;
import it.salvatorevirzi.spring.service.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
class Settimana11EpicEnergyServicesApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ComuneController comuneController;
	
	
	@Autowired
	ClienteService clienteService;
	
	
	@Test
	void findAllComune() throws Exception{
		this.mockMvc.perform(get("/comune/findby-all")).andDo(print())
					.andExpect(status().isOk());
					
	}
	
	@Test
	void findAllProvincia() throws Exception{
		this.mockMvc.perform(get("/provincia/findby-all")).andDo(print())
					.andExpect(status().isOk());
					
	}
	

			@Test
			void findAllCliente() throws Exception{				
				this.mockMvc.perform(get("/cliente/findby-nome?nome=Aldo")).andDo(print())
							.andExpect(status().isOk());
							
			}
}
