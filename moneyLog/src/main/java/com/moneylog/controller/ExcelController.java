package com.moneylog.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
/*
 * [엑셀 데이터 조회]
 * 1. 첫번째 row는 헤더로 간주 => 엑셀 내용 맞게 정리 필요
 * 2. xls 확장자만 구현
 * 3. 파일 경로를 UI에서 받지 못하여 우선 특정 경로에 있는 파일 조회
 * */
@Controller
@Log4j
@AllArgsConstructor
public class ExcelController {
	
	
	@GetMapping(value="/readExcel", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public static ResponseEntity<List<List<String>>> readExcel(@RequestParam("fileName") String filename) {
		List<List<String>> l_ExcelList = new ArrayList<>();
		
		try {
			
			FileInputStream fs = new FileInputStream("D:/path/" + filename);
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			
			// 첫번째 시트만 조회
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			
			for (int i=0; i < rows; i++) {
				HSSFRow row = sheet.getRow(i);
				int cells = row.getPhysicalNumberOfCells();
				
				List<String> l_List = new ArrayList<>();
				for (int j=0; j < cells; j++) {
					HSSFCell cell = row.getCell(j);
					
					String value = "";
//					if (cell == null) {
//						continue;
//					} else {
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
							//value = cell.getBooleanCellValue() + "";
							value = "";
							break;
						case ERROR :
							value = cell.getErrorCellValue() + "";
							break;
						}
						l_List.add(value);
						
						System.out.println(i+"번 행 : "+j+"번 열 값은: "+value);
//					}
				}
				
				l_ExcelList.add(l_List);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(l_ExcelList, HttpStatus.OK);
	}
	
	@PostMapping(value="/registList", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public static void registExcelList (@RequestBody String test) {
		log.info(test);
	}
}
