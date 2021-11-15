package it.salvatorevirzi.spring.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import it.salvatorevirzi.spring.controller.AuthController;
import it.salvatorevirzi.spring.security.LoginRequest;
import it.salvatorevirzi.spring.security.SignupRequest;

@RestController
@RequestMapping("/request")
public class IndexController {
	
	//Queste variambili di classe mi permettono di fare il riavvio del programma dopo il primo accesso. 
	// Se infatti si è loggati sarà possibile scorrere da una pagina all'altra senza problemi
	String username;
	String password;
	
	@Autowired
	AuthController authController;
	
	@PostMapping("/login")
	public ModelAndView creaLogin(@RequestParam String username, String password) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
			if(authController.authenticateUser(loginRequest).getStatusCode().is2xxSuccessful()) {
				this.username=username;
				this.password=password;
			return new ModelAndView("homepage");
		}else {
			return new ModelAndView("error");
		}
	}
	
	@GetMapping("/homepage")
	public ModelAndView getHome() {
		if(username != null && password != null) {
		return creaLogin(username, password);
	}else {
		return new ModelAndView("badcredenziali");
		}
	}
	
	@PostMapping("/signup")
	public ModelAndView creaSignUp(@RequestParam String username, String email, 
			String password, String nome, String cognome) {
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(username);
		signupRequest.setEmail(email);
		signupRequest.setPassword(password);
		signupRequest.setNome(nome);
		signupRequest.setCognome(cognome);
		if(authController.registerUser(signupRequest).getStatusCode().isError()) {
			return  new ModelAndView("error");
		}else {
			this.username=username;
			this.password=password;
			return  new ModelAndView("homepage");
		}
	}
	
	
}
