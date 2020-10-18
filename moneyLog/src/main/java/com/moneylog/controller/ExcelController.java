package com.moneylog.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moneylog.domain.MoneyVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
/*
 * [���� ������ ��ȸ]
 * 1. ù��° row�� ����� ���� => ���� ���� �°� ���� �ʿ�
 * 2. xls Ȯ���ڸ� ����
 * */
@RestController
@Log4j
@AllArgsConstructor
public class ExcelController {
	
	/**
	 * (����)���� ���ε�
	 * 
	 * @param	uploadFile		���ε��� ����
	 * @return	saveFilePath	���ε�� ���� ���
	 */
	@PostMapping(value="/uploadFile")
	public ResponseEntity<String> uploadFile(MultipartFile[] uploadFile) {
		String uploadFolder = "c:\\upload";
		String uploadFolderPath = getFolder();
		String saveFilePath = "";
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("##### File Name ==> " + multipartFile.getOriginalFilename());
			log.info("##### File Size ==> " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName  = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid + "_" + uploadFileName;
			
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile);
				saveFilePath = URLEncoder.encode(saveFile.getAbsolutePath(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<String>(saveFilePath, HttpStatus.OK);
	}
	
	/**
	 * (����)���� ���� ��ȸ
	 * 
	 * @param	path	���ϰ��
	 * @return	list	���� ������
	 */
	@GetMapping(value="/readFile", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<List<String>>> readFile(@RequestParam("path") String path) {
		List<List<String>> l_ExcelList = new ArrayList<>();
		
		try {
			
			FileInputStream fs = new FileInputStream(path);
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			
			// ù��° ��Ʈ�� ��ȸ
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
						
//					}
				}
				
				l_ExcelList.add(l_List);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(l_ExcelList, HttpStatus.OK);
	}
	
	/**
	 * ���� ��¥�� ���� ��� ȹ��
	 * 
	 * @return	String	���� ���
	 */
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	@PostMapping(value="/registList", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public static void registExcelList (@RequestBody MoneyVO vo) {
		vo.setOwnerId("test");
	}
	
}
