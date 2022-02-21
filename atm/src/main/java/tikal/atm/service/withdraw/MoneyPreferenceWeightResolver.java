package tikal.atm.service.withdraw;

import org.springframework.stereotype.Component;

import tikal.atm.model.Money;

@Component
public class MoneyPreferenceWeightResolver {
	
	public static double getPreferenceWeightByMoneyType(Money moneyType) {
		return switch (moneyType.getMoneyType()) {
			case BILL -> 1;
			case COIN -> 5;
		};
	}
}
