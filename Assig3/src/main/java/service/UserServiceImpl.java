package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UserDto;
import entity.Role;
import entity.User;
import entity.builder.UserBuilder;
import repository.RoleRepository;
import repository.UserRepository;
import validators.IValidator;
import validators.Notification;
import validators.UserValidator;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private IValidator validator;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public Notification<Boolean> save(UserDto user, String type) {
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		validator = new UserValidator(user); 
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<Boolean>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			System.out.println(userRegisterNotification.getFormattedErrors());
			userRegisterNotification.setResult(Boolean.FALSE);	
		}
		else{
			User dbUser = new UserBuilder().setUsername(user.getUsername()).setPassword(enc.encode(user.getPassword())).build();
			List<Role> userRoles = dbUser.getRoles();
			userRoles.add(roleRepository.findByRoleName(type));
			dbUser.setRoles(userRoles);
			userRepository.save(dbUser);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		return userRegisterNotification;
	}
	
	@Override
	public List<User> findAll() {

		final Iterable<User> users = userRepository.findAll();
		List<User> result = new ArrayList<User>();
		users.forEach(result::add);
		return result;
	}
	
	@Override
	public void fireById(int id) {
		userRepository.deleteById(id);
		
	}
	
	@Override
	public Notification<Boolean> update(UserDto user) {
		
		
		validator = new UserValidator(user); 
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			System.out.println(userRegisterNotification.getFormattedErrors());
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			User dbUser = userRepository.getOne(user.getId());
			dbUser.setUsername(user.getUsername());
			dbUser.setPassword(enc.encode(user.getPassword()));
			userRepository.save(dbUser);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		
		return userRegisterNotification;
	}

}
