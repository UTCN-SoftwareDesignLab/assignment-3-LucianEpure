package converter;

import java.util.List;

import org.springframework.stereotype.Component;

import dto.UserDto;
import entity.Role;
import entity.User;

@Component
public class UserConverter {

	public UserDto convertToDto(User user){
		UserDto userDto= new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		
		List<Role> roles = user.getRoles();
		for(Role role:roles)
			userDto.setRoles(userDto.getRoles()+" "+role.getRoleName());
		return userDto;
	}
}
