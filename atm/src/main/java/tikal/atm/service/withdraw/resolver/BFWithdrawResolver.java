package tikal.atm.service.withdraw.resolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import tikal.atm.model.Money;
import tikal.atm.model.WithdrawalSet;

@Service
public class BFWithdrawResolver implements IWithdrawResolver{
	
	private final Comparator<Money> moneyComparatorDesc = new MoneyComparator().reversed();
	
	public WithdrawalSet calculateWithdrawBills(double amount, Map<Money, Integer> availableMoney){
		ArrayList<WithdrawalSet> allPermutations = new ArrayList<WithdrawalSet>();
		calculateWithdrawBillsPermutations(amount, availableMoney,
				new TreeMap<Money, Integer>(moneyComparatorDesc), allPermutations);
		return allPermutations.stream()
				.min(new WithdrawSetComparator())
				.orElse(null);
	}
	
	private void calculateWithdrawBillsPermutations(double amount,
			Map<Money, Integer> availableMoney, SortedMap<Money, Integer> curPermutationMoneyMap,
			List<WithdrawalSet> returnedPermutations){
		
		if (amount < 0.01) {//this check if amount == 0 with double data type tolerance
			returnedPermutations.add(new WithdrawalSet(curPermutationMoneyMap));
			return;
		}
		
		availableMoney.entrySet().stream()
			.filter(entry -> entry.getValue() > 0) //non empty currency
			.filter(entry -> entry.getKey().getValue() <= amount) //currency value is smaller than given amount
			.filter(entry -> !curPermutationMoneyMap.containsKey(entry.getKey())) // currency value was not already used
			.sorted(Entry.comparingByKey(moneyComparatorDesc))
			.findFirst() // get highest relevant currency
			.ifPresent(maxMoneyEntry -> {
				permutateWithGivenMoney(amount, availableMoney, curPermutationMoneyMap, returnedPermutations,
						maxMoneyEntry);
			});
	}

	private void permutateWithGivenMoney(double amount, Map<Money, Integer> availableMoney,
			SortedMap<Money, Integer> curPermutationMoneyMap, List<WithdrawalSet> returnedPermutations,
			Entry<Money, Integer> maxMoneyEntry) {
		int quotient = (int) Math.floor(amount / maxMoneyEntry.getKey().getValue());
		int maxQtyOfMoneyType = maxMoneyEntry.getValue() < quotient ? maxMoneyEntry.getValue() : quotient;
		
		switch (maxMoneyEntry.getKey().getMoneyType()){
			case COIN -> {
				curPermutationMoneyMap.put(maxMoneyEntry.getKey(), maxQtyOfMoneyType);
				calculateWithdrawBillsPermutations(amount - maxQtyOfMoneyType*maxMoneyEntry.getKey().getValue(),
						availableMoney, curPermutationMoneyMap, returnedPermutations);
			}
			case BILL -> {
				IntStream.range(0, maxQtyOfMoneyType+1)
				.forEach(chosenQty -> {
					SortedMap<Money, Integer> newPermutation = new TreeMap<Money, Integer>(moneyComparatorDesc);
					newPermutation.putAll(curPermutationMoneyMap);
					newPermutation.put(maxMoneyEntry.getKey(), chosenQty);
					calculateWithdrawBillsPermutations(amount - chosenQty*maxMoneyEntry.getKey().getValue(),
							availableMoney, newPermutation, returnedPermutations);
				});
			}
		}
	}
}
	
