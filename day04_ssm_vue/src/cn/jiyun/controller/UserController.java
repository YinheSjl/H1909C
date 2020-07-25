package cn.jiyun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jiyun.pojo.User;
import cn.jiyun.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService us;
	
	@RequestMapping("toLogin")
	public String toLogin() {
		
		return "login";
	}
	
	
	@RequestMapping("login")
	public int login(@RequestBody User user) {
		int i = us.login(user);
		return i;
	}
	
	
	
}
