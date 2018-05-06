package application;

import dto.PatientDto;
import dto.UserDto;
import entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.RoleRepository;
import repository.UserRepository;
import service.consultation.ConsultationService;
import service.patient.PatientService;
import service.user.UserService;

import javax.annotation.PostConstruct;

@Component
public class Bootstrap {
private RoleRepository roleRepository;
private UserService userService;
private ConsultationService consultationService;
private PatientService patientService;

    @Autowired
    public Bootstrap(RoleRepository roleRepository, UserService userService, ConsultationService consultationService, PatientService patientService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.consultationService = consultationService;
        this.patientService = patientService;
    }

    @PostConstruct
    private void initialize(){
        initRoles();
        initUsers();
        initPatients();
    }

    private void initRoles() {
    Role role1 = new Role("doctor");
    Role role2 = new Role("administrator");
    Role role3 = new Role("secretary");
    roleRepository.save(role1);
    roleRepository.save(role2);
    roleRepository.save(role3);
    }

    private void initUsers() {
        UserDto userDto = new UserDto();
        userDto.setUsername("Luc@yahoo.com");
        userDto.setPassword("aB123456!");
        userService.save(userDto, "administrator");

        UserDto userDto1 = new UserDto();
        userDto1.setUsername("Anc@yahoo.com");
        userDto1.setPassword("aB123456!");
        userService.save(userDto1, "doctor");

        UserDto userDto2 = new UserDto();
        userDto2.setUsername("Ana@yahoo.com");
        userDto2.setPassword("aB123456!");
        userService.save(userDto2, "secretary");
    }

    private void initPatients() {
        PatientDto patientDto= new PatientDto();
        patientDto.setName("Ionut");
        patientDto.setCNP("1234555891234");
        patientDto.setCardId(120056);
        patientDto.setAddress("Argentina");
        patientDto.setBirthDate("1999-07-08");
        patientService.save(patientDto);

        PatientDto patientDto1= new PatientDto();
        patientDto1.setName("Adina");
        patientDto1.setCNP("2237685891234");
        patientDto1.setCardId(121256);
        patientDto1.setAddress("Guantamo");
        patientDto1.setBirthDate("1997-04-08");
        patientService.save(patientDto1);
    }


}

