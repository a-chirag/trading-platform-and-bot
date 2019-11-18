package com.inautix.market;
import com.inautix.utils.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class UsersDB {
Map<Integer,UserData> users ; 
public UsersDB(){
	System.out.println("Database Initialised");
 users = new HashMap<Integer, UserData>(); 
}

public Map<Integer, UserData> getUsers() {
	return users;
}
@Autowired
public void setUserData(UserData userData) {
	users.put(0, userData);
}

public UserData getUser(int id){
	return users.get(id);
}
}
