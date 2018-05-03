package converter;


import org.springframework.stereotype.Component;

import dto.PatientDto;
import entity.Patient;

@Component
public class PatientConverter {

	public PatientDto convertToDto(Patient patient){
		PatientDto patientDto = new PatientDto();
		patientDto.setId(patient.getId());
		patientDto.setName(patient.getName());
		patientDto.setAddress(patient.getAddress());
		patientDto.setBirthDate(patient.getBirthDate().toString());
		patientDto.setCardId(patient.getCardId());
		patientDto.setCNP(patient.getCNP());
		return patientDto;
	}
	/*
	public Patient convertFromDto(PatientDto patientDto){
		Patient patient = new Patient();
		patient.setId(patientDto.getId());
		patient.setAddress(patientDto.getAddress());
		patient.setBirthDate(extractDate(patientDto.getBirthDate()));
		patient.setCardId(patientDto.getCardId());
		patient.setCNP(patientDto.getCNP());
		return patient;
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
}*/
}
