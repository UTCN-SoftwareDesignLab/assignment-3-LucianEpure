package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import dto.UserDto;
import entity.User;
import service.user.UserService;
import validators.Notification;



@Controller
@RequestMapping(value = "/admin")
public class AdminController {

private UserService userService;
	
	
	@Autowired
	public AdminController(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping()
	@Order(value = 1)
	 public String displayMenu( Model model) {	
		model.addAttribute(new UserDto());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		model.addAttribute("username", name);
		return "administrator";
	    }
	
	@PostMapping(params = "addUser")
	public String addUser(@ModelAttribute UserDto user, @RequestParam("type") String type,Model model){
		Notification<Boolean> notification = userService.save(user,type);
		//model.addAttribute(new UserDto());	
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
		return "administrator";
	}
	
	@PostMapping(value = "/showUsers",params="showUsers")
	 public String findAll(Model model) {
	        List<UserDto> users = userService.findAll();
	        model.addAttribute("users", users);
	        return "showUsers";
	    }
	
	@PostMapping(params="deleteUser")
	 public String delete( @RequestParam("deleteId") String deleteId, Model model) {
			userService.fireById(Integer.parseInt(deleteId));
			return "redirect:/admin";
	    }
	
	@PostMapping(params = "updateUser")
	//public String updateUser(@RequestParam("updateId") String updateId,@RequestParam("newUsername") String newUsername,@RequestParam Model model){
	public String updateUser(@ModelAttribute UserDto user, Model model){
		Notification<Boolean> notification = userService.update(user);
		//	model.addAttribute(new UserDto());	
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
		return "administrator";
	}
	
	 @PostMapping(params="logout")
	    public String logout(HttpServletRequest request, HttpServletResponse response){
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null){
	            new SecurityContextLogoutHandler().logout(request, response, auth);
	        }
	        return "redirect:/login";
	}
	 
	 
}
