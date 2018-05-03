package entity.builder;

import java.util.Date;

import entity.Consultation;
import entity.Patient;
import entity.User;

public class ConsultationBuilder {

	private Consultation consultation;
	
	public ConsultationBuilder(){
		this.consultation = new Consultation();
	}
	public ConsultationBuilder setPatient(Patient patient){
		consultation.setPatient(patient);
		return this;
	}
	
	public ConsultationBuilder setDoctor(User doctor){
		consultation.setDoctor(doctor);
		return this;
	}
	public ConsultationBuilder setDate(Date date){
		consultation.setScheduledDate(date);
		return this;
	}
	
	public ConsultationBuilder setDiagnostic(String diagnostic){
		consultation.setDiagnostic(diagnostic);
		return this;
	}
	
	public Consultation build(){
		return this.consultation;
	}
}
