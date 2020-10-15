package com.moneylog.excel;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static void main(String[] args) {
		try {
			Map<String, Object> m_ExcelList = new HashMap<String, Object>();
			List<String> l_List = new ArrayList<>();
			
			FileInputStream fs = new FileInputStream("D:/test.xlsx");
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			
			// 첫번째 시트만 조회
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			
			for (int i=0; i < rows; i++) {
				HSSFRow row = sheet.getRow(i);
				
				int cells = row.getPhysicalNumberOfCells();
				for (int j=0; j < cells; j++) {
					HSSFCell cell = row.getCell(j);
					
					String value = "";
					
					switch (cell.getCellType()) {
					case FORMULA :
						value = cell.getCellFormula();
						break;
					case NUMERIC :
						value = cell.getNumericCellValue() + "";
						break;
					case STRING :
						value = cell.getStringCellValue();
						break;
					case BLANK :
						value = cell.getBooleanCellValue() + "";
						break;
					case ERROR :
						value = cell.getErrorCellValue() + "";
						break;
					}
					
					l_List.add(value);
					
					System.out.println(i+"번 행 : "+j+"번 열 값은: "+value);
				}
				
				if (i == 0) {
					m_ExcelList.put("ML_HEADER", l_List);
				} else {
					m_ExcelList.put("ML_CONTENT", l_List);
				}
			}
			System.out.println(m_ExcelList);
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			
		}
	}
}
