package tikal.atm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tikal.atm.inventory.AtmInventory;
import tikal.atm.model.Money;
import tikal.atm.utils.Utils;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AtmInventory atmInventory;

	@PostMapping("/refill")
	public ResponseEntity<Double> refill(Money money, int amount) {
		atmInventory.refill(money, amount);
		
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

}
