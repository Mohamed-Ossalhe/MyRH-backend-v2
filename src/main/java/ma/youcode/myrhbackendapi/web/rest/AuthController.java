package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.RegisterRequest;
import ma.youcode.myrhbackendapi.dto.requests.UserRequest;
import ma.youcode.myrhbackendapi.dto.requests.VerificationCodeRequest;
import ma.youcode.myrhbackendapi.dto.responses.AuthResponse;
import ma.youcode.myrhbackendapi.dto.responses.UserResponse;
import ma.youcode.myrhbackendapi.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authentication(@Valid @RequestBody UserRequest request) {
        Optional<AuthResponse> response = authService.login(request);
        assert response.isPresent();
        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @ModelAttribute RegisterRequest request) {
        Optional<AuthResponse> response = authService.register(request);
        assert response.isPresent();
        return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
    }

    // TODO: make this method accept a parameter
    @PostMapping("/{id}/verify-account/{code}")
    public ResponseEntity<UserResponse> verifyAccount(@PathVariable String id, @PathVariable String code) {
        Optional<UserResponse> response = authService.verifyAccount(id, code);
        assert response.isPresent();
        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }
}
