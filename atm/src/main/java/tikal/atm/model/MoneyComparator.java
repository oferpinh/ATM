package tikal.atm.model;

import java.util.Comparator;

public class MoneyComparator implements Comparator<Money>
{
    public int compare(Money m1, Money m2){
        return (int) (m1.getValue() - m2.getValue());
    }
}