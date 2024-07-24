package com.generation.progetto_finale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.generation.progetto_finale.auth.model.Role;
import com.generation.progetto_finale.auth.model.UserEntity;
import com.generation.progetto_finale.auth.repository.RoleRepository;
import com.generation.progetto_finale.auth.repository.UserRepository;

@SpringBootTest
class ProgettoBaseApplicationTests 
{
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Test
	void addUser() 
	{
		UserEntity dipendente = new UserEntity();
	
		Role roles = roleRepository.findByName("DIPENDENTE").get();
		List<Role> rl = new ArrayList<>();
		rl.add(roles);
        dipendente.setRoles(rl);
		dipendente.setUsername("dipendente@email.com");


		dipendente.setPassword(passwordEncoder.encode(("1234")));


        userRepository.save(dipendente);

		
		// francesca.setRoles();
	}

}
