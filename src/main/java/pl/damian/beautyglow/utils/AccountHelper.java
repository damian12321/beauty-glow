package pl.damian.beautyglow.utils;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import pl.damian.beautyglow.entity.Treatment;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.service.UserService;
import pl.damian.beautyglow.user.NewUser;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class AccountHelper {
    public static void transformUserToNewUser(@RequestParam("email") String email, Model theModel, UserService userService) {
        User user = userService.findByEmailAddress(email);
        NewUser newUser = new NewUser();
        newUser.setEmail(email);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setDate(user.getDate());
        theModel.addAttribute("newUser", newUser);
    }
    public static int getAvailableHours(Treatment treatment, List<Date> allHours, LinkedHashMap<Date, Integer> tempAvailableHours, StringBuilder sb) {
        int temp1 = 0;
        for (int i = sb.toString().length() - 1; i >= 0; i--) {
            if (sb.toString().charAt(i) == 'f') {
                temp1++;
            } else {
                temp1 = 0;
            }
            tempAvailableHours.put(allHours.get(i), temp1);
        }
        return treatment.getDuration() / 15;
    }
}
