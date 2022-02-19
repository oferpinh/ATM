package tikal.atm.service.withdraw;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import tikal.atm.inventory.AtmInventory;

@Component
public class WithdrawAmountValidator {

	private static final int MAX_ALLOWED_WITHDRAWAL = 2000;
	
	@Autowired
	private AtmInventory atmInventory;
	
	public void validateWithdrawalRequestedAmount(double requestedAmount) {
		if (requestedAmount > MAX_ALLOWED_WITHDRAWAL) {
			throw new ForbiddenAmountException();
		}
		
		if (BigDecimal.valueOf(requestedAmount).scale() > 2) {
			throw new AmountWithMoreThanTwoDecimalPointsException();
		}
		
		double availableAmount = atmInventory.getAvailableAmount();
		if (requestedAmount > availableAmount) {
			throw new InsufficientFundsException(availableAmount);
		}
	}
	
	@ResponseStatus(value=HttpStatus.CONFLICT, reason="Insufficient funds")
	 protected class InsufficientFundsException extends RuntimeException {
		protected InsufficientFundsException(double availableAmount) {
			 super(String.format("Insufficient funds, maximum withdrawal available is %d", availableAmount));
		 }
	 }
	
	@ResponseStatus(value=HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, reason="Forbidden amount")
	protected class ForbiddenAmountException extends RuntimeException {
		protected ForbiddenAmountException() {
			super(String.format("Maximum allowed withdrawal is %d", MAX_ALLOWED_WITHDRAWAL));
		}
	}
	
	@ResponseStatus(value=HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, reason="Forbidden amount")
	protected class AmountWithMoreThanTwoDecimalPointsException extends RuntimeException {
		protected AmountWithMoreThanTwoDecimalPointsException() {
			super("Withdrawal with more than 2 decimal point is not allowed");
		}
	}
}
