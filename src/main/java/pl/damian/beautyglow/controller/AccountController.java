package pl.damian.beautyglow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.service.UserService;
import javax.validation.Valid;

@Controller
@RequestMapping("/myAccount")
public class AccountController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public String showAccount(Authentication authentication, Model theModel) {
        String email = authentication.getName();
        User theUser = userService.findByEmailAddress(email);
        theModel.addAttribute("user",theUser);
        return "my-account-info";
    }
    @GetMapping("/editData")
    public String editData(@RequestParam("email") String email,Model theModel) {
        User user = userService.findByEmailAddress(email);
        theModel.addAttribute("user",user);
        return "edit-data";
    }
    @PostMapping("/updateData")
    public String updateData(@Valid @ModelAttribute("user") User user,
        BindingResult theBindingResult) {

        if (theBindingResult.hasErrors()){
                return "edit-data";
        }
        userService.update(user);
        return "my-account-info";

    }
    @GetMapping("/changePassword")
    public String changePassword() {

        return "change-password";
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
