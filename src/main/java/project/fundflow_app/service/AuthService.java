package project.fundflow_app.service;

import project.fundflow_app.dto.auth.AuthResponse;
import project.fundflow_app.dto.auth.LoginRequest;
import project.fundflow_app.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
