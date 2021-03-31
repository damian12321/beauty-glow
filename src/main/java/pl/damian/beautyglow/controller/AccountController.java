package pl.damian.beautyglow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.entity.UsersTreatments;
import pl.damian.beautyglow.service.UserService;
import pl.damian.beautyglow.user.NewUser;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/myAccount")
public class AccountController {
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        userService.updateData(user);
        return "my-account-info";

    }

    @GetMapping("/changePassword")
    public String changePassword(@RequestParam("email") String email,
                                 Model theModel) {
        User user = userService.findByEmailAddress(email);
        NewUser newUser = new NewUser();
        newUser.setEmail(email);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setDate(user.getDate());
        theModel.addAttribute("newUser", newUser);
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
        if (!passwordEncoder.matches(theNewUser.getOldPassword(),userOldPassword)) {
            theModel.addAttribute("registrationError", "Stare hasło jest nie prawidłowe.");
            return "change-password";
        }
        user.setPassword(passwordEncoder.encode(theNewUser.getPassword()));
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
            System.out.println(theBindingResult.getAllErrors());
            return "change-email";
        }
        User existing = userService.findByEmailAddress(theNewUser.getEmail());
        if (existing != null){
            System.out.println("mamy to");
            theModel.addAttribute("newUser", theNewUser);
            theModel.addAttribute("registrationError", "Adres email już istnieje.");
            return "change-email";
        }
        User user=userService.findByEmailAddress(theNewUser.getOldEmail());
        user.setEmail(theNewUser.getEmail());
        userService.changeEmail(user);
        return "registration-confirmation";
    }

    @GetMapping("/myActualVisits")
    public String showMyActualVisits(Authentication authentication, Model theModel) {
        String email = authentication.getName();
        User theUser = userService.findByEmailAddress(email);
        List<UsersTreatments> actualTreatments=new ArrayList<>();
        for(UsersTreatments usersTreatments:theUser.getUsersTreatments()){
            if(usersTreatments.getStatus().equals("planned"))
            {
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
        List<UsersTreatments> historyTreatments=new ArrayList<>();
        for(UsersTreatments usersTreatments:theUser.getUsersTreatments()){
            if(!usersTreatments.getStatus().equals("planned"))
            {
                historyTreatments.add(usersTreatments);
            }
        }
        theModel.addAttribute("historyTreatments", historyTreatments);
        return "my-visits-history";
    }

    @GetMapping("/orderVisit")
    public String orderVisit() {
        return "order-visit";
    }
}
