package service.consultation;

import java.util.List;

import dto.ConsultationDto;
import dto.UserDto;
import validators.Notification;

public interface ConsultationService {

	Notification<Boolean> addConsultation(ConsultationDto consultation);

	List<ConsultationDto> findAllConsultations();
	
	List<UserDto> findAllDoctors();
}
