package tikal.atm.service.withdraw.resolver;

import java.util.Comparator;

import tikal.atm.model.Money;

public class MoneyComparator implements Comparator<Money>
{
	public int compare(Money m1, Money m2){
    	double comparison = m1.getValue() - m2.getValue();
        if (comparison > 0) {
        	return 1;
        }
        if (comparison < 0) {
        	return -1;
        }
        return 0;
    }
}