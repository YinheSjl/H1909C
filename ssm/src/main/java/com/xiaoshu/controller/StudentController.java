package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Clazz;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Student;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("student")
public class StudentController extends LogController {
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private StudentService ss;

	@Autowired
	private OperationService operationService;

	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {
		List<Clazz> roleList = ss.findClazz();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "student";
	}

	@RequestMapping(value = "studentList", method = RequestMethod.POST)
	public void userList(Student student, HttpServletRequest request, HttpServletResponse response, String offset,
			String limit) throws Exception {
		try {
			// User user = new User();
			// String username = request.getParameter("username");
			// String roleid = request.getParameter("roleid");
			// String usertype = request.getParameter("usertype");
			// String order = request.getParameter("order");
			// String ordername = request.getParameter("ordername");
			// if (StringUtil.isNotEmpty(username)) {
			// user.setUsername(username);
			// }
			// if (StringUtil.isNotEmpty(roleid) && !"0".equals(roleid)) {
			// user.setRoleid(Integer.parseInt(roleid));
			// }
			// if (StringUtil.isNotEmpty(usertype)) {
			// user.setUsertype(usertype.getBytes()[0]);
			// }

			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			PageInfo<Student> userList = ss.findAll(student, pageNum, pageSize);

			// request.setAttribute("username", username);
			// request.setAttribute("roleid", roleid);
			// request.setAttribute("usertype", usertype);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", userList.getTotal());
			jsonObj.put("rows", userList.getList());
			WriterUtil.write(response, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误", e);
			throw e;
		}
	}

	// 新增或修改
	@RequestMapping("reserveStudent")
	public void reserveUser(HttpServletRequest request, Student student, MultipartFile file,
			HttpServletResponse response) {
		Integer userId = student.getSid();
		// String hobby = student.getHobby();
		// if(hobby==null){
		// student.setHobby("");
		// }
		JSONObject result = new JSONObject();

		String filename = file.getOriginalFilename();

		try {
			if (userId != null) { // userId不为空 说明是修改
				if (ss.existUserWithUserName(student.getSname()) != null
						&& ss.existUserWithUserName(student.getSname()).getSid().compareTo(userId) == 0
						|| ss.existUserWithUserName(student.getSname()) == null) {
					if (filename != null && filename != "") {
						String newname = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
						student.setImg("/picture/" + newname);
						file.transferTo(new File("/picture/" + newname));
					}
					ss.updateStudent(student);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}

			} else { // 添加
				if (ss.existUserWithUserName(student.getSname()) == null) { // 没有重复可以添加
					if (filename != null && filename != "") {
						String newname = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
						student.setImg("/picture/" + newname);
						file.transferTo(new File("/picture/" + newname));
					}
					ss.addStudent(student);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误", e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}

	// 导入
	@RequestMapping("inStudent")
	public void inStudent(HttpServletRequest request, MultipartFile infile, HttpServletResponse response) {
		JSONObject result = new JSONObject();


		try {
			//1.创建工作簿
			XSSFWorkbook workbook = new XSSFWorkbook(infile.getInputStream());
			//2.读取sheet页
			XSSFSheet sheet = workbook.getSheetAt(0);
			//3.获取行
			int lastRowNum = sheet.getLastRowNum();
//			ArrayList<Student> list = new ArrayList<Student>();
			for (int i = 1; i <= lastRowNum; i++) {
				XSSFRow row = sheet.getRow(i);
				Student student = new Student();
//				student.setSid((int)row.getCell(0).getNumericCellValue());
				student.setSname(row.getCell(1).getStringCellValue());
				student.setSex(row.getCell(2).getStringCellValue());
				student.setHobby(row.getCell(3).getStringCellValue());
				
				String bir = row.getCell(4).getStringCellValue();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				student.setBirthday(simpleDateFormat.parse(bir));
				String cname = row.getCell(5).getStringCellValue();
				Integer cid = ss.findClazzId(cname);
				student.setCid(cid);
				ss.addStudent(student);
				
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误", e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	// 导出
	@RequestMapping("outStudent")
	public void outStudent(HttpServletRequest request, MultipartFile infile, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		
		List<Student> list = ss.findStudentAll();
		
		try {
			
			//1.实例化工作簿
			XSSFWorkbook workbook = new XSSFWorkbook();
			//2.创建sheet页
			XSSFSheet sheet = workbook.createSheet("学生信息");
			//3.创建行row
			XSSFRow row0 = sheet.createRow(0);
			//4.创建单元格并赋值
			String[] title={"编号","姓名","性别","爱好","生日","班级"};
			for (int i = 0; i < title.length; i++) {
				XSSFCell cell = row0.createCell(i);
				cell.setCellValue(title[i]);
			}
			for (int i = 0; i < list.size(); i++) {
				XSSFRow row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(list.get(i).getSid());
				row.createCell(1).setCellValue(list.get(i).getSname());
				row.createCell(2).setCellValue(list.get(i).getSex());
				row.createCell(3).setCellValue(list.get(i).getHobby());
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				row.createCell(4).setCellValue(simpleDateFormat.format(list.get(i).getBirthday()));
				row.createCell(5).setCellValue(list.get(i).getClazz().getCname());
				
			}
			//5.导出
			FileOutputStream outputStream = new FileOutputStream("E:/导出学生信息.xlsx");
			workbook.write(outputStream);
			
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误", e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}

	@RequestMapping("deleteStudent")
	public void delUser(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			String[] ids = request.getParameter("ids").split(",");
			for (String id : ids) {
				ss.deleteStudent(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误", e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}

}
