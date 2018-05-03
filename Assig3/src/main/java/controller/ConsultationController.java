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

import dto.ConsultationDto;
import dto.PatientDto;
import dto.UserDto;
import service.consultation.ConsultationService;
import service.patient.PatientService;
import service.user.UserService;
import validators.Notification;

@Controller
@RequestMapping(value = "/secretary/consultation")
public class ConsultationController {

	private ConsultationService consultationService;
	private PatientService patientService;
	private UserService userService;
	
	@Autowired
	public ConsultationController(ConsultationService consultationService,PatientService patientService, UserService userService){
		this.consultationService = consultationService;
		this.patientService = patientService;
		this.userService = userService;
	}
	
	@GetMapping()
	@Order(value = 1)
	 public String displayMenu( Model model) {	
		model.addAttribute("consultationDto", new ConsultationDto());
		return "consultation";
	    }
	
	@PostMapping(params = "addConsultation")
	public String addConsultation(@ModelAttribute ConsultationDto consultation,Model model){
		Notification<Boolean> notification = consultationService.addConsultation(consultation);
		//model.addAttribute(new UserDto());	
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
		return "consultation";
	}
	
	@PostMapping(value = "/showPatients",params="showPatients")
	 public String findAllPatients(Model model) {
	        List<PatientDto> patients = patientService.findAll();
	        model.addAttribute("patients", patients);
	        return "showPatients";
	    }
	
	@PostMapping(value = "/showDoctors",params="showDoctors")
	 public String findAllDoctors(Model model) {
	       List<UserDto> doctors = consultationService.findAllDoctors();
	       model.addAttribute("doctors", doctors);
	        return "showDoctors";
	    }
	
	@PostMapping(value = "/showConsultations",params="showConsultations")
	 public String findAllConsultations(Model model) {
	       List<ConsultationDto> consultations = consultationService.findAllConsultations();
	       model.addAttribute("consultations", consultations);
	        return "showConsultations";
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
