package tikal.atm.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WithdrawalSet{
	
	@Getter
	private List<WithdrawalPieceElement> pieceElementList;
	
	public WithdrawalSet(Map<Money, Integer> map) {
		pieceElementList = map.entrySet().stream()
			.map(entry -> new WithdrawalPieceElement(entry.getKey(), entry.getValue()))
			.collect(Collectors.toList());
	}
	
	public int countCoins() {
		return countPiecesOfType(MoneyType.COIN);
	}

	public int countPiecesOfType(MoneyType moneyType) {
		return pieceElementList.stream()
			.filter(piece -> piece.getMoney().getMoneyType().equals(moneyType))
			.map(piece -> piece.getQuantity())
			.mapToInt(Integer::valueOf)
		    .sum();
	}
	
	public int countBills() {
		return countPiecesOfType(MoneyType.BILL);
	}
	
	public int countAllPieces() {
		return pieceElementList.stream()
				.map(piece -> piece.getQuantity())
				.mapToInt(Integer::valueOf)
				.sum();
	}
	
	public double calculateTotalValue() {
		return pieceElementList.stream()
			.map(piece -> piece.getTotalValue())
			.mapToDouble(Double::valueOf)
		    .sum();
	}
}
