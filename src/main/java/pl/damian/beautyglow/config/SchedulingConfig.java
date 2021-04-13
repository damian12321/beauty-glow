package pl.damian.beautyglow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.damian.beautyglow.entity.UsersTreatments;
import pl.damian.beautyglow.service.UsersTreatmentsService;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Autowired
    UsersTreatmentsService usersTreatmentsService;
    @Scheduled(cron = "0 */15 * * * *", zone = "Europe/Warsaw")
    public void scheduleFixedRateTask() {
       List<UsersTreatments> usersTreatmentsList=usersTreatmentsService.getUsersTreatments();
       Date date=new Date();
       for(UsersTreatments usersTreatments:usersTreatmentsList)
       {
           if(usersTreatments.getDate().before(date)&&usersTreatments.getStatus().equals("planned"))
           {
               usersTreatments.setStatus("finished");
               usersTreatmentsService.updateUsersTreatments(usersTreatments);
           }
       }
    }
}