package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dto.UserDto;
//import validators.Notification;
import service.UserService;
import validators.Notification;


@Controller
@RequestMapping("/registration")
public class RegisterController {

private UserService userService;
	
	@Autowired
	public RegisterController(UserService userService){
		this.userService = userService;
	}
	@GetMapping()
	@Order(value = 1)
    public String displayMenu(Model model) {
		model.addAttribute(new UserDto());
		return "register";
	}
	@PostMapping(params="register")
    public String register( @ModelAttribute UserDto user, Model model) {	
		Notification<Boolean> notification = userService.save(user,"administrator");
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
        return "login";
	}
}
