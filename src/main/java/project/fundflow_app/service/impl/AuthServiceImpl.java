package project.fundflow_app.service.impl;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.fundflow_app.dto.auth.AuthResponse;
import project.fundflow_app.dto.auth.LoginRequest;
import project.fundflow_app.dto.auth.RegisterRequest;
import project.fundflow_app.entity.Role;
import project.fundflow_app.repository.RoleRepository;
import project.fundflow_app.repository.UserRepository;
import project.fundflow_app.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return new AuthResponse(null, "Email is already registered.");
        }
        Role userRole  = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        project.fundflow_app.entity.User user = new project.fundflow_app.entity.User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return new AuthResponse(null , "User Registered Successfully.");
        

    }

    @Override
    public AuthResponse login(LoginRequest request){
            project.fundflow_app.entity.User user = userRepository.findByEmail(request.getEmail())
                            .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
                                                
            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                throw new RuntimeException("Invalid Credentials");
            }
            return new AuthResponse(null, "Login Successful.");
    }
}
