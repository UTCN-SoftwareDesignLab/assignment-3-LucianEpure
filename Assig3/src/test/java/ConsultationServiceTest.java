import dto.ConsultationDto;
import dto.PatientDto;
import dto.UserDto;
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
import service.consultation.ConsultationService;
import service.patient.PatientService;
import service.user.UserService;

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:tests.properties")
@EnableJpaRepositories(basePackages = {"repository"})
@ComponentScan({"entity","converter","dto", "application","controller","config","service","repository"})
@EntityScan(basePackages ={"entity"})
@Transactional



public class ConsultationServiceTest {


    private static final String USERNAME = "Abc@yahoo.com";
    private static final String PASSWORD = "aB123456!";
    private static final String NAME = "Ion";
    private static final String CNP = "1234567891234";
    private static final int CARDID = 123456;
    private static final String ADDRESS = "Argentina";
    private static final String BIRTHDATE = "1999-02-03";
    private static final String SCHEDULEDDATE = "1999-02-04 10:00";
    private static final String UPDATESCHEDULEDDATE = "1999-02-04 15:00";
    private static final String DIAGNOSTIC = "VEry ILL!";

    @Autowired
    ConsultationService consultationService;

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @Before
    public void setup() throws Exception {
        consultationService.removeAll();
        userService.removeAll();
        patientService.removeAll();

    }

    @Test
    public void addConsultation(){
        PatientDto patientDto = new PatientDto();
        patientDto.setName(NAME);
        patientDto.setCNP(CNP);
        patientDto.setBirthDate(BIRTHDATE);
        patientDto.setAddress(ADDRESS);
        patientDto.setCardId(CARDID);
        patientService.save(patientDto);
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setPatientId(patientService.findAll().get(0).getId());
        consultationDto.setUserId(userService.findAll().get(0).getId());
        consultationDto.setScheduledDate(SCHEDULEDDATE);
        consultationService.addConsultation(consultationDto);
        Assert.assertTrue(consultationService.findAllConsultations().size()==1);
    }

    @Test
    public void findAll(){
        Assert.assertTrue(userService.findAll().size()==0);
    }

    @Test
    public void setSchedule(){
        PatientDto patientDto = new PatientDto();
        patientDto.setName(NAME);
        patientDto.setCNP(CNP);
        patientDto.setBirthDate(BIRTHDATE);
        patientDto.setAddress(ADDRESS);
        patientDto.setCardId(CARDID);
        patientService.save(patientDto);
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setPatientId(patientService.findAll().get(0).getId());
        consultationDto.setUserId(userService.findAll().get(0).getId());
        consultationDto.setScheduledDate(SCHEDULEDDATE);
        consultationService.addConsultation(consultationDto);
        Assert.assertTrue(consultationService.seeDoctorSchedule(userService.findByUsername(USERNAME).getId()).size()==1);
    }

    @Test
    public void delete(){
        PatientDto patientDto = new PatientDto();
        patientDto.setName(NAME);
        patientDto.setCNP(CNP);
        patientDto.setBirthDate(BIRTHDATE);
        patientDto.setAddress(ADDRESS);
        patientDto.setCardId(CARDID);
        patientService.save(patientDto);
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setPatientId(patientService.findAll().get(0).getId());
        consultationDto.setUserId(userService.findAll().get(0).getId());
        consultationDto.setScheduledDate(SCHEDULEDDATE);
        consultationService.addConsultation(consultationDto);
        consultationService.delete(consultationService.findAllConsultations().get(0).getId());
        Assert.assertTrue(consultationService.findAllConsultations().isEmpty());
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
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setPatientId(patientService.findAll().get(0).getId());
        consultationDto.setUserId(userService.findAll().get(0).getId());
        consultationDto.setScheduledDate(SCHEDULEDDATE);
        consultationService.addConsultation(consultationDto);
        ConsultationDto consultationDto1 = new ConsultationDto();
        consultationDto1.setId(consultationService.findAllConsultations().get(0).getId());
        consultationDto1.setPatientId(patientService.findAll().get(0).getId());
        consultationDto1.setUserId(userService.findAll().get(0).getId());
        consultationDto1.setScheduledDate(UPDATESCHEDULEDDATE);
        consultationService.update(consultationDto1);
        Assert.assertTrue(consultationService.findAllConsultations().get(0).getScheduledDate()!=SCHEDULEDDATE);
    }

    @Test
    public void diagnose(){
        PatientDto patientDto = new PatientDto();
        patientDto.setName(NAME);
        patientDto.setCNP(CNP);
        patientDto.setBirthDate(BIRTHDATE);
        patientDto.setAddress(ADDRESS);
        patientDto.setCardId(CARDID);
        patientService.save(patientDto);
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setPatientId(patientService.findAll().get(0).getId());
        consultationDto.setUserId(userService.findAll().get(0).getId());
        consultationDto.setScheduledDate(SCHEDULEDDATE);
        consultationService.addConsultation(consultationDto);
        consultationService.diagnose(consultationService.findAllConsultations().get(0).getId(),DIAGNOSTIC);
        Assert.assertNotNull(consultationService.findAllConsultations().get(0).getDiagnostic());
    }
}
