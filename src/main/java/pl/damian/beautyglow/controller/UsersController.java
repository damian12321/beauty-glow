package pl.damian.beautyglow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.entity.UsersTreatments;
import pl.damian.beautyglow.service.UserService;
import pl.damian.beautyglow.service.UsersTreatmentsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")

public class UsersController {
    @Autowired
    UserService userService;
    @Autowired
    UsersTreatmentsService usersTreatmentsService;

    @GetMapping("/usersVisits")
    public String getUsersVisits() {
        return "users-visits";
    }

    @GetMapping("/checkVisits")
    public String checkVisits(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Model theModel) {
        List<UsersTreatments> usersTreatmentsList = usersTreatmentsService.getUsersTreatmentsOnSpecificDay(date);
        theModel.addAttribute("usersTreatmentsList", usersTreatmentsList);
        theModel.addAttribute("date", date);
        return "all-visits-on-day";
    }

    @GetMapping("/usersList")
    public String usersList(Model theModel) {
        List<User> userList = userService.getAllUsers();
        theModel.addAttribute("userList", userList);
        return "users-list";
    }
    @GetMapping("/userActualVisits")
    public String userActualVisits(@RequestParam("email") String email, Model theModel) {
        User user=userService.findByEmailAddress(email);
        List<UsersTreatments> actualVisits=new ArrayList<>();
        for(UsersTreatments usersTreatments:user.getUsersTreatments())
        {
            if(usersTreatments.getStatus().equals("planned"))
            {
                actualVisits.add(usersTreatments);
            }
        }
        theModel.addAttribute("user", user);
        theModel.addAttribute("userActualVisits", actualVisits);
        return "users-actual-visits";
    }
    @GetMapping("/userHistoryVisits")
    public String userHistoryVisits(@RequestParam("email") String email,Model theModel) {
        User user=userService.findByEmailAddress(email);
        List<UsersTreatments> historyVisits=new ArrayList<>();
        for(UsersTreatments usersTreatments:user.getUsersTreatments())
        {
            if(!usersTreatments.getStatus().equals("planned"))
            {
                historyVisits.add(usersTreatments);
            }
        }
        theModel.addAttribute("user", user);
        theModel.addAttribute("historyVisits", historyVisits);
        return "users-history-visits";
    }
}
