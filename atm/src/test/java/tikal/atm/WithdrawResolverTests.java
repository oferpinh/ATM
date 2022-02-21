package tikal.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.ortools.Loader;

import tikal.atm.model.Money;
import tikal.atm.service.withdraw.ScipWithdrawResolver;

@SpringBootTest
class WithdrawResolverTests {

	@InjectMocks
	private ScipWithdrawResolver scipWithdrawResolver;
	
	@BeforeAll
	public static void setup() {
		Loader.loadNativeLibraries();
    }

	
	@Test
	void calculateWithdrawBillsTest1500Good() {
		Map<Money, Integer> availableMoney = createMoneyMap(2, 10, 10, 10, 10, 10, 10, 10, 10);
		double amount = 1500;
		var solution = scipWithdrawResolver.calculateWithdrawBills(amount, availableMoney);
		assertNotNull(solution);
		assertEquals(14, solution.countPieces());
		assertEquals(amount, solution.calculateTotalValue());
	}
	
	@Test
	void calculateWithdrawBillsTest0_9Good() {
		double amount = 0.9;
		Map<Money, Integer> availableMoney = createMoneyMap(2, 10, 10, 10, 10, 10, 10, 10, 10);
		var solution = scipWithdrawResolver.calculateWithdrawBills(amount, availableMoney);
		assertNotNull(solution);
		assertEquals(9, solution.countPieces());
		assertEquals(amount, solution.calculateTotalValue());
	}
	
	@Test
	void calculateWithdrawBillsTest90Good() {
		double amount = 90;
		Map<Money, Integer> availableMoney = createMoneyMap(2, 10, 10, 10, 10, 10, 10, 10, 10);
		var solution = scipWithdrawResolver.calculateWithdrawBills(amount, availableMoney);
		assertNotNull(solution);
		assertEquals(3, solution.countPieces());
		assertEquals(amount, solution.calculateTotalValue());
	}
	
	@Test
	void calculateWithdrawBillsTest60Good() {
		double amount = 60;
		Map<Money, Integer> availableMoney = createMoneyMap(2, 10, 10, 10, 10, 10, 10, 10, 10);
		var solution = scipWithdrawResolver.calculateWithdrawBills(amount, availableMoney);
		assertNotNull(solution);
		assertEquals(3, solution.countPieces());
		assertEquals(solution.calculateTotalValue(), amount);
	}
	
	@Test
	void calculateWithdrawBillsTest0_9GoodPenny() {
		double amount = 0.9;
		Map<Money, Integer> availableMoney = createMoneyMap(2, 10, 10, 10, 10, 10, 10, 2, 90);
		var solution = scipWithdrawResolver.calculateWithdrawBills(amount, availableMoney);
		assertNotNull(solution);
		assertEquals(72, solution.countPieces());
		assertEquals(solution.calculateTotalValue(), amount);
	}
	
	@Test
	void calculateWithdrawBillsTest0_9Null() {
		double amount = 0.9;
		Map<Money, Integer> availableMoney = createMoneyMap(2, 10, 10, 10, 10, 10, 10, 2, 2);
		var solution = scipWithdrawResolver.calculateWithdrawBills(amount, availableMoney);
		assertNull(solution);
	}

	private Map<Money, Integer> createMoneyMap(int amount200, int amount100, int amount50, int amount20,  int amount10, int amount5,
			int amount1, int amount10Cent, int amount1Cent) {
		Map<Money, Integer> availableMoney = new HashMap<Money, Integer>();
		availableMoney.put(Money.BILL_200, amount200);
		availableMoney.put(Money.BILL_100, amount100);
		availableMoney.put(Money.BILL_50, amount50);
		availableMoney.put(Money.BILL_20, amount20);
		availableMoney.put(Money.COIN_10, amount10);
		availableMoney.put(Money.COIN_5, amount5);
		availableMoney.put(Money.COIN_1, amount1);
		availableMoney.put(Money.COIN_10_CENT, amount10Cent);
		availableMoney.put(Money.COIN_1_CENT, amount1Cent);
		return availableMoney;
	}

}
