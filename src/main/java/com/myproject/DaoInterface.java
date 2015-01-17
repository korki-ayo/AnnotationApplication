package com.myproject;

import java.util.List;

public interface DaoInterface{
	public List<User> list();
	public User findByLogin(String login);
	public User findById(String id);
	public void saveUser(User user);
	public void updateUser(User user);
}