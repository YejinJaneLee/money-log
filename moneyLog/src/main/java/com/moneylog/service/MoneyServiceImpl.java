package com.moneylog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneylog.domain.MoneyVO;
import com.moneylog.mapper.MoneyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MoneyServiceImpl implements MoneyService{

	@Setter(onMethod_= @Autowired)
	private MoneyMapper mapper;
	
	@Override
	public List<MoneyVO> getList() {
		
		return null;
	}

	@Override
	public MoneyVO get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int regist(MoneyVO vo) {
		log.info("[MoneyService] regist == >");
		
		return mapper.insert(vo);
	}

	@Override
	public int modify(MoneyVO money) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int remove(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
