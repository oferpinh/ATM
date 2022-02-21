package tikal.atm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WithdrawalPieceElement{
	
	@Getter
	private Money money;
	
	@Getter
	private int quantity;
	
	public double getTotalValue() {
		return money.getValue()*quantity;
	}
}
