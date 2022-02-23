package tikal.atm.controller.api_data;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class WithdrawalApiObject {
	
	@Digits(fraction = 2, integer = 0)
	@NotNull
	@Min(value = (long) 0.01, message = "Amount must be greater than zero")
	@Max(value = 2000, message = "Maximum amount allowerd is 2000")
	private BigDecimal amount;
}