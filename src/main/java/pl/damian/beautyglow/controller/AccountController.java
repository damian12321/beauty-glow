package pl.damian.beautyglow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damian.beautyglow.entity.Form;
import pl.damian.beautyglow.entity.Treatment;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.entity.UsersTreatments;
import pl.damian.beautyglow.service.TreatmentService;
import pl.damian.beautyglow.service.UserService;
import pl.damian.beautyglow.service.UsersTreatmentsService;
import pl.damian.beautyglow.user.NewUser;
import pl.damian.beautyglow.utils.AccountHelper;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/myAccount")
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private TreatmentService treatmentService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UsersTreatmentsService usersTreatmentsService;

    @GetMapping("/info")
    public String showAccount(Authentication authentication, Model theModel) {
        String email = authentication.getName();
        User theUser = userService.findByEmailAddress(email);
        theModel.addAttribute("user", theUser);
        return "my-account-info";
    }

    @GetMapping("/editData")
    public String editData(@RequestParam("email") String email, Model theModel) {
        User user = userService.findByEmailAddress(email);
        theModel.addAttribute("user", user);
        return "edit-data";
    }

    @PostMapping("/updateData")
    public String updateData(@Valid @ModelAttribute("user") User user,
                             BindingResult theBindingResult) {

        if (theBindingResult.hasErrors()) {
            return "edit-data";
        }
        User oldUser = userService.findByEmailAddress(user.getEmail());
        user.setRoles(oldUser.getRoles());
        userService.updateData(user);
        return "my-account-info";
    }

    @GetMapping("/changePassword")
    public String changePassword(@RequestParam("email") String email,
                                 Model theModel) {
        AccountHelper.transformUserToNewUser(email, theModel, userService);
        return "change-password";
    }

    @PostMapping("/processPasswordChange")
    public String processPasswordChange(@Valid @ModelAttribute("newUser") NewUser theNewUser,
                                        BindingResult theBindingResult,
                                        Model theModel) {
        if (theBindingResult.hasErrors()) {
            return "change-password";
        }
        User user = userService.findByEmailAddress(theNewUser.getEmail());
        String userOldPassword = user.getPassword();

        if (!passwordEncoder.matches(theNewUser.getOldPassword(), userOldPassword)) {
            theModel.addAttribute("registrationError", "Stare hasło jest nie prawidłowe.");
            return "change-password";
        }
        user.setPassword(passwordEncoder.encode(theNewUser.getPassword()));
        User oldUser = userService.findByEmailAddress(user.getEmail());
        user.setForm(oldUser.getForm());
        user.setRoles(oldUser.getRoles());
        userService.updateData(user);
        return "password-changed-confirm";
    }

    @GetMapping("/changeEmail")
    public String changeEmail(@RequestParam("email") String email,
                              Model theModel) {
        User user = userService.findByEmailAddress(email);
        NewUser newUser = new NewUser();
        newUser.setOldEmail(email);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setDate(user.getDate());
        newUser.setPassword(user.getPassword());
        newUser.setMatchingPassword(user.getPassword());
        theModel.addAttribute("newUser", newUser);
        return "change-email";
    }

    @PostMapping("/processChangingEmail")
    public String processChangingEmail(@Valid @ModelAttribute("newUser") NewUser theNewUser,
                                       BindingResult theBindingResult,
                                       Model theModel) {
        if (theBindingResult.hasErrors()) {

            return "change-email";
        }
        User existing = userService.findByEmailAddress(theNewUser.getEmail());
        if (existing != null) {
            theModel.addAttribute("newUser", theNewUser);
            theModel.addAttribute("registrationError", "Adres email już istnieje.");
            return "change-email";
        }
        User user = userService.findByEmailAddress(theNewUser.getOldEmail());
        user.setEmail(theNewUser.getEmail());
        userService.changeEmail(user, false);
        return "registration-confirmation";
    }

    @GetMapping("/myActualVisits")
    public String showMyActualVisits(Authentication authentication, Model theModel) {
        String email = authentication.getName();
        User theUser = userService.findByEmailAddress(email);
        List<UsersTreatments> actualTreatments = new ArrayList<>();
        for (UsersTreatments usersTreatments : theUser.getUsersTreatments()) {
            if (usersTreatments.getStatus().equals("planned")) {
                actualTreatments.add(usersTreatments);
            }
        }
        theModel.addAttribute("actualTreatments", actualTreatments);
        return "my-actual-visits";
    }

    @GetMapping("/visitsHistory")
    public String showMyHistoryVisits(Authentication authentication, Model theModel) {
        String email = authentication.getName();
        User theUser = userService.findByEmailAddress(email);
        List<UsersTreatments> historyTreatments = new ArrayList<>();
        for (UsersTreatments usersTreatments : theUser.getUsersTreatments()) {
            if (!usersTreatments.getStatus().equals("planned")) {
                historyTreatments.add(usersTreatments);
            }
        }
        theModel.addAttribute("historyTreatments", historyTreatments);
        return "my-visits-history";
    }

    @GetMapping("/newVisit")
    public String newVisit(Authentication authentication, Model theModel) {
        String email = authentication.getName();
        User theUser = userService.findByEmailAddress(email);
        if (theUser.getForm() == null) {

            return "redirect:/myAccount/userForm";
        }
        List<Treatment> treatmentList = treatmentService.getTreatments();
        theModel.addAttribute("treatmentList", treatmentList);
        return "new-visit";
    }

    @GetMapping("/bookVisit")
    public String orderVisit(@RequestParam("treatmentId") int id, Authentication authentication, Model theModel) {
        String email = authentication.getName();
        User theUser = userService.findByEmailAddress(email);
        if (theUser.getForm() == null) {
            return "redirect:/myAccount/userForm";
        }
        Treatment treatment = treatmentService.getTreatment(id);
        LocalDate now = LocalDate.now();
        now = now.plusDays(1);
        LocalDate max = now.plusYears(2L);
        theModel.addAttribute("now", now);
        theModel.addAttribute("max", max);
        theModel.addAttribute("treatment", treatment);
        theModel.addAttribute("date", new Date());
        return "book-a-visit";
    }

    @GetMapping("/checkBookingHours")
    public String checkBookingHours(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                    @RequestParam("treatmentId") int id,
                                    Model theModel) {
        Treatment treatment = treatmentService.getTreatment(id);

        int hoursOfWork = 8;
        int day = date.getDay();
        {
            if (day == 0 || date.before(new Date())) {
                hoursOfWork = 0;
            } else if (day == 6) {
                hoursOfWork = 6;
            }
        }

        int startHourOfWork = 8;
        List<Date> allHours = new ArrayList<>();
        List<Date> bookedHours = new ArrayList<>();
        LinkedHashMap<Date, Integer> tempAvailableHours = new LinkedHashMap<>();
        List<Date> availableHours = new ArrayList<>();
        List<UsersTreatments> usersTreatmentsList = usersTreatmentsService.getUsersTreatmentsOnSpecificDay(date);
        getHoursOfWork(hoursOfWork, startHourOfWork, allHours);

        getBookedHours(bookedHours, usersTreatmentsList);
        StringBuilder sb = new StringBuilder();
        for (Date date2 : allHours) {
            boolean reserved = false;
            for (Date date1 : bookedHours) {
                if ((date1.getMinutes() == date2.getMinutes()) && date1.getHours() == date2.getHours()) {
                    reserved = true;
                    break;
                }
            }
            if (reserved) {
                sb.append("r");
            } else {
                sb.append("f");
            }
        }
        int iterationsNeeded = AccountHelper.getAvailableHours(treatment, allHours, tempAvailableHours, sb);
        for (Map.Entry<Date, Integer> map : tempAvailableHours.entrySet()) {
            if (map.getValue() >= iterationsNeeded) {
                availableHours.add(map.getKey());
            }
        }
        Collections.sort(availableHours, (o1, o2) -> (int) (o1.getTime() - o2.getTime()));

        theModel.addAttribute("date", date);
        theModel.addAttribute("availableHours", availableHours);
        theModel.addAttribute("treatment", treatment);
        if (availableHours.isEmpty()) {
            return "no-visits-available";
        }
        return "visit-available-hours";
    }

    private void getBookedHours(List<Date> bookedHours, List<UsersTreatments> usersTreatmentsList) {
        for (UsersTreatments usersTreatments : usersTreatmentsList) {
            Date startDate = usersTreatments.getDate();
            startDate.setSeconds(0);
            for (int i = 0; i < usersTreatments.getTreatment().getDuration(); i += 15) {
                int tempMinutes = i % 60;
                int tempHour = i / 60;
                Date tempDate = new Date();
                tempDate.setTime(startDate.getTime());
                tempDate.setMinutes(tempDate.getMinutes() + tempMinutes);
                tempDate.setHours(tempDate.getHours() + tempHour);
                bookedHours.add(tempDate);
            }
        }
    }

    private void getHoursOfWork(int hoursOfWork, int startHourOfWork, List<Date> allHours) {
        for (int i = 0; i < hoursOfWork * 60; i += 15) {
            int tempMinutes = i % 60;
            int tempHour = i / 60;
            Date tempDate = new Date();
            tempDate.setSeconds(0);
            tempDate.setMinutes(tempMinutes);
            tempDate.setHours(tempHour + startHourOfWork);
            allHours.add(tempDate);
        }
    }

    @PostMapping("/processBookingVisit")
    public String processBookingVisit(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                      @RequestParam("hour") String hour, @RequestParam("minutes") String minutes,
                                      @RequestParam("treatmentId") int id, Authentication authentication,
                                      Model theModel) {

        date.setHours(Integer.parseInt(hour));
        date.setMinutes(Integer.parseInt(minutes));
        User user = userService.findByEmailAddress(authentication.getName());
        Treatment treatment = treatmentService.getTreatment(id);
        UsersTreatments usersTreatments = new UsersTreatments(user, treatment, date, "planned");
        theModel.addAttribute("usersTreatments", usersTreatments);
        return "confirm-a-visit";
    }

    @PostMapping("/confirmVisit")
    public String confirmVisit(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                               @RequestParam("hour") String hour, @RequestParam("minutes") String minutes,
                               @RequestParam("treatmentId") int id, Authentication authentication,
                               Model theModel) {
        int startHourOfWork = 8;
        int hoursOfWork = 8;
        int day = date.getDay();
        {
            if (day == 0) {
                hoursOfWork = 0;
            } else if (day == 6) {
                hoursOfWork = 6;
            }
        }
        Treatment treatment = treatmentService.getTreatment(id);
        List<Date> allHours = new ArrayList<>();
        List<Date> bookedHours = new ArrayList<>();
        LinkedHashMap<Date, Integer> tempAvailableHours = new LinkedHashMap<>();
        List<UsersTreatments> usersTreatmentsList = usersTreatmentsService.getUsersTreatmentsOnSpecificDay(date);
        date.setHours(Integer.parseInt(hour));
        date.setMinutes(Integer.parseInt(minutes));
        StringBuilder sb = new StringBuilder();
        getHoursOfWork(hoursOfWork, startHourOfWork, allHours);
        boolean isAvailable = false;
        getBookedHours(bookedHours, usersTreatmentsList);
        for (Date date5 : allHours) {
            boolean reserved = false;
            for (Date date1 : bookedHours) {
                if ((date1.getMinutes() == date5.getMinutes()) && date1.getHours() == date5.getHours())
                    reserved = true;
            }
            if (reserved) {
                sb.append("r");
            } else {
                sb.append("f");
            }
        }
        int iterationsNeeded = AccountHelper.getAvailableHours(treatment, allHours, tempAvailableHours, sb);
        for (Map.Entry<Date, Integer> map : tempAvailableHours.entrySet()) {
            if ((date.getMinutes() == map.getKey().getMinutes()) && (date.getHours() == map.getKey().getHours())) {
                if (map.getValue() >= iterationsNeeded) {

                    isAvailable = true;
                }
            }
        }
        theModel.addAttribute("treatment", treatment);
        if (!isAvailable) {
            return "already-booked";
        }
        User user = userService.findByEmailAddress(authentication.getName());
        UsersTreatments usersTreatments = new UsersTreatments(user, treatment, date, "planned");
        usersTreatmentsService.addUsersTreatments(usersTreatments);
        theModel.addAttribute("usersTreatments", usersTreatments);
        return "visit-confirmed";
    }

    @PostMapping("/cancelVisit")
    public String cancelVisit(@RequestParam("usersTreatmentsId") int id, Model theModel) {
        UsersTreatments usersTreatments = usersTreatmentsService.getUsersTreatmentsById(id);
        usersTreatments.setStatus("cancelled");
        theModel.addAttribute("usersTreatments", usersTreatments);
        usersTreatmentsService.updateUsersTreatments(usersTreatments);
        return "cancel-confirmed";
    }

    @GetMapping("/userForm")
    public String userForm(Model theModel) {
        Form form = new Form();
        theModel.addAttribute("form", form);
        return "user-form";
    }

    @GetMapping("/editUserForm")
    public String editUserForm(Authentication authentication, Model theModel) {
        User user = userService.findByEmailAddress(authentication.getName());
        Form form = user.getForm();
        theModel.addAttribute("form", form);
        return "user-form";
    }

    @PostMapping("/saveUserForm")
    public String userForm(@Valid @ModelAttribute Form form, Authentication authentication) {
        User user = userService.findByEmailAddress(authentication.getName());
        form.setId(user.getId());
        user.setForm(form);
        userService.updateData(user);
        return "redirect:/myAccount/info";
    }
}
