package tikal.atm.service.withdraw.resolver;

import java.util.Comparator;

import tikal.atm.model.WithdrawalSet;

public class WithdrawSetComparator implements Comparator<WithdrawalSet>
{
    public int compare(WithdrawalSet ws1, WithdrawalSet ws2){
    	return MoneyPreferenceWeightResolver.getWithdrawSetWeight(ws1) - MoneyPreferenceWeightResolver.getWithdrawSetWeight(ws2);
    }
}