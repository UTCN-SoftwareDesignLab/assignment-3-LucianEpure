package controller;

import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@GetMapping()
	@Order(value = 1)
	 public String displayMenu( Model model,HttpSession session) {	
					return "administrator";
	    }
}
