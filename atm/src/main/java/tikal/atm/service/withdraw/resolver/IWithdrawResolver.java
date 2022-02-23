package tikal.atm.service.withdraw.resolver;

import java.util.Map;

import tikal.atm.model.Money;
import tikal.atm.model.WithdrawalSet;

public interface IWithdrawResolver {

	WithdrawalSet calculateWithdrawBills(double amount, Map<Money, Integer> availableMoney);
}
