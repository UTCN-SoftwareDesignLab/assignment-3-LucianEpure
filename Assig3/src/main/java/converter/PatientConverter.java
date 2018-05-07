package converter;

import dto.PatientDto;
import entity.Patient;


public interface PatientConverter {

	PatientDto convertToDto(Patient patient);

}
