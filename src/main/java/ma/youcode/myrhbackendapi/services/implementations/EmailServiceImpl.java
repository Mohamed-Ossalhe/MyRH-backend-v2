package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.exceptions.SomethingWentWrongException;
import ma.youcode.myrhbackendapi.services.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Override
    public void send(String email, String subject, String text) {
        try {
            CompletableFuture.runAsync(() -> {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("contact.recrutement@myrh.ma");
                message.setSubject(subject);
                message.setText(text);
                message.setTo(email);
                javaMailSender.send(message);
            });
        }catch (Exception exception) {
            throw new SomethingWentWrongException("Something went wrong while trying to send email");
        }
    }
}
