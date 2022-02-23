package tikal.atm.controller.api_data;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import tikal.atm.model.MoneyType;
import tikal.atm.model.WithdrawalPieceElement;
import tikal.atm.model.WithdrawalSet;

@Data
public class WithdrawalResult {
	
	private BillsSplitResult result;
	
	public WithdrawalResult(WithdrawalSet withdrawalSet) {
		Map<Boolean, List<WithdrawalPieceElement>> billsCoinsPartition = withdrawalSet.getPieceElementList().stream()
				.sorted(Comparator.comparing(pieceElement -> ((WithdrawalPieceElement) pieceElement).getMoney().getValue()).reversed())
				.collect(Collectors.partitioningBy(pieceElement -> pieceElement.getMoney().getMoneyType() == MoneyType.BILL));
		
		Map<String, Integer> bills = billsCoinsPartition.get(true).stream()
				.collect(Collectors.toMap(
		            p -> p.getMoney().getStringValue(),
		            p -> p.getQuantity()));
		Map<String, Integer> coins = billsCoinsPartition.get(false).stream()
				.collect(Collectors.toMap(
						p -> p.getMoney().getStringValue(),
						p -> p.getQuantity()));
		
		
		result = new BillsSplitResult(bills, coins);
	}
}

@Data
@AllArgsConstructor
class BillsSplitResult {
	private Map<String, Integer> bills;
	private Map<String, Integer> coins;
}
