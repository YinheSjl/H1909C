package cn.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jiyun.mapper.StudentMapper;
import cn.jiyun.pojo.Student;
import cn.jiyun.pojo.StudentVo;

@Service
public class StudentService {

	@Autowired
	private StudentMapper sm;

	public List<Student> findAll(StudentVo vo) {
		// TODO Auto-generated method stub
		return sm.findAll(vo);
	}

	public List<Class> findClass() {
		// TODO Auto-generated method stub
		return sm.findClass();
	}

	public int addStudent(Student student) {
		// TODO Auto-generated method stub
		int i = sm.addStudent(student);
		return i;
	}

	public int updateStu(Student stu) {
		// TODO Auto-generated method stub
		int i = sm.updateStu(stu);
		return i;
	}

	public int delStu(Integer[] ids) {
		// TODO Auto-generated method stub
		int i = sm.delStu(ids);
		return i;
	}
}
