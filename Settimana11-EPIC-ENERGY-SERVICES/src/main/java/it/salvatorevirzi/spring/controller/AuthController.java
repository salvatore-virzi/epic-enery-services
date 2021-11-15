package it.salvatorevirzi.spring.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.salvatorevirzi.spring.model.Role;
import it.salvatorevirzi.spring.model.ERole;
import it.salvatorevirzi.spring.model.User;
import it.salvatorevirzi.spring.model.UserDetailsImpl;
import it.salvatorevirzi.spring.repository.RoleRepository;
import it.salvatorevirzi.spring.repository.UserRepository;
import it.salvatorevirzi.spring.security.JwtUtils;
import it.salvatorevirzi.spring.security.LoginRequest;
import it.salvatorevirzi.spring.security.LoginResponse;
import it.salvatorevirzi.spring.security.SignupRequest;
import it.salvatorevirzi.spring.security.SignupResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
				authentication.getAuthorities();
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				// ResponseEntity<?> ResponseEntity<?> response = 
				ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(),
						userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.getExpirationTime()));
				return ResponseEntity.ok(new SignupResponse("Connesso"));
	}
		
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
	
		// Verifica l'esistenza di Username e Email già registrate
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Errore: Username già in uso!"));
		}
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Errore: Email già in uso!"));
		}
		// Crea un nuovo user codificando la password
		User user = new User(null, signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getNome(), signupRequest.getCognome());
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();

		// Verifica l'esistenza dei Role
		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleType(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByRoleType(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByRoleType(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new SignupResponse("User registrato con successo!"));
	}
}