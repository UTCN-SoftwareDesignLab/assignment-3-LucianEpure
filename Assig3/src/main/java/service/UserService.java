package service;

import java.util.List;

import dto.UserDto;
import entity.User;
import validators.Notification;

public interface UserService {

	Notification<Boolean> save(UserDto user, String type);
	
	public Notification<Boolean> update(UserDto user);
	
	void fireById(int id);

	List<User> findAll();
}
