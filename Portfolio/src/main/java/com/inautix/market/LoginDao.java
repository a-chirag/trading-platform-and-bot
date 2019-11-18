package com.inautix.market;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {
	JdbcTemplate jt;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jt = new JdbcTemplate(dataSource);
	}

	public int valid(User user2) {
		String stmt = "Select * from users_prj_chirag_tst where username = '" + user2.getUsername() + "' and password ='" + user2.getPassword() + "'";
		List<User> users = jt.query(stmt, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.username = rs.getString(2);
				user.password = rs.getString(3);
				user.uid=rs.getInt(1);
				return user;
			}
		});
		if (users.size() == 0) {
			return 0;
		} else if (users.size() > 1) {
			System.out.println("some problem with the database contact DBA");
			return 0;
		} else {
			return users.get(0).uid;
		}
	}

	public int register(String username, String password) {
		String stmt = "Select max(userid) from users_prj_chirag_tst";
		//stmt = "Select * from users_prj_chirag_tst where username = '" + username + "' and password ='" + password+ "'";
		List<Integer> uids= jt.query(stmt, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		stmt = "Insert into users_prj_chirag_tst values (?,?,?)";
		jt.update(stmt, uids.get(0)+1,username,password);
		return uids.get(0)+1;
	}

	public List<User> getUsers() {
		String stmt = "Select * from users_prj_chirag_tst";
		List<User> users = jt.query(stmt, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.username = rs.getString(2);
				user.password = rs.getString(3);
				user.uid=rs.getInt(1);
				return user;
			}
		});// TODO Auto-generated method stub
		return users;
	}
}
