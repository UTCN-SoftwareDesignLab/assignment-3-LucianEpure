package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dto.ConsultationDto;
import entity.Consultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestParam;
import service.consultation.ConsultationService;
import service.user.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/doctor")
public class DoctorController {

	private ConsultationService consultationService;
	private UserService userService;
	@Autowired
	public DoctorController(ConsultationService consultationService, UserService userService){
		this.consultationService = consultationService;
		this.userService = userService;
	}

	@GetMapping()
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

	@PostMapping( params = "diagnose")
	public String diagnose(@RequestParam("consultationId") String consultationId,@RequestParam("diagnose") String diagnose){
 		consultationService.diagnose(Integer.parseInt(consultationId),diagnose);
		return  "doctor";
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
