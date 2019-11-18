package com.inautix.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	LoginDao ld;
	public List<User> getUsers(){
		return ld.getUsers();
	}
	public int valid(User user2) {
		return ld.valid(user2);
	}
	public int register(String username, String password){
		return ld.register(username,password);
	}
}
