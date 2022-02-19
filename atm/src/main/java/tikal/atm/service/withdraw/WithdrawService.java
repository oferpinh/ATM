package tikal.atm.service.withdraw;

import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tikal.atm.inventory.AtmInventory;
import tikal.atm.model.Money;
import tikal.atm.service.MoneyCalculator;

@Service
public class WithdrawService {
	
	@Autowired
	private AtmInventory atmInventory;
	@Autowired
	private MoneyCalculator moneyCalculator;
	@Autowired
	private WithdrawAmountValidator amountValidator;
	@Autowired
	private WithdrawalSetVerifier withdrawalSetVerifier;
	
	public SortedMap<Money, Integer> withdraw(double requestedAmount) {
		amountValidator.validateWithdrawalRequestedAmount(requestedAmount);
		
		SortedMap<Money, Integer> withdrawalSet = moneyCalculator.calculateWithdrawBills(requestedAmount, atmInventory.getInventory());
		
		withdrawalSetVerifier.verifyWithdrawalSet(withdrawalSet);
		
		atmInventory.withdraw(withdrawalSet);
		
		return withdrawalSet;
	}


}
