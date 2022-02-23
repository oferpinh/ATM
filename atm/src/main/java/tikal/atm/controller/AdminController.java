package tikal.atm.controller;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import tikal.atm.inventory.AtmInventory;
import tikal.atm.model.Money;
import tikal.atm.utils.Utils;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AtmInventory atmInventory;

	@PostMapping("/refill")
	public ResponseEntity<Double> refill(double money, int amount) {
		System.out.println(String.format("Got refill request. amount: %d, type %s", amount, money));
		Money moneyEnum = Money.fromValue(money).orElseThrow(() -> new UnsupportedMoneyException());
		atmInventory.refill(moneyEnum, amount);
		
		Double availableAmount = atmInventory.getAvailableAmount();
		return new ResponseEntity<Double>(availableAmount, HttpStatus.OK);
	}
	
	@GetMapping("/getInventoryMap")
	public ResponseEntity<Map<Double, Integer>> getInventory() {
		var inventory = atmInventory.getInventory();
		return new ResponseEntity<Map<Double, Integer>>(Utils.moneyMapToValueMap(inventory), HttpStatus.OK);
	}
	
	@GetMapping("/getAvailableAmount")
	public ResponseEntity<Double> getAvailableAmount() {
		Double availableAmount = atmInventory.getAvailableAmount();
		return new ResponseEntity<Double>(availableAmount, HttpStatus.OK);
	}
	
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Unsupported Money") // 400
	 public class UnsupportedMoneyException extends RuntimeException {
		 public UnsupportedMoneyException() {
			 super(String.format("Unsupported money. supported values are: %s", 
					 Stream.of(Money.values())
					 .map(curMoney -> curMoney.getValue())
					 .collect(Collectors.toList())));
		 }
	 }

}
