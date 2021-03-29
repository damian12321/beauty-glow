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
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    @Autowired
    private UserService userService;
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("newUser", new NewUser());
		
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("newUser") NewUser theNewUser,
				BindingResult theBindingResult, 
				Model theModel) {
		
		String email = theNewUser.getEmail();

		 if (theBindingResult.hasErrors()){
			 return "registration-form";
	        }

        User existing = userService.findByEmailAddress(email);
        if (existing != null){
        	theModel.addAttribute("newUser", new NewUser());
			theModel.addAttribute("registrationError", "Adres email już istnieje.");

			logger.warning("Email address already exists.");
        	return "registration-form";
        }
        

        userService.save(theNewUser);
        
        logger.info("Successfully created user: " + email);
        
        return "registration-confirmation";		
	}
	@GetMapping("/validate/{email}/{key}")
	public String validateEmail(@PathVariable String email,@PathVariable String key) {
		if(userService.validateEmailAddress(email,key))
		{
			return "activation-confirm";
		}else
		{
			return "activation-denied";
		}
	}
}
