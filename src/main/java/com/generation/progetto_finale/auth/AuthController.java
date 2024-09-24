package com.generation.progetto_finale.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.auth.dto.AuthResponseDto;
import com.generation.progetto_finale.auth.dto.CredentialsDto;
import com.generation.progetto_finale.auth.dto.RegistrationDto;
import com.generation.progetto_finale.auth.model.Role;
import com.generation.progetto_finale.auth.model.UserEntity;
import com.generation.progetto_finale.auth.repository.RoleRepository;
import com.generation.progetto_finale.auth.repository.UserRepository;
import com.generation.progetto_finale.auth.security.JWTGenerator;

@RestController
@RequestMapping("/auth")
public class AuthController 
{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;


    @PostMapping("login")
    public AuthResponseDto login(@RequestBody CredentialsDto loginDto)
    {
        
            Authentication user = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
            loginDto.getUsername(),
            loginDto.getPassword()));

        String token = jwtGenerator.generateToken(user);
        String username = jwtGenerator.getUsernameFromJWT(token);
        // metto oltre al token il ruolo 
        Optional<String> role =   jwtGenerator.getRolesFromJWT(token)
                        .stream()
                        .filter(r -> !r.equals("ROLE_USER"))
                        .findFirst();

        if(role.isPresent())
            return new AuthResponseDto(token, role.get().replace("ROLE_", ""), username);
        else
            return new AuthResponseDto(token, "", username);
    }

  @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegistrationDto registerDto) 
    {
        System.out.println(registerDto);

        if (userRepository.existsByUsername(registerDto.getUsername())) 
        {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        System.out.println(registerDto.getPassword());
        System.out.println(registerDto.getPasswordConfirmation());
        if(!registerDto.getPassword().equals(registerDto.getPasswordConfirmation()))
        {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role userRole = roleRepository.findByName("DIPENDENTE").get();
    
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
