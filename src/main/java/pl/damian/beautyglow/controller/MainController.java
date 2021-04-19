package pl.damian.beautyglow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showMain() {

        return "index";
    }

    @GetMapping("/permanentMakeup")
    public String permanentMakeup() {

        return "permanent-makeup";
    }

    @GetMapping("/eyelashExtensions")
    public String eyelashExtensions() {

        return "eyelash-extensions";
    }

    @GetMapping("/lifting")
    public String lifting() {

        return "lifting";
    }

    @GetMapping("/makeup")
    public String makeup() {

        return "makeup";
    }

    @GetMapping("/priceList")
    public String priceList() {

        return "price-list";
    }

    @GetMapping("/contact")
    public String contact() {

        return "contact";
    }

}










