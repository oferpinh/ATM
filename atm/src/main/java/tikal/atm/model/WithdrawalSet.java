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
	
	public int getNumOfCoins() {
		return pieceElementList.stream()
			.filter(piece -> piece.getMoney().getMoneyType().equals(MoneyType.COIN))
			.map(piece -> piece.getQuantity())
			.mapToInt(Integer::valueOf)
		    .sum();
	}
	
	public int countPieces() {
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
