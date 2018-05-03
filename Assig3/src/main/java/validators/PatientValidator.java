package validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dto.PatientDto;

public class PatientValidator implements IValidator{

	private final List<String> errors;
	private final PatientDto patient;
	private final String CNP_VALIDATION_REGEX = "[0-9]+";
	public PatientValidator(PatientDto patient){
		this.patient = patient;
		 errors = new ArrayList<String>();
	}
	
	private void validateCNP(String CNP){
		if(CNP.length()<13){
			errors.add("Too short CNP");
		}
		if(CNP.length()>13){
			errors.add("Too long CNP");
		}
		   if (!Pattern.compile(CNP_VALIDATION_REGEX).matcher(CNP).matches()) {
	            errors.add("No letters in CNP!");
	        }
	}
	private void validateCardId(int cardId){
		if(cardId<100000){
			errors.add("Too short cardId");
		}
		if(cardId>999999){
			errors.add("Too long cardId");
		}
	}
	
	@Override
	public boolean validate() {
		validateCNP(patient.getCNP());
		validateCardId(patient.getCardId());
		return errors.isEmpty();
	}

	@Override
	public List<String> getErrors() {
		// TODO Auto-generated method stub
        return errors;
	}
}
