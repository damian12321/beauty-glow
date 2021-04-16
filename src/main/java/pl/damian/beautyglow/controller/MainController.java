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

}










