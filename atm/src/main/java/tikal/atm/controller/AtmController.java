package tikal.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tikal.atm.controller.api_data.WithdrawalApiObject;
import tikal.atm.controller.api_data.WithdrawalResult;
import tikal.atm.service.withdraw.WithdrawService;

@Controller
@RequestMapping("/atm")
public class AtmController {

	@Autowired
	private WithdrawService withdrawService;
	
	@PostMapping("withdrawal")
	public ResponseEntity<WithdrawalResult> withdraw(WithdrawalApiObject withdrawalApiObject) {
		
		System.out.println(String.format("Got withdraw request. amount: %1$,.2f", withdrawalApiObject.getAmount()));
		var withdrawalSet = withdrawService.withdraw(withdrawalApiObject.getAmount().doubleValue());
		
		return new ResponseEntity<WithdrawalResult>(new WithdrawalResult(withdrawalSet), HttpStatus.OK);
	}
	
}
