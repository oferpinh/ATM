package tikal.atm.service.withdraw;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import tikal.atm.model.Money;
import tikal.atm.model.WithdrawalSet;

@Component
public class ScipWithdrawResolver {
	
	private MPSolver solver = MPSolver.createSolver("SCIP");
	
	public WithdrawalSet calculateWithdrawBills(double amount, Map<Money, Integer> availableMoney){
		
		// integer non-negative variables.
		Map<Money, MPVariable> variables = availableMoney.entrySet().stream()
			    .collect(Collectors.toMap(Entry::getKey, entry -> solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, entry.getKey().name())));
		
		// availability constraints.
		availableMoney.entrySet().stream()
				.forEach(entry -> {
					var availabilityConstaint = solver.makeConstraint(Double.NEGATIVE_INFINITY, entry.getValue(), entry.getKey().name());
					availabilityConstaint.setCoefficient(variables.get(entry.getKey()), 1);
				});
		

		// 200*bill200 + 100*bill100 + 10*coin10 <= 350.
		MPConstraint moneyValuesConstraintUpperBound = solver.makeConstraint(Double.NEGATIVE_INFINITY, amount, "moneyValuesConstraintUpperBound");
		availableMoney.entrySet().stream()
				.forEach(entry -> moneyValuesConstraintUpperBound.setCoefficient(variables.get(entry.getKey()), entry.getKey().getValue()));
		// -200*bill200 + -100*bill100 + -10*coin10 <= -350.
		MPConstraint moneyValuesConstraintLowerBound = solver.makeConstraint(Double.NEGATIVE_INFINITY, -amount, "moneyValuesConstraintLowerBound");
		availableMoney.entrySet().stream()
				.forEach(entry -> moneyValuesConstraintLowerBound.setCoefficient(variables.get(entry.getKey()), -entry.getKey().getValue()));
		
		// Minimize 1*bill200 + 1*bill100 + 5*coin10.
		MPObjective objective = solver.objective();
		variables.entrySet().stream()
			.forEach(entry -> 
				objective.setCoefficient(entry.getValue(), 
						MoneyPreferenceWeightResolver.getPreferenceWeightByMoneyType(entry.getKey())));
		objective.setMinimization();
		
		final MPSolver.ResultStatus resultStatus = solver.solve();
		
		if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
			var map = variables.entrySet().stream()
					.filter(entry -> entry.getValue().solutionValue() > 0)
				    .collect(Collectors.toMap(Entry::getKey, entry -> (int)entry.getValue().solutionValue()));
			return new WithdrawalSet(map);
		    } else {
		    	return null;
		    }
	}
	
}
