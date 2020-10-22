package com.moneylog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
	}
	@GetMapping(value = "/manage/getExcelData")
	public void excel () {
		
	}
	@GetMapping(value = "/list/{month}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<MoneyVO>> list (@PathVariable("month") String month) {
		log.info("[CalendarController] list == > " + month);
		
		return new ResponseEntity<>(service.getListByMonth(month), HttpStatus.OK);
	}
	
	@PostMapping(value="/registList", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public void registExcelList (@RequestBody MoneyVO vo) {
		log.info("[CalendarController] registExcelList == > ");
		
		vo.setOwnerId("test");
		service.regist(vo);
	}
}
