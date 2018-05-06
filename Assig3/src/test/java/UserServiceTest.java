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

import dto.UserDto;
import service.user.UserService;


@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:tests.properties")
@EnableJpaRepositories(basePackages = {"repository"})
@ComponentScan({"entity","converter","dto", "application","controller","config","service","repository"})
@EntityScan(basePackages ={"entity"})
@Transactional

public class UserServiceTest {
    @Autowired
    UserService userService;

    private static final String USERNAME = "Abc@yahoo.com";
    private static final String USERNAME_UPDATED = "Abcd@yahoo.com";
    private static final String PASSWORD = "aB123456!";

    @Before
    public void setup() throws Exception {
        userService.removeAll();
    }

    @Test
    public void addUser(){
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        Assert.assertTrue(userService.findAll().size() == 1);
    }

    @Test
    public void findAll(){
        Assert.assertTrue(userService.findAll().size()==0);
    }

    @Test
    public void fireById(){
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        int id = userService.findAll().get(0).getId();
        userService.fireById(id);
        Assert.assertTrue(userService.findAll().isEmpty());
    }

    @Test
    public void findByUsername(){
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        Assert.assertNotNull(userService.findByUsername(USERNAME));
    }

    @Test
    public void update(){
        UserDto user = new UserDto();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        userService.save(user, "doctor");
        int id = userService.findAll().get(0).getId();
        UserDto user1 = new UserDto();
        user1.setId(id);
        user1.setUsername(USERNAME_UPDATED);
        user1.setPassword(PASSWORD);
        userService.update(user1);
        Assert.assertTrue(userService.findAll().get(0).getUsername()!=USERNAME);
    }

}
