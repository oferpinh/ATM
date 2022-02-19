package tikal.atm.service.withdraw;

import java.util.SortedMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import tikal.atm.model.Money;
import tikal.atm.model.MoneyType;

@Component
public class WithdrawalSetVerifier{
	
	private static final int MAX_COINS_ALLOWED = 50;
	
	public void verifyWithdrawalSet(SortedMap<Money, Integer> withdrawalSet) {
		int numOfCoins = withdrawalSet.entrySet().stream()
			.filter(entry -> entry.getKey().getMoneyType().equals(MoneyType.COIN))
			.map(entry -> entry.getValue())
			.mapToInt(Integer::valueOf)
		    .sum();
		
		if (numOfCoins > MAX_COINS_ALLOWED) {
			throw new ExceedsAllowedCoinsException();
		}
	}
	
	@ResponseStatus(value=HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, reason="Coins exceeded")
	protected class ExceedsAllowedCoinsException extends RuntimeException {
		protected ExceedsAllowedCoinsException() {
			super("Withdrawal exceeds allowed coins amount");
		}
	}

}
