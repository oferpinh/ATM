package tikal.atm.inventory;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import tikal.atm.model.Money;
import tikal.atm.model.MoneyComparator;

@Component
public class AtmInventory {
	private SortedMap<Money, Integer> inventory = new TreeMap<Money, Integer>(new MoneyComparator());
	
	private AtmInventory() {
		initInventory();
	}

	public void initInventory() {
		for (Money money : Money.values()) {
			inventory.put(money, 10);
		}
	}
	
	public SortedMap<Money,Integer> getInventory(){
		return inventory.entrySet().stream()
			.filter(x -> x.getValue() > 0)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, TreeMap::new));
	}
	
	public void withdraw(Money money, int amount) {
		var availableAmount = inventory.get(money);
		if (availableAmount < amount) {
			throw new InsufficientFundsException(money);
		}
		inventory.put(money, availableAmount-amount);
	}
	
	public void withdraw(Map<Money, Integer> map) {
		for (var entry: map.entrySet()) {
			withdraw(entry.getKey(), entry.getValue());
		}
	}
	
	public void refill(Money money, int amount) {
		var availableAmount = inventory.get(money);
		inventory.put(money, availableAmount+amount);
	}
	
	public double getAvailableAmount(Money money) {
		return inventory.get(money)*money.getValue();
	}
	
	public double getAvailableAmount() {
		double sum = 0;
		for (var entry: inventory.entrySet()) {
			sum+=(entry.getKey().getValue()*entry.getValue());
		}
		return sum;
	}
	
	 @ResponseStatus(value=HttpStatus.CONFLICT, reason="Insufficient funds") // 409
	 public class InsufficientFundsException extends RuntimeException {
		 public InsufficientFundsException(Money money) {
			 super(String.format("Insufficient funds of type %d", money.getValue()));
		 }
	 }
}
