package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ConsultationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestParam;
import service.consultation.ConsultationService;
import service.user.UserService;

import java.util.List;

@Controller

public class DoctorController {

	private ConsultationService consultationService;
	private UserService userService;
	@Autowired
	public DoctorController(ConsultationService consultationService, UserService userService){
		this.consultationService = consultationService;
		this.userService = userService;
	}

	@GetMapping(value = "/doctor")
	@Order(value = 1)
	 public String displayMenu( Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		model.addAttribute("username", name);
					return "doctor";
	}

	@PostMapping(value = "/doctorConsultations", params = "showConsultations")
	public String viewScheduledConsultations( Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		int doctorId = userService.findByUsername(name).getId();
		List<ConsultationDto> consultations = consultationService.seeDoctorSchedule(doctorId);
		model.addAttribute("consultations",consultations);
		return  "doctorConsultations";
	}

	@PostMapping( value = "/doctor", params = "diagnose")
	public String diagnose(@RequestParam("consultationId") String consultationId,@RequestParam("diagnostic") String diagnostic){
 		consultationService.diagnose(Integer.parseInt(consultationId),diagnostic);
		return  "doctor";
	}


	@PostMapping(value = "/doctor", params="logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}
}
