package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.responses.VerificationCodeResponse;
import ma.youcode.myrhbackendapi.entities.User;
import ma.youcode.myrhbackendapi.entities.VerificationCode;
import ma.youcode.myrhbackendapi.exceptions.InvalidVerificationCodeException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.exceptions.TokenExpirationException;
import ma.youcode.myrhbackendapi.repositories.VerificationCodeRepository;
import ma.youcode.myrhbackendapi.services.VerificationCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final ModelMapper mapper;

    @Override
    public Optional<VerificationCodeResponse> generateCode(String email) {
        String sub = email.substring(0, 4) + UUID.randomUUID().toString().replace("-", "");
        VerificationCodeResponse response = VerificationCodeResponse.builder()
                .code(sub.substring(0, 10))
                .expiration(LocalDateTime.now().plusMinutes(3))
                .build();
        return Optional.of(response);
    }

    @Override
    public Optional<VerificationCode> verifyCode(User user, String code) {
        VerificationCode verificationCode = verificationCodeRepository.findVerificationCodeByUserAndCode(user, code)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Validation Code"));

        if (verificationCode.isUsed()) throw new InvalidVerificationCodeException("Verification Code is Already Used");
        if (LocalDateTime.now().isBefore(verificationCode.getExpiration().minusMinutes(3))) throw new InvalidVerificationCodeException("Verification Code is Invalid");
        if (LocalDateTime.now().isAfter(verificationCode.getExpiration())) throw new TokenExpirationException("Verification Code is Expired");

        verificationCode.setUsed(true);
        verificationCodeRepository.save(verificationCode);
        return Optional.of(verificationCode);
    }

    @Override
    public Optional<VerificationCodeResponse> save(User user, VerificationCodeResponse code) {
        VerificationCode verificationCode = mapper.map(code, VerificationCode.class);
        verificationCode.setUser(user);
        VerificationCode savedVerificationCode = verificationCodeRepository.save(verificationCode);
        return Optional.of(mapper.map(savedVerificationCode, VerificationCodeResponse.class));
    }
}
