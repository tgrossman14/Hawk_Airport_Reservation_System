package dao;

import models.User;

import java.util.Map;

public interface UserDao {
	String getOne();
	Map<Integer, User> getAllUsers();
	Map<Integer, User> checkUser(User user);
	void createUser(User user);
	void registerUser(String firstName, String lastName, String email, String password);
	User userLogin(String email, String password);
}
