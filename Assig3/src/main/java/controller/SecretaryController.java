package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import dto.ConsultationDto;
import dto.PatientDto;
import dto.UserDto;
import entity.Patient;
import entity.User;
import service.consultation.ConsultationService;
import service.patient.PatientService;
import validators.Notification;

@Controller
@RequestMapping(value = "/secretary")
public class SecretaryController {

private PatientService patientService;
//private ConsultationService consultationService;

@Autowired
public SecretaryController (PatientService patientService)
{
	this.patientService = patientService;
	//this.consultationService = consultationService;
}
	@GetMapping()
	@Order(value = 1)
	 public String displayMenu( Model model) {	
		model.addAttribute("patientDto", new PatientDto());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		model.addAttribute("username", name);
		return "secretary";
	    }
	
	@PostMapping(params = "addPatient")
	public String addPatient(@ModelAttribute PatientDto patient,Model model){
		Notification<Boolean> notification = patientService.save(patient);
		//model.addAttribute(new UserDto());	
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
		return "secretary";
	}
	
	@PostMapping(value = "/showPatients",params="showPatients")
	 public String findAll(Model model) {
	        List<PatientDto> patients = patientService.findAll();
	        model.addAttribute("patients", patients);
	        return "showPatients";
	    }
	
	
	@PostMapping(params="deletePatient")
	 public String delete( @RequestParam("deleteId") String deleteId, Model model) {
			patientService.delete(Integer.parseInt(deleteId));
			return "redirect:/secretary";
	    }
	
	@PostMapping(params = "updatePatient")
	//public String updateUser(@RequestParam("updateId") String updateId,@RequestParam("newUsername") String newUsername,@RequestParam Model model){
	public String updatePatient(@ModelAttribute PatientDto patient, Model model){
		Notification<Boolean> notification = patientService.update(patient);
		//	model.addAttribute(new UserDto());	
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully updated!");
		return "secretary";
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
