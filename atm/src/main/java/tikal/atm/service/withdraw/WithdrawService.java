package tikal.atm.service.withdraw;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import tikal.atm.inventory.AtmInventory;
import tikal.atm.model.WithdrawalSet;
import tikal.atm.service.withdraw.resolver.BFWithdrawResolver;

@Service
public class WithdrawService {
	
	@Autowired
	private AtmInventory atmInventory;
//	@Autowired
//	private ScipWithdrawResolver withdrawResolver;
	@Autowired
	private BFWithdrawResolver withdrawResolver;
	@Autowired
	private WithdrawAmountValidator amountValidator;
	@Autowired
	private WithdrawalSetVerifier withdrawalSetVerifier;
	
	public WithdrawalSet withdraw(double requestedAmount) {
		amountValidator.validateWithdrawalRequestedAmount(requestedAmount);
		
			var withdrawalSet = withdrawResolver.calculateWithdrawBills(requestedAmount, atmInventory.getInventory());
			if (withdrawalSet == null) {
				throw new InsufficientFundsException(atmInventory.getAvailableAmount());
			}
			
			withdrawalSetVerifier.verifyWithdrawalSet(withdrawalSet);
			
			atmInventory.withdraw(withdrawalSet);
			
			return withdrawalSet;
		
	}


	@ResponseStatus(value=HttpStatus.CONFLICT, reason="Insufficient funds")
	 private class InsufficientFundsException extends RuntimeException {
		 public InsufficientFundsException(double maxAmount) {
			 super(String.format("Insufficient funds - current available amount is %1$,.2f", maxAmount));
		 }
	 }
}
