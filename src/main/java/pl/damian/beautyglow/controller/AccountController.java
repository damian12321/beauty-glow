package pl.damian.beautyglow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myAccount")
public class AccountController {
    @GetMapping("/info")
    public String showAccount() {

        return "my-account-info";
    }
    @GetMapping("/editData")
    public String editData() {

        return "edit-data";
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
