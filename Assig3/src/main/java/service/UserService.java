package service;

import dto.UserDto;

public interface UserService {

	void save(UserDto user, String type);
}
