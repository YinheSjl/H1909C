package cn.jiyun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.jiyun.pojo.Student;
import cn.jiyun.pojo.StudentVo;
import cn.jiyun.service.StudentService;

@Controller
@RequestMapping("student")
public class StudentController {

	@Autowired
	private StudentService ss;

	
	@RequestMapping("toShow")
	public String toShow() {

		return "show";
	}

	@RequestMapping("findAll")
	@ResponseBody
	public PageInfo<Student> findAll(@RequestBody StudentVo vo) {
		
		PageHelper.startPage(vo.getPageNum(), 2);
		
		List<Student> sList = ss.findAll(vo);
		
		PageInfo<Student> page = new PageInfo<>(sList);

		return page;
	}

	@RequestMapping("toAdd")
	public String toAdd() {

		return "add";
	}

	@RequestMapping("findClass")
	@ResponseBody
	public List<Class> findClass() {

		List<Class> cList = ss.findClass();

		return cList;
	}

	@RequestMapping("addStudent")
	@ResponseBody
	public int addStudent(@RequestBody Student student) {

		int i = ss.addStudent(student);

		return i;
	}

	// 修改
	@ResponseBody
	@RequestMapping("updateStu")
	public int updateStu(@RequestBody Student stu) {
		int i = ss.updateStu(stu);
		return i;
	}

	// 删除
	@ResponseBody
	@RequestMapping("delStu")
	public int delStu(@RequestBody Integer[] ids) {
		int i = ss.delStu(ids);
		return i;
	}

}
