package ma.youcode.myrhbackendapi.dto.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    private UUID id;
    private String fullName;
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "Email is required")
    @Email(message = "Please Enter a valid Email")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotNull(message = "password cannot be null")
    private String password;
    private String phoneNumber;
    private String role;
    private boolean isVerified = false;
}
