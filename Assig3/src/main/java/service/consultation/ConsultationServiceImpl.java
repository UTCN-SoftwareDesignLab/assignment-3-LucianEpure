package service.consultation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import converter.ConsultationConverter;
import converter.UserConverter;
import dto.ConsultationDto;
import dto.PatientDto;
import dto.UserDto;
import entity.Consultation;
import entity.Patient;
import entity.User;
import entity.builder.ConsultationBuilder;
import repository.ConsultationRepository;
import repository.PatientRepository;
import repository.UserRepository;
import validators.ConsultationValidator;
import validators.IValidator;
import validators.Notification;

@Service
public class ConsultationServiceImpl implements ConsultationService{

	
	private ConsultationRepository consultationRepository;
	private UserRepository userRepository;
	private PatientRepository patientRepository;
	private UserConverter userConverter;
	private ConsultationConverter consultationConverter;
	private IValidator validator;
	
	@Autowired
	public ConsultationServiceImpl( ConsultationRepository consultationRepository,UserRepository userRepository,PatientRepository patientRepository, UserConverter userConverter, ConsultationConverter consultationConverter){
		this.consultationRepository = consultationRepository;
		this.patientRepository = patientRepository;
		this.userRepository = userRepository;
		this.userConverter = userConverter;
		this.consultationConverter = consultationConverter;
	}
	
	@Override
	public Notification<Boolean> addConsultation(ConsultationDto consultation) {
	validator = new ConsultationValidator(consultation);
		
		boolean consultationValid = validator.validate();
		Notification<Boolean> consultationNotification = new Notification<Boolean>();
		if(!consultationValid){	
			validator.getErrors().forEach(consultationNotification::addError);
			consultationNotification.setResult(Boolean.FALSE);
		}
		else{
			Patient patient = patientRepository.getOne(consultation.getPatientId());
			User doctor = userRepository.getOne(consultation.getUserId());
			if(patient==null)
				consultationNotification.addError("No such patient!");
			if(doctor ==null)
				consultationNotification.addError("No such doctor!");
			List<Consultation> consultations = consultationRepository.findByDoctor(doctor);
			for(Consultation consult:consultations){

					if(Math.abs(consult.getScheduledDate().getTime() - extractDate(consultation.getScheduledDate()).getTime())<3600000){
						consultationNotification.addError("Not enough time between consultations");
						return consultationNotification;
					}


			}
			Consultation dbConsultation = new ConsultationBuilder().setPatient(patient).setDoctor(doctor).setDate(extractDate(consultation.getScheduledDate())).build();
			consultationRepository.save(dbConsultation);
		}
		return consultationNotification;
	}
	

	private Date extractDate(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = df.parse(dateString);
			System.out.println("AAAAAAAAAAAa"+date.toString()+"bbbbb" +date.getTime());
			return date;

		} catch (ParseException e) {
		
		}


		return null;
	}

	@Override
	public List<UserDto> findAllDoctors() {
		final Iterable<User> doctors = userRepository.findAll();
		List<UserDto> result = new ArrayList<UserDto>();
		for(User doctor:doctors){
			if(doctor.getRoles().get(0).getRoleName().equalsIgnoreCase("doctor"))
				{
				
				result.add(userConverter.convertToDto(doctor));
				}
		}
		return result;
	}



	@Override
	public List<ConsultationDto> findAllConsultations() {
		final Iterable<Consultation> consultations = consultationRepository.findAll();
		List<ConsultationDto> result = new ArrayList<ConsultationDto>();
		for(Consultation consultation:consultations){
			result.add(consultationConverter.convertToDto(consultation));
		}
		return result;
	}
	@Override
	public List<ConsultationDto> seeDoctorSchedule(int doctorId) {
		User doctor = userRepository.getOne(doctorId);
		final Iterable<Consultation> consultations = consultationRepository.findByDoctor(doctor);
		List<ConsultationDto> result = new ArrayList<ConsultationDto>();
		for(Consultation consultation:consultations){
			result.add(consultationConverter.convertToDto(consultation));
		}
		return result;
	}



	@Override
	public Notification<Boolean> update(ConsultationDto consultation) {
		validator = new ConsultationValidator(consultation);

		boolean consultationValid = validator.validate();
		Notification<Boolean> consultationNotification = new Notification<>();
		if(!consultationValid){
			validator.getErrors().forEach(consultationNotification::addError);
			consultationNotification.setResult(Boolean.FALSE);
		}
		else{
			Consultation dbConsultation = consultationRepository.getOne(consultation.getId());
			dbConsultation.setDoctor(userRepository.getOne(consultation.getUserId()));
			dbConsultation.setPatient(patientRepository.getOne(consultation.getPatientId()));
			dbConsultation.setDiagnostic(consultation.getDiagnostic());
			dbConsultation.setScheduledDate(extractDate(consultation.getScheduledDate()));
			consultationRepository.save(dbConsultation);
			consultationNotification.setResult(Boolean.TRUE);
		}
		return consultationNotification;
	}

	@Override
	public void diagnose(int id, String diagnostic) {
		Consultation consultation = consultationRepository.getOne(id);
		consultation.setDiagnostic(diagnostic);
		consultationRepository.save(consultation);
	}

	@Override
	public void delete(int id) {
		consultationRepository.deleteById(id);
	}

}
