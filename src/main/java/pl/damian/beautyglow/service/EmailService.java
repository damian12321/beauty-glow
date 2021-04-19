package pl.damian.beautyglow.service;


import pl.damian.beautyglow.entity.UsersTreatments;

public interface EmailService {
    public void sendSimpleMessage(
            String to, String subject, String text);

    public String textRegisterMessage(
            String name, String email, String key);

    public String textResetMessage(
            String name, String email, String key);

    public String textCancelVisitMessage(
            String name, String email, UsersTreatments UsersTreatments);
}
