package ma.youcode.myrhbackendapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeResponse {
    private UUID id;
    private String code;
    private LocalDateTime expiration;
}
