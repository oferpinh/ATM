package tikal.atm.service;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import tikal.atm.inventory.AtmInventory;
import tikal.atm.model.Money;
import tikal.atm.model.MoneyComparator;

@Service
public class MoneyCalculator {
	
	@Autowired
	private AtmInventory atmInventory;
	
	public SortedMap<Money, Integer> calculateWithdrawBills(double amount, Map<Money, Integer> availableMoney){
		SortedMap<Money, Integer> withdrawnMoneyMap = new TreeMap<Money, Integer>(new MoneyComparator().reversed());
		return calculateWithdrawBillsRec(amount, availableMoney, withdrawnMoneyMap);
	}
	
	private SortedMap<Money, Integer> calculateWithdrawBillsRec(double amount,
			Map<Money, Integer> availableMoney, SortedMap<Money, Integer> withdrawnMoneyMap){
		
		if (amount == 0) {
			return withdrawnMoneyMap;
		}
		
		Entry<Money, Integer> maxMoneyEntry = atmInventory.getInventory().entrySet().stream()
			.filter(entry -> entry.getValue() > 0) //non empty currency
			.filter(entry -> entry.getKey().getValue() <= amount) //currency value is smaller than given amount
			.findFirst()
			.orElseThrow(() -> new InsufficientFundsException()); // no permutation of the inventory can satisfy the request
		
		int quotient = (int) Math.floor(amount / maxMoneyEntry.getKey().getValue());
		int numOfBills = maxMoneyEntry.getValue() < quotient ? maxMoneyEntry.getValue() : quotient;
		availableMoney.put(maxMoneyEntry.getKey(), maxMoneyEntry.getValue()-numOfBills);
		withdrawnMoneyMap.put(maxMoneyEntry.getKey(), numOfBills);
		
		return calculateWithdrawBillsRec(amount - numOfBills*maxMoneyEntry.getKey().getValue(), availableMoney, withdrawnMoneyMap);
	}
	
	 @ResponseStatus(value=HttpStatus.CONFLICT, reason="Insufficient funds")
	 private class InsufficientFundsException extends RuntimeException {
		 public InsufficientFundsException() {
			 super("Insufficient funds");
		 }
	 }
}
