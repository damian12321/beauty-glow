package pl.damian.beautyglow.service;


import pl.damian.beautyglow.entity.UsersTreatments;

public interface EmailService {

     void sendSimpleMessage(
            String to, String subject, String text);

     String textRegisterMessage(
            String name, String email, String key);

     String textResetMessage(
            String name, String email, String key);

     String textCancelVisitMessage(
            String name, String email, UsersTreatments UsersTreatments);
}
