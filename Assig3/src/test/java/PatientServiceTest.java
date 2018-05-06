import dto.PatientDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import service.patient.PatientService;

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:tests.properties")
@EnableJpaRepositories(basePackages = {"repository"})
@ComponentScan({"entity","converter","dto", "application","controller","config","service","repository"})
@EntityScan(basePackages ={"entity"})
@Transactional
public class PatientServiceTest {

    @Autowired
    PatientService patientService;

    private static final String NAME = "Ion";
    private static final String UPDATENAME = "Costi";
    private static final String CNP = "1234567891234";
    private static final int CARDID = 123456;
    private static final String ADDRESS = "Argentina";
    private static final String BIRTHDATE = "1999-02-03";


    @Before
    public void setup() throws Exception{
        patientService.removeAll();
    }

    @Test
    public void addPatient(){
        PatientDto patientDto = new PatientDto();
        patientDto.setName(NAME);
        patientDto.setCNP(CNP);
        patientDto.setBirthDate(BIRTHDATE);
        patientDto.setAddress(ADDRESS);
        patientDto.setCardId(CARDID);
        patientService.save(patientDto);
        Assert.assertTrue(patientService.findAll().size()==1);
    }

    @Test
    public void findAll(){
        Assert.assertTrue(patientService.findAll().size()==0);
    }

    @Test
    public void update(){
        PatientDto patientDto = new PatientDto();
        patientDto.setName(NAME);
        patientDto.setCNP(CNP);
        patientDto.setBirthDate(BIRTHDATE);
        patientDto.setAddress(ADDRESS);
        patientDto.setCardId(CARDID);
        patientService.save(patientDto);
        int id = patientService.findAll().get(0).getId();
        PatientDto patientDto1 = new PatientDto();
        patientDto1.setId(id);
        patientDto1.setName(UPDATENAME);
        patientDto1.setCNP(CNP);
        patientDto1.setBirthDate(BIRTHDATE);
        patientDto1.setAddress(ADDRESS);
        patientDto1.setCardId(CARDID);
        patientService.update(patientDto1);
        Assert.assertTrue(patientService.findAll().get(0).getName()!=NAME);
    }

    @Test
    public void deleteById(){
        PatientDto patientDto = new PatientDto();
        patientDto.setName(NAME);
        patientDto.setCNP(CNP);
        patientDto.setBirthDate(BIRTHDATE);
        patientDto.setAddress(ADDRESS);
        patientDto.setCardId(CARDID);
        patientService.save(patientDto);
        int id = patientService.findAll().get(0).getId();
        patientService.delete(id);
        Assert.assertTrue(patientService.findAll().isEmpty());
    }

}
