package it.salvatorevirzi.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.salvatorevirzi.spring.model.User;
import it.salvatorevirzi.spring.model.UserDetailsImpl;
import it.salvatorevirzi.spring.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return UserDetailsImpl.build(user.get());
        } else {
            throw new UsernameNotFoundException("Utente " + username +" non trovato!" );
        }
    }
}