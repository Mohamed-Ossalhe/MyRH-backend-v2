package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.RegisterRequest;
import ma.youcode.myrhbackendapi.dto.requests.UserRequest;
import ma.youcode.myrhbackendapi.dto.requests.VerificationCodeRequest;
import ma.youcode.myrhbackendapi.dto.responses.AuthResponse;
import ma.youcode.myrhbackendapi.dto.responses.UserResponse;

import java.util.Optional;

public interface AuthService {
    public Optional<AuthResponse> login(UserRequest userRequest);
    public Optional<AuthResponse> register(RegisterRequest userRequest);
    public Optional<UserResponse> verifyAccount(String id, String code);
}
