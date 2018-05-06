package converter;

import dto.ConsultationDto;
import entity.Consultation;
import org.springframework.stereotype.Component;

import javax.persistence.Converter;

@Component
public class ConsultationConverterImpl implements ConsultationConverter {

    @Override
    public ConsultationDto convertToDto(Consultation consultation){
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setUserId(consultation.getDoctor().getId());
        consultationDto.setPatientId(consultation.getPatient().getId());
        consultationDto.setDiagnostic(consultation.getDiagnostic());
        consultationDto.setId(consultation.getId());
        consultationDto.setScheduledDate(consultation.getScheduledDate().toString());
        return consultationDto;
    }
}
