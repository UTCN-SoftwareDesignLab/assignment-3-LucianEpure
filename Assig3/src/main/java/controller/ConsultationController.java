package controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Message;
import entity.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dto.ConsultationDto;
import dto.PatientDto;
import dto.UserDto;
import service.consultation.ConsultationService;
import service.patient.PatientService;
import service.user.UserService;
import validators.Notification;

@Controller
@RequestMapping(value = "/consultation")
public class ConsultationController {

	private ConsultationService consultationService;
	private PatientService patientService;
	private UserService userService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;


	@Autowired
	public ConsultationController(ConsultationService consultationService,PatientService patientService, UserService userService){
		this.consultationService = consultationService;
		this.patientService = patientService;
		this.userService = userService;
	}
	
	@GetMapping()
	 public String displayMenu( Model model) {	
		model.addAttribute("consultationDto", new ConsultationDto());
		return "consultation";
	    }

	@PostMapping(params = "addConsultation")
	@MessageMapping("/reminder")
	public String addConsultation(@ModelAttribute ConsultationDto consultation,Model model,Principal principal, Reminder reminder){
		Notification<Boolean> notification = consultationService.addConsultation(consultation);
		//model.addAttribute(new UserDto());	
		if(notification.hasErrors()) {
			model.addAttribute("valid", notification.getFormattedErrors());
		}
		else{
			model.addAttribute("valid", "Succesfully registered!");
			Message message = new Message();
			message.setContent("from "+principal.getName()+" patient:"+consultation.getPatientId()+" at date:" + consultation.getScheduledDate());

			messagingTemplate.convertAndSendToUser(userService.findById(consultation.getUserId()).getUsername(), "/queue/reply", message);
		}


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

	@PostMapping(params="deleteConsultation")
	public String delete( @RequestParam("deleteId") String deleteId, Model model) {
		consultationService.delete(Integer.parseInt(deleteId));
		return "redirect:/consultation";
	}

	@PostMapping(value = "/showConsultations", params="checkSchedule")
	public String check(@RequestParam("doctorId") String doctorId, Model model) {
		List<ConsultationDto> consultations = consultationService.seeDoctorSchedule(Integer.parseInt(doctorId));
		model.addAttribute("consultations", consultations);
		return "showConsultations";
	}

	@PostMapping(params = "updateConsultation")
	//public String updateUser(@RequestParam("updateId") String updateId,@RequestParam("newUsername") String newUsername,@RequestParam Model model){
	public String updateConsultation(@ModelAttribute ConsultationDto consultation, Model model){
		Notification<Boolean> notification = consultationService.update(consultation);
		//	model.addAttribute(new UserDto());
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully updated!");
		return "consultation";
	}
/*
	@MessageMapping("/consult")
	@SendTo("/topic/greetings")
	public Greeting greeting(ConsultationDto consultationDto) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(String.valueOf(consultationDto.getPatientId())) + "!");
	}
*/




	@PostMapping(params="logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
	}


}
