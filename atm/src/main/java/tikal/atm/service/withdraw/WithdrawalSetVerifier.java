package tikal.atm.service.withdraw;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import tikal.atm.model.WithdrawalSet;

@Component
public class WithdrawalSetVerifier{
	
	private static final int MAX_COINS_ALLOWED = 50;
	
	public void verifyWithdrawalSet(WithdrawalSet withdrawalSet) {
		int numOfCoins = withdrawalSet.countCoins();
		
		if (numOfCoins > MAX_COINS_ALLOWED) {
			throw new TooMuchCoinsException();
		}
	}
	
	@ResponseStatus(value=HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, reason="Coins exceeded")
	protected class TooMuchCoinsException extends RuntimeException {
		protected TooMuchCoinsException() {
			super("Withdrawal exceeds allowed coins amount");
		}
	}

}
