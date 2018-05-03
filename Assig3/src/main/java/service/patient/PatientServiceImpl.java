package service.patient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import converter.PatientConverter;
import dto.PatientDto;
import dto.UserDto;
import entity.Patient;
import entity.User;
import entity.builder.PatientBuilder;
import repository.PatientRepository;
import validators.IValidator;
import validators.Notification;
import validators.PatientValidator;
import validators.UserValidator;

@Service
public class PatientServiceImpl implements PatientService{

	private PatientRepository patientRepository;
	private PatientConverter patientConverter;
	private IValidator validator;
	
	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository, PatientConverter patientConverter){
		this.patientRepository = patientRepository;
		this.patientConverter = patientConverter;
	}
	
	@Override
	public Notification<Boolean> save(PatientDto patient) {
		validator = new PatientValidator(patient);
		boolean patientValid = validator.validate();
		Notification<Boolean> patientRegisterNotification = new Notification<Boolean>();
		if(!patientValid){
			validator.getErrors().forEach(patientRegisterNotification::addError);
			System.out.println(patientRegisterNotification.getFormattedErrors());
			patientRegisterNotification.setResult(Boolean.FALSE);	
		}
		else{
			Patient dbPatient = new PatientBuilder().setName(patient.getName()).setCNP(patient.getCNP()).setCardId(patient.getCardId()).setAddress(patient.getAddress()).setDate(extractDate(patient.getBirthDate())).build();
			patientRepository.save(dbPatient);
			patientRegisterNotification.setResult(Boolean.TRUE);
		}
		return patientRegisterNotification;
	}
	


	@Override
	public List<PatientDto> findAll() {
		final Iterable<Patient> patients = patientRepository.findAll();
		List<PatientDto> result = new ArrayList<PatientDto>();
		for(Patient patient:patients)
			result.add(patientConverter.convertToDto(patient));
		return result;
	}

	@Override
	public void delete(int id) {
		patientRepository.deleteById(id);
	}
	
	@Override
	public Notification<Boolean> update(PatientDto patient) {
		
		
		validator = new PatientValidator(patient); 
	
		boolean patientValid = validator.validate();
		Notification<Boolean> patientRegisterNotification = new Notification<>();
		if(!patientValid){
			validator.getErrors().forEach(patientRegisterNotification::addError);
			patientRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			Patient dbPatient = patientRepository.getOne(patient.getId());
			dbPatient.setName(patient.getName());
			dbPatient.setCNP(patient.getCNP());
			dbPatient.setCardId(patient.getCardId());
			dbPatient.setBirthDate(extractDate(patient.getBirthDate()));
			dbPatient.setAddress(patient.getAddress());
			patientRepository.save(dbPatient);
			patientRegisterNotification.setResult(Boolean.TRUE);
		}
		
		return patientRegisterNotification;
	}
	private Date extractDate(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-d");
		Date date;
		try {
			date = df.parse(dateString);
			return date;
		} catch (ParseException e) {
		
		}
		return null;
	}
	
	

}
