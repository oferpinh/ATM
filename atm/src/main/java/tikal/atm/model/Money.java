package tikal.atm.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Money {	
	BILL_200(MoneyType.BILL, 200),
	BILL_100(MoneyType.BILL, 100),
	BILL_50(MoneyType.BILL, 50),
	BILL_20(MoneyType.BILL, 20),
	COIN_10(MoneyType.COIN, 10),
	COIN_5(MoneyType.COIN, 5),
	COIN_1(MoneyType.COIN, 1),
	COIN_10_CENT(MoneyType.COIN, 0.1),
	COIN_1_CENT(MoneyType.COIN, 0.01);

	private MoneyType moneyType;
	private double value;
	
}

