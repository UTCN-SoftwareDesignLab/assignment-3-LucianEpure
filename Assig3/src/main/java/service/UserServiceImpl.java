package service;

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

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public void save(UserDto user, String type) {
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		User dbUser = new UserBuilder().setUsername(user.getUsername()).setPassword(enc.encode(user.getPassword())).build();
		List<Role> userRoles = dbUser.getRoles();
		userRoles.add(roleRepository.findByRoleName(type));
		dbUser.setRoles(userRoles);
		userRepository.save(dbUser);

	}

}
