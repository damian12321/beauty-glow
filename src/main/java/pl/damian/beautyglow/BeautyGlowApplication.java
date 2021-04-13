package pl.damian.beautyglow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
public class BeautyGlowApplication {

    public static void main(String[] args) {

        SpringApplication.run(BeautyGlowApplication.class, args);

    }
}
