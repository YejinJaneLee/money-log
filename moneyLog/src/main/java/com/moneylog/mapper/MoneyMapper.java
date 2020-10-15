package com.moneylog.mapper;

import java.util.List;

import com.moneylog.domain.MoneyVO;

public interface MoneyMapper {

	public List<MoneyVO> getList(); 
	public int insert(MoneyVO vo);
	public int update(MoneyVO vo);
	public int delete(int mno);
}
