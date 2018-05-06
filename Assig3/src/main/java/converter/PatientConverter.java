package converter;


import org.springframework.stereotype.Component;

import dto.PatientDto;
import entity.Patient;


public interface PatientConverter {

	PatientDto convertToDto(Patient patient);

}
