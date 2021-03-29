package pl.damian.beautyglow.service;

public interface EmailService {
    public void sendSimpleMessage(
            String to, String subject, String text);

    public String textRegisterMessage(
            String name, String email, String key);

    public String textResetMessage(
            String name, String email, String key);
}
