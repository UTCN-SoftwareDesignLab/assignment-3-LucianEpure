package service.consultation;

import java.util.List;

import dto.ConsultationDto;
import dto.UserDto;
import validators.Notification;

public interface ConsultationService {

	Notification<Boolean> addConsultation(ConsultationDto consultation);

	List<ConsultationDto> findAllConsultations();
	
	List<UserDto> findAllDoctors();

	List<ConsultationDto> seeDoctorSchedule(int doctorId);

	Notification<Boolean> update(ConsultationDto consultation);

	void diagnose(int id, String diagnostic);

	void delete(int id);

	void removeAll();
}
