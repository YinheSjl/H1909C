package cn.jiyun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.jiyun.pojo.Student;
import cn.jiyun.pojo.StudentVo;

public interface StudentMapper {

	List<Student> findAll(StudentVo vo);

	List<Class> findClass();

	int addStudent(Student student);

	int updateStu(Student stu);

	int delStu(@Param("ids") Integer[] ids);

}
