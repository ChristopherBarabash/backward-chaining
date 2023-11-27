//package agents;
//
//
//
//import predicateCalculus.Rule;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class KnowledgeBase {
//	private List<Rule> rules;
//
//	public KnowledgeBase() {
//		this.rules = new ArrayList<>();
//	}
//
//	public void addRule(Rule rule) {
//		rules.add(rule);
//	}
//
//
//	public boolean backwardChaining(String goal, List<String> facts) {
//		if (facts.contains(goal)) {
//			return true;
//		}
//
//		for (Rule rule : rules) {
//			if (rule.applies(facts) && backwardChaining(rule.getConclusion(), facts)) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	public List<Formula> getFormulasByPredicateType(PredicateType predicateType){
//		return formulas
//			.stream()
//			.filter(formula -> (formula.getType().equals(FormulaType.ATOM) && formula.containsPredicate(predicateType)))
//			.collect(Collectors.toList());
//	}
//
//	public void tell(String formulaLine) {
//		formulas.add(FormulaParser.parseFormulaLine(formulaLine));
//	}
//
//	public void readFormulas(String filePath) throws IOException {
//		InputManager inputManager = new InputManager();
//		try {
//			inputManager.openFileForReading(filePath);
//			formulas = inputManager.readLines()
//				.map(formulaLine -> FormulaParser.parseFormulaLine(formulaLine)).collect(Collectors.toList());
//			inputManager.finishReading();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//}
