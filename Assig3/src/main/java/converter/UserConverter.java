package converter;

import java.util.List;

import org.springframework.stereotype.Component;

import dto.UserDto;
import entity.Role;
import entity.User;


public interface UserConverter {

	UserDto convertToDto(User user);
}
