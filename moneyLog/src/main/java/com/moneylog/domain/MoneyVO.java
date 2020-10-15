package com.moneylog.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MoneyVO {

	private int		mno;
	private Date	payDate;
	private String	payMethod;
	private String	item;
	private int		Amount;
	private String	ownerId;
	private String	category;
	private Date	regDate;
	private Date	updateDate;
	
}
