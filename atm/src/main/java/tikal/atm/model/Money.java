package tikal.atm.model;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Money {	
	BILL_200(MoneyType.BILL, 200, "200"),
	BILL_100(MoneyType.BILL, 100, "100"),
	BILL_50(MoneyType.BILL, 50, "50"),
	BILL_20(MoneyType.BILL, 20, "20"),
	COIN_10(MoneyType.COIN, 10, "10"),
	COIN_5(MoneyType.COIN, 5, "5"),
	COIN_1(MoneyType.COIN, 1, "1"),
	COIN_10_CENT(MoneyType.COIN, 0.1, "0.1"),
	COIN_1_CENT(MoneyType.COIN, 0.01, "0.01");


	private MoneyType moneyType;
	private double value;
	private String stringValue;

	private static Map<Double, Money> valueMap = Stream.of(Money.values())
			.collect(Collectors.toMap(Money::getValue, money -> money));
	
	public static Optional<Money> fromValue(double value) {
		return Optional.ofNullable(valueMap.get(value));
	}
	
}

