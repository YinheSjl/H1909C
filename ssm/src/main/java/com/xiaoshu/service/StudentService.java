package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Clazz;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;

@Service
public class StudentService {

	@Autowired
	StudentMapper sm;

	
//	// 数量
//	public int countUser(User t) throws Exception {
//		return userMapper.selectCount(t);
//	};
//
//	// 通过ID查询
//	public User findOneUser(Integer id) throws Exception {
//		return userMapper.selectByPrimaryKey(id);
//	};
//
	// 新增
	public void addStudent(Student t) throws Exception {
		sm.insert(t);
	};

	// 修改
	public void updateStudent(Student t) throws Exception {
		sm.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteStudent(Integer id) throws Exception {
		sm.deleteByPrimaryKey(id);
	};
//
//	// 登录
//	public User loginUser(User user) throws Exception {
//		UserExample example = new UserExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andPasswordEqualTo(user.getPassword()).andUsernameEqualTo(user.getUsername());
//		List<User> userList = userMapper.selectByExample(example);
//		return userList.isEmpty()?null:userList.get(0);
//	};
//
//	// 通过用户名判断是否存在，（新增时不能重名）
//	public User existUserWithUserName(String userName) throws Exception {
//		UserExample example = new UserExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andUsernameEqualTo(userName);
//		List<User> userList = userMapper.selectByExample(example);
//		return userList.isEmpty()?null:userList.get(0);
//	};
//
//	// 通过角色判断是否存在
//	public User existUserWithRoleId(Integer roleId) throws Exception {
//		UserExample example = new UserExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andRoleidEqualTo(roleId);
//		List<User> userList = userMapper.selectByExample(example);
//		return userList.isEmpty()?null:userList.get(0);
//	}
//
//	public PageInfo<User> findUserPage(User user, int pageNum, int pageSize, String ordername, String order) {
//		PageHelper.startPage(pageNum, pageSize);
//		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
//		order = StringUtil.isNotEmpty(order)?order:"desc";
//		UserExample example = new UserExample();
//		example.setOrderByClause(ordername+" "+order);
//		Criteria criteria = example.createCriteria();
//		if(StringUtil.isNotEmpty(user.getUsername())){
//			criteria.andUsernameLike("%"+user.getUsername()+"%");
//		}
//		if(user.getUsertype() != null){
//			criteria.andUsertypeEqualTo(user.getUsertype());
//		}
//		if(user.getRoleid() != null){
//			criteria.andRoleidEqualTo(user.getRoleid());
//		}
//		List<User> userList = userMapper.selectUserAndRoleByExample(example);
//		PageInfo<User> pageInfo = new PageInfo<User>(userList);
//		return pageInfo;
//	}

	


	public List<Clazz> findClazz() {
		// TODO Auto-generated method stub
		return sm.findClazz();
	}


	public PageInfo<Student> findAll(Student student, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
//		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
//		order = StringUtil.isNotEmpty(order)?order:"desc";
//		UserExample example = new UserExample();
//		example.setOrderByClause(ordername+" "+order);
//		Criteria criteria = example.createCriteria();
//		if(StringUtil.isNotEmpty(user.getUsername())){
//			criteria.andUsernameLike("%"+user.getUsername()+"%");
//		}
//		if(user.getUsertype() != null){
//			criteria.andUsertypeEqualTo(user.getUsertype());
//		}
//		if(user.getRoleid() != null){
//			criteria.andRoleidEqualTo(user.getRoleid());
//		}
		List<Student> userList = sm.findAll(student);
		PageInfo<Student> pageInfo = new PageInfo<Student>(userList);
		return pageInfo;
	}


	public Student existUserWithUserName(String sname) {
		List<Student> userList = sm.findStudentByName(sname);
		return userList.isEmpty()?null:userList.get(0);
	}

	public Integer findClazzId(String cname) {
		// TODO Auto-generated method stub
		return sm.findClazzId(cname);
	}

	public List<Student> findStudentAll() {
		// TODO Auto-generated method stub
		List<Student> list = sm.findAll(null);
		return list;
	}


}
