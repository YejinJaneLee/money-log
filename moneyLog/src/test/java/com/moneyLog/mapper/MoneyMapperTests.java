package com.moneyLog.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.moneylog.domain.MoneyVO;
import com.moneylog.mapper.MoneyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MoneyMapperTests {

	@Setter(onMethod_=@Autowired)
	private MoneyMapper mapper;
	
	/*
	 * 목록 조회
	 */
	/*@Test
	public void testList() {
		List<MoneyVO> list = mapper.getList();
		
		list.forEach(l -> log.info(l));
	}*/
	
	/*
	 * 등록
	 */
	/*@Test
	public void TestCreate() {
		MoneyVO vo = new MoneyVO();
		
		vo.setItem("test2");
		vo.setAmount(2000);
		vo.setOwnerId("test");
		vo.setPayMethod("credit");
		vo.setPayDate(new Date());
		vo.setCategory("");
		
		if(mapper.insert(vo) == 1) log.info("success");
	}*/
	
	/*
	 * 수정
	 */
	/*
	@Test
	public void TestUpdate() {
		MoneyVO vo = new MoneyVO();
		
		vo.setItem("test2_modify");
		vo.setAmount(3000);
		vo.setOwnerId("test");
		vo.setPayMethod("credit");
		vo.setPayDate(new Date());
		vo.setCategory("");
		vo.setMno(2);
		
		if(mapper.update(vo) == 1) log.info("success");
	}
	*/
	/*
	 * 삭제
	 */
	/*
	@Test
	public void TestDelete() {
		int mno = 2;
		mapper.delete(mno);
	}
	*/
}
