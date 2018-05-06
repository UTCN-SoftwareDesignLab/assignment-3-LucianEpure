package converter;

import dto.PatientDto;
import entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientConverterImpl implements PatientConverter{

    @Override
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
}
