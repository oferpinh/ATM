package tikal.atm.service.withdraw.resolver;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import tikal.atm.model.Money;
import tikal.atm.model.MoneyType;
import tikal.atm.model.WithdrawalSet;

@Component
public class MoneyPreferenceWeightResolver {
	
	public static int getMoneyPreferenceWeight(Money money) {
		return getMoneyTypeWeight(money.getMoneyType());
	}

	private static int getMoneyTypeWeight(MoneyType moneyType) {
		return switch (moneyType) {
			case BILL -> 1;
			case COIN -> 5;
		};
	}
	
	public static int getWithdrawSetWeight(WithdrawalSet withdrawalSet) {
		return Stream.of(MoneyType.values())
				.mapToInt(type -> getMoneyTypeWeight(type) * withdrawalSet.countPiecesOfType(type))
				.sum();
	}
}
