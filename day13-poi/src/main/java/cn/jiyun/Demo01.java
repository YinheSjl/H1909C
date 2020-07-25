package cn.jiyun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Demo01 {

	public static void main(String[] args) throws Exception {
		// poi中的四个类  
		// WorkBook 工作簿
		// Sheet 表
		// row 行
		// cell 格
		
		// poi的导出，从java代码中，将数据到处到excel文件中
		List<User> list = new ArrayList<>();
		
		for (int i = 0; i <5; i++) {
			User u = new User();
			u.setUid(i+1);
			u.setUsername("张三"+i);
			u.setPassword("李四"+i);
			list.add(u);
		}
		// 创建工作簿对象
		XSSFWorkbook wb = new XSSFWorkbook();
		// 通过工作簿对象，创建工作表对象
		XSSFSheet sheet = wb.createSheet();
		// 创建第0行
		XSSFRow row0 = sheet.createRow(0);
		// 编号，账号，密码
		XSSFCell cell0 = row0.createCell(0);
		cell0.setCellValue("编号");
		XSSFCell cell1 = row0.createCell(1);
		cell1.setCellValue("账号");
		row0.createCell(2).setCellValue("密码");
		
		
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.createRow(i+1);
			User user = list.get(i);
			row.createCell(0).setCellValue(user.getUid());
			row.createCell(1).setCellValue(user.getUsername());
			row.createCell(2).setCellValue(user.getPassword());
		}
		
		OutputStream out = new FileOutputStream(new File("F://demo01.xlsx"));
		
		// 回显
		wb.write(out);
		
		out.close();
		
		
	}
	
}
