package ma.youcode.myrhbackendapi.services;

public interface EmailService {
    public void send(String email, String subject, String text);
}
