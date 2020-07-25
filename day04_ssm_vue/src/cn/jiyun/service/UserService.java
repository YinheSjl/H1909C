package cn.jiyun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jiyun.mapper.UserMapper;
import cn.jiyun.pojo.User;

@Service
public class UserService {

	@Autowired
	private UserMapper um;

	public int login(User user) {
		// TODO Auto-generated method stub
		return um.login(user);
	}
}
