package tikal.atm.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import tikal.atm.model.Money;
import tikal.atm.model.WithdrawalPieceElement;
import tikal.atm.model.WithdrawalSet;

public class Utils {

	public static Map<Double, Integer> withdrawalSetToValueMap(WithdrawalSet withdrawalSet) {
		Map<Double, Integer> retMap = withdrawalSet.getPieceElementList().stream()
				.sorted(Comparator.comparing(pieceElement -> ((WithdrawalPieceElement) pieceElement).getMoney().getValue()).reversed())
				.collect(Collectors.toMap(
		            p -> p.getMoney().getValue(),
		            p -> p.getQuantity()));
		return retMap;
	}
	
	public static Map<Double, Integer> moneyMapToValueMap(Map<Money, Integer> withdrawalSet) {
		Map<Double, Integer> retMap = withdrawalSet.entrySet().stream()
				.collect(Collectors.toMap(
		            e -> e.getKey().getValue(),
		            e -> e.getValue())
		        );
		return retMap;
	}
}
