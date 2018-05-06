package converter;



import dto.ConsultationDto;
import entity.Consultation;

public interface ConsultationConverter {

	 ConsultationDto convertToDto(Consultation consultation);
}
