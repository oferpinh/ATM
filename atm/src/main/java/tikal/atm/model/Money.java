package tikal.atm.model;

import lombok.Getter;

public enum Money {	
	BILL_200_DOLLAR(200, MoneyType.BILL),
	BILL_100_DOLLAR(100, MoneyType.BILL),
	COIN_10_DOLLAR(100, MoneyType.COIN),
	COIN_5_DOLLAR(5, MoneyType.COIN),
	COIN_10_CENT(0.1f, MoneyType.COIN),
	COIN_1_CENT(0.01f, MoneyType.COIN);

	@Getter
	private MoneyType moneyType;
	@Getter
	private double value;
	
	Money(double value, MoneyType moneyType){
		this.moneyType=moneyType;
		this.value=value;
	}
	
}

