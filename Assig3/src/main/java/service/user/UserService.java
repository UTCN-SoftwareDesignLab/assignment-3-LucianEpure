package service.user;

import java.util.List;

import dto.UserDto;
import entity.User;
import validators.Notification;

public interface UserService {

	Notification<Boolean> save(UserDto user, String type);
	
	Notification<Boolean> update(UserDto user);
	
	void fireById(int id);

	List<UserDto> findAll();

	User findByUsername(String username);

	User findById(int id);
}
