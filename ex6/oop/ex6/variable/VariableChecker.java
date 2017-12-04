package oop.ex6.variable;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.blocks.Block;
import oop.ex6.main.RegexMenu;
import oop.ex6.main.ReservedWords;

/**
 * This class is variable checker, makes the logic checks, crates variables only if it's legal, can check if 
 * variable can be assigned as condition, check if the type fits etc.
 */
public class VariableChecker {
	private static boolean isFinal;
	private static boolean isDefined;
	private static String variableType;
	private static TypeChecker typeChecker;
	
	/**
	 * Constructor of this class, sets all the variable fields as default.
	 */
	public VariableChecker() {
		isFinal = false;
		isDefined = false;
		variableType = RegexMenu.EMPTY_LINE.getValue();
		typeChecker = new TypeChecker();
	}
	
	/**
	 * prepare the variable, and when check all the logic checks returns an array with the variables at this 
	 * line.
	 * @param line
	 * @param currBlock
	 * @return ArrayList of variables
	 */
	public ArrayList<Variable> prepareVariable(String line, Block currBlock) {
		Pattern p = Pattern.compile(RegexMenu.VARIABLE_LINE.getValue());
		Matcher m = p.matcher(line);
		String variableName;
		String value = null;
		ArrayList<Variable> arrayVariables = new ArrayList<Variable>();
		if (m.find()){
			if (ReservedWords.FINAL.getValue().equals(m.group(1))) {
				 isFinal = true;
			}
			variableType = m.group(2);
			String[] lineParts = m.group(3).split(RegexMenu.COMMA.getValue());
			for (String part: lineParts){
				part = part.replaceAll(RegexMenu.EQUAL_WITH_SPACE.getValue(), RegexMenu.EQUAL.getValue());
				String[] parts = part.split(RegexMenu.EQUAL.getValue());
				variableName = parts[0];
				if (parts.length == 2){
					value = parts[1];
					value = value.replace(RegexMenu.SPACE.getValue(), RegexMenu.EMPTY_LINE.getValue());
					if (check_value(variableType, value, currBlock)){
						isDefined = true;
					} else {
						return null;
					}
				}
				Variable variable = 
						FactoryVariable.createVariable(variableName, variableType, isFinal, isDefined);
				arrayVariables.add(variable);
			}
		}
		return arrayVariables;
	}
	
	/**
	 * check value, if was initialize, then the type need to fit to the type of the variable.
	 * @param type
	 * @param value
	 * @param currBlock
	 * @return true if type fits, else false
	 */
	private boolean check_value(String type, String value, Block currBlock){
		if (!Type.contains(type)){
			return false;
		}
		Type valueType = typeChecker.whichType(value);
		if (valueType != null) {
			String valueString = valueType.getValue();
			return checkIfTypeFits(type, valueString);
		} else {
			Variable currVariable = compareVariables(value, currBlock);
			if(currVariable != null){
				return checkIfTypeFits(type, currVariable.getVariableType());
			}
		}
		return false;
	}
	
	/**
	 * check if the variable can be condition - boolean, int or double.
	 * @param name
	 * @param currBlock
	 * @return true if can be condition, else false
	 */
	public boolean checkIfCanBeCondition(String name, Block currBlock){
		Variable currVariable = compareVariables(name, currBlock);
		if ( currVariable != null){
				if (ifTypeCondition(currVariable)){
							return true;
				}
		}
		return false;	
	}
	
	/**
	 * checks if the variable is already exists at the previews blocks.
	 * @param name
	 * @param currBlock
	 * @return curr_variable
	 */
	public Variable compareVariables(String name, Block currBlock){
		while(currBlock != null){
			LinkedList<Variable> currVariableList = currBlock.getVariableList();
			for (Variable currVariable: currVariableList ){
				if(currVariable.getVariableName().equals(name)){
					if(currVariable.isDefined()){
						return currVariable;
					}
				}
			}
			currBlock = currBlock.getPrevScope();
		}
		return null;
	}
	
	/**
	 * checks if the variable is already exist at the current block, if it is, can't declare twice at the 
	 * sane variable, or can't be 2 variables with the same name.
	 * @param name
	 * @param currBlock
	 * @return
	 */
	public boolean compareVariablesInBlock(String name, Block currBlock){
		LinkedList<Variable> currVariableList = currBlock.getVariableList();
		for (Variable currVariable: currVariableList ){
			if(currVariable.getVariableName().equals(name)){
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * check if the value is from the right type.
	 * @param type
	 * @param valueString
	 * @return true if the type fits, else false
	 */
	private boolean checkIfTypeFits(String type, String valueString){
		if (valueString.equals(type)){
			return true;
		} else {
			if (Type.STRING.getValue().equals(type) && Type.CHAR.getValue().equals(valueString)) {
				return true;
			} else if (Type.DOUBLE.getValue().equals(type) && Type.INT.getValue().equals(valueString)) {
				return true;
			} else if (Type.BOOLEAN.getValue().equals(type) && ((Type.INT.getValue().equals(valueString)) || 
					Type.DOUBLE.getValue().equals(valueString))){
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Checks if the type can be at condition -int, double or boolean.
	 * @param curr_variable
	 * @return true if fits, else false
	 */
	private boolean ifTypeCondition(Variable curr_variable){
		String variableType = curr_variable.getVariableType();
		if (variableType.equals(Type.DOUBLE.getValue()) || variableType.equals(Type.INT.getValue()) 
				|| variableType.equals(Type.BOOLEAN.getValue())) {
			return true;
		} else { 
			return false;
		}
	}
	
	/**
	 * if we try to make initialize of variable to variable, we want to check before if the variable
	 * is already exist and defined.  
	 * @param name
	 * @return type of the variable or null
	 */
	public String lookForVariable(String name, Block currBlock){
		Block blockToCheck = currBlock;
		while (blockToCheck != null){ // looks at all the previews blocks  
			for (Variable curr : blockToCheck.getVariableList()) {
				if (curr.getVariableName().equals(name)) {
					if (!curr.isFinal()){ // if the variable is final, cannot be changed
						return curr.getVariableType();
					}
				}
			}
			blockToCheck = blockToCheck.getPrevScope();
		}

		return null;
	}

	/**
	 * gets if has the type of the first variable, if not that means that it was declared before, 
	 * and will look for the type, and gets the entire line, well check the type of the value, and compare 
	 * between 2 types that the assignment is legal. 
	 * @param type1_string
	 * @param line
	 * @return true if it's legal, else false.
	 */
	public boolean variableAssigned(String type1_string, String line, Block currBlock) {
		TypeChecker typeChecker = new TypeChecker();
		VariableChecker variableChecker = new VariableChecker();
		line = line.replaceAll(RegexMenu.EQUAL_WITH_SPACE.getValue(), RegexMenu.EQUAL.getValue());
		String[] variable_assinged = line.split( RegexMenu.EQUAL.getValue() + 
				RegexMenu.SPACE_OR_END_LINE.getValue());
		if(type1_string == null){
			type1_string = variableChecker.lookForVariable(variable_assinged[0], currBlock);
			if (type1_string == null){
				return false;
			}
		}
		Type type2 = typeChecker.whichType(variable_assinged[1]);
		if (type2 != null){
			if (type2.getValue().equals(type1_string)) {
				return true;
			}
		} else {
			String type2_string = variableChecker.lookForVariable(variable_assinged[1], currBlock);
			if (type2_string != null && type2_string.equals(type1_string)) {
				return true;
			}
		}
		return false;
	}

}
