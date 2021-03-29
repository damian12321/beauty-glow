package pl.damian.beautyglow.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String showMain() {
		
		return "index";
	}
	@GetMapping("/account")
	public String showAccount() {

		return "account";
	}

}










