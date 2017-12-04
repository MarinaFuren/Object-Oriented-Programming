package oop.ex6.variable;

import java.util.ArrayList;

import oop.ex6.blocks.Block;
import oop.ex6.variable.Variable;;

/**
 * This class creates new variables.
 */
public class FactoryVariable {
	
	/**
	 * This method creates new variable.
	 * @param name
	 * @param type
	 * @param isFinal
	 * @param isDefined
	 * @return Variable 
	 */
	public static Variable createVariable(String name, String type, boolean isFinal, boolean isDefined) {
		return new Variable(name, type, isFinal, isDefined);
	}
	
	/**
	 * Tries to create a variable, if has success returns true, otherwise return false.
	 * @param line
	 * @return
	 */
	public static boolean variableCreator(String line, Block currBlock){
		VariableChecker variableChecker = new VariableChecker();
		Variable currVariable = new Variable();
		if (currVariable.isLineValid(line)){ // check the syntax of the line
			ArrayList<Variable> arrayVariables = variableChecker.prepareVariable(line, currBlock);
			if (arrayVariables == null){
				return false;
			}
			for (int i=0;i<arrayVariables.size() ;i++){ // tries to add the variables to the list of the block
				if (variableChecker.compareVariablesInBlock(arrayVariables.get(i).getVariableName(), 
						currBlock)) {
					return false;
				}
				if (variableChecker.compareVariables(arrayVariables.get(i).getVariableName(), 
						currBlock) != null){
					return false;
				} else {
					currBlock.addVariable(arrayVariables.get(i));
				}
			}
			return true;
		}
		return false;
	}
}
