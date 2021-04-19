package pl.damian.beautyglow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.damian.beautyglow.entity.UsersTreatments;

import java.text.SimpleDateFormat;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("springstudent123@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public String textRegisterMessage(String name, String email, String key) {
        String text = "Witaj " + name + "," + "\n"
                + "kliknij w poniższy link aby aktywować swoje konto na stronie localhost:8080." + "\n"
                + "http://localhost:8080/register/validate/" + email + "/" + key;
        return text;
    }

    @Override
    public String textResetMessage(String name, String email, String key) {
        String text = "Witaj " + name + "," + "\n"
                + "kliknij w poniższy link aby zresetować swoje hasło na stronie localhost:8080." + "\n"
                + "http://localhost:8080/reset/" + email + "/" + key;
        return text;
    }

    @Override
    public String textCancelVisitMessage(String name, String email, UsersTreatments usersTreatments) {
        String pattern = "dd.MM.yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(usersTreatments.getDate());
        String text = "Witaj " + name + "," + "\n"
                + "Z przykrością musimy Cię poinformować o odwołaniu Twojej wizyty w terminie " + date + ".";
        return text;
    }
}