package iris.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import iris.db.ConnectionDB;
import iris.db.model.User;
import iris.mapper.UserMapper;

public class UserDB {

	private final SqlSessionFactory sqlMapper;

	public UserDB() {
		this.sqlMapper = ConnectionDB.getSqlMapper();
	}
	
	public List<User> getUsers() {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.selectAll();
		} finally {
			session.close();
		}
	}
	
	public User getUser(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.select(id);
		} finally {
			session.close();
		}
	}
	

	public User login(String name, String password) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.login(name, password);
		} finally {
			session.close();
		}
	}
	
	public void insert(User user) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.insert(user);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void changePassword(int id, String password) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.update(id, password);
			session.commit();
		} finally {
			session.close();
		}
	}

	public User getUser(String name) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.selectByName(name);
		} finally {
			session.close();
		}
	}
	
	public void deleteUser(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.delete(id);
			session.commit();
		} finally {
			session.close();
		}
	}

}


