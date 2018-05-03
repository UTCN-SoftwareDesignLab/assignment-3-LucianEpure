package validators;

import java.util.ArrayList;
import java.util.List;

import dto.ConsultationDto;
import dto.PatientDto;

public class ConsultationValidator implements IValidator{

	private final List<String> errors;
	private final ConsultationDto consultation;
	
	public ConsultationValidator(ConsultationDto consultation){
		this.consultation = consultation;
		 errors = new ArrayList<String>();
	}
	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return errors.isEmpty();
	}

	@Override
	public List<String> getErrors() {
		// TODO Auto-generated method stub
        return errors;
	}
}
