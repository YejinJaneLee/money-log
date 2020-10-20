package com.moneylog.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moneylog.domain.MoneyVO;
import com.moneylog.service.MoneyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/moneyLog/*")
@AllArgsConstructor
public class CalendarController {
	
	private MoneyService service;
	
	@GetMapping(value = "/calendar")
	public void calendar () {
		List<String> list = Arrays.asList("a","b");
		
	}
	@GetMapping(value = "/manage/getExcelData")
	public void excel () {
		List<String> list = Arrays.asList("a","b");
		
	}
	@GetMapping(value = "/list", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<String>> list (Model model) {
		List<String> list = Arrays.asList("a","b");
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping(value="/registList", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public void registExcelList (@RequestBody MoneyVO vo) {
		vo.setOwnerId("test");
		service.regist(vo);
	}
}
