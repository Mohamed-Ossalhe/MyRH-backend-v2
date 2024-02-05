package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.responses.VerificationCodeResponse;
import ma.youcode.myrhbackendapi.entities.User;
import ma.youcode.myrhbackendapi.entities.VerificationCode;

import java.util.Optional;

public interface VerificationCodeService {
    public Optional<VerificationCodeResponse> generateCode(String email);
    public Optional<VerificationCode> verifyCode(User id, String code);
    public Optional<VerificationCodeResponse> save(User recruiter, VerificationCodeResponse verificationCode);
}
