package tikal.atm.utils;

import java.util.Map;
import java.util.stream.Collectors;

import tikal.atm.model.Money;

public class Utils {

	public static Map<Double, Integer> moneyMapToValueMap(Map<Money, Integer> withdrawalSet) {
		Map<Double, Integer> retMap = withdrawalSet.entrySet().stream()
				.collect(Collectors.toMap(
		            e -> e.getKey().getValue(),
		            e -> e.getValue())
		        );
		return retMap;
	}
}
