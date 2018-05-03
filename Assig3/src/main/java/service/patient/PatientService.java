package service.patient;

import java.util.List;

import dto.PatientDto;
import dto.UserDto;
import entity.Patient;
import validators.Notification;

public interface PatientService {

	Notification<Boolean> save(PatientDto patient);
	
	Notification<Boolean> update(PatientDto user);
	
	List<PatientDto> findAll();
	
	void delete(int id);
}
