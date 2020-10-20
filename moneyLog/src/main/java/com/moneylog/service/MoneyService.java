package com.moneylog.service;

import java.util.List;

import com.moneylog.domain.MoneyVO;

public interface MoneyService {
	public List<MoneyVO> getList();
	public MoneyVO get(int id);
	public int regist(MoneyVO money);
	public int modify(MoneyVO money);
	public int remove(int id);
	
}
