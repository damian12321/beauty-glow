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
import pl.damian.beautyglow.service.UserService;
import pl.damian.beautyglow.user.NewUser;

import javax.validation.Valid;

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
    public String changeEmail() {

        return "change-email";
    }

    @GetMapping("/myActualVisits")
    public String showMyActualVisits() {

        return "my-actual-visits";
    }

    @GetMapping("/visitsHistory")
    public String showMyHistoryVisits() {

        return "my-visits-history";
    }

    @GetMapping("/orderVisit")
    public String orderVisit() {

        return "order-visit";
    }
}
