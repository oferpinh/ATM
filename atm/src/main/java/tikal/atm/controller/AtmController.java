package tikal.atm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tikal.atm.controller.api_data.WithdrawalApiObject;
import tikal.atm.service.withdraw.WithdrawService;
import tikal.atm.utils.Utils;

@Controller
@RequestMapping("/atm")
public class AtmController {

	@Autowired
	private WithdrawService withdrawService;
	
	@PostMapping("withdrawal")
	public ResponseEntity<Map<Double, Integer>> withdraw(WithdrawalApiObject withdrawalApiObject) {
		
		var withdrawalSet = withdrawService.withdraw(withdrawalApiObject.getAmount().doubleValue());
		
		return new ResponseEntity<Map<Double, Integer>>(Utils.withdrawalSetToValueMap(withdrawalSet), HttpStatus.OK);
	}
	
}
