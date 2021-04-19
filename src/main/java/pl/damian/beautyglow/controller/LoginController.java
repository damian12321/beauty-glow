package pl.damian.beautyglow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
public class LoginController {
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

    @GetMapping("/passwordRemind")
    public String showPasswordRemind(Model theModel) {
        theModel.addAttribute("email", "");
        return "password-remind";
    }

    @PostMapping("/remindConfirm")
    public String showRemindConfirm(@Valid @ModelAttribute("email") String email,
                                    BindingResult theBindingResult) {
        if (theBindingResult.hasErrors()) {
            return "password-remind";
        }
        boolean result = userService.remindPassword(email, false);
        if (result) {
            return "remind-confirm";
        } else {
            return "password-remind";
        }
    }

    @GetMapping("/reset/{email}/{key}")
    public String resetPassword(@PathVariable String email, @PathVariable String key, Model theModel) {
        if (userService.resetPassword(email, key)) {
            User user = userService.findByEmailAddress(email);
            NewUser newUser = new NewUser();
            newUser.setEmail(email);
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setDate(user.getDate());
            theModel.addAttribute("newUser", newUser);
            return "reset-password";
        } else {
            return "reset-denied";
        }
    }

    @PostMapping("/reset/resetConfirm")
    public String showResetConfirm(@Valid @ModelAttribute("newUser") NewUser newUser,
                                   BindingResult theBindingResult) {
        if (theBindingResult.hasErrors()) {
            return "reset-password";
        }
        boolean result = userService.changePassword(newUser.getEmail(), newUser.getPassword());
        if (result) {
            return "reset-confirm";
        }
        return "reset-password";
    }

}









