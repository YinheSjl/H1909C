package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonExample;
import com.xiaoshu.entity.PersonExample.Criteria;
import com.xiaoshu.entity.User;

@Service
public class PersonService {

	@Autowired
	PersonMapper pm;



	public List<Company> findCompany() {
		// TODO Auto-generated method stub
		return pm.findCompany();
	}

	public PageInfo<Person> findUserPage(Person person, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Person> userList = pm.findAll(person);
		PageInfo<Person> pageInfo = new PageInfo<Person>(userList);
		return pageInfo;
	}

	public Person existUserWithUserName(String getpName) {
		PersonExample example = new PersonExample();
		Criteria criteria = example.createCriteria();
		criteria.andPNameEqualTo(getpName);
		List<Person> userList = pm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}

	public void updatePerson(Person person) {
		// TODO Auto-generated method stub
		pm.updateByPrimaryKeySelective(person);
	}

	public void addPerson(Person person) {
		// TODO Auto-generated method stub
		pm.insert(person);
	}

	public void deletePerson(int parseInt) {
		// TODO Auto-generated method stub
		pm.deleteByPrimaryKey(parseInt);
	}


}
