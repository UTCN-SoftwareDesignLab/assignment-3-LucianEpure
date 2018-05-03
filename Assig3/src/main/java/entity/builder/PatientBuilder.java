package entity.builder;

import java.time.LocalDate;
import java.util.Date;

import entity.Patient;

public class PatientBuilder {

	private Patient patient;
	
	public PatientBuilder(){
		this.patient = new Patient();
	}
	
	public PatientBuilder setName(String name){
		patient.setName(name);
		return this;
	}
	
	public PatientBuilder setCNP(String cnp){
		patient.setCNP(cnp);
		return this;
	}
	public PatientBuilder setCardId(int cardId){
		patient.setCardId(cardId);
		return this;
	}
	public PatientBuilder setDate(Date date){
		patient.setBirthDate(date);
		return this;
	}
	public PatientBuilder setAddress(String address){
		patient.setAddress(address);
		return this;
	}
	public Patient build(){
		return this.patient;
	}
}
