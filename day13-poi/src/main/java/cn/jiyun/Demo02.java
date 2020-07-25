package cn.jiyun;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Demo02 {
	public static void main(String[] args) throws Exception {

		// poi中的四个类
		// WorkBook 工作簿
		// Sheet 表
		// row 行
		// cell 格
		
		// 得到已有的excel文档对象
		XSSFWorkbook wb = new XSSFWorkbook("F://demo01.xlsx");
		
		// 得到工作簿中的第一个表对象
		XSSFSheet sheet = wb.getSheetAt(0);
		
		// 得到最后一行的下标
		int lastRowNum = sheet.getLastRowNum();
		System.out.println(lastRowNum);
		
		List<User> list = new ArrayList<>();
		
		for (int i = 1; i < lastRowNum+1; i++) {
			User u = new User();

			XSSFRow row = sheet.getRow(i);
			double id = row.getCell(0).getNumericCellValue();
			u.setUid((int)id);
			
			String username = row.getCell(1).getStringCellValue();
			u.setUsername(username);
			
			String password = row.getCell(2).getStringCellValue();
			u.setPassword(password);
			
			list.add(u);
			
		}
		
		System.out.println(list);

	}
}
