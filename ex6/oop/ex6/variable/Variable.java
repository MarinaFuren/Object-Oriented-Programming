package oop.ex6.variable;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.Compileable;
import oop.ex6.main.RegexMenu;
import oop.ex6.main.ReservedWords;

/**
 * This variable class implements the interface Compileable and the method that checks the validity of the 
 * line., has the constructor that defines all the fields of variable, and the getters and the setters
 * of the fields.
 */
public class Variable implements Compileable {
	private String variableType;
	private String variableName;
	private String variableValue;
	private boolean isFinal;
	private boolean isDefined;
	private final static int VARIABLE_LEANGTH = 1;
	private final static int LINE_WITH_FINAL = 1;
	private final static int LINE_WITHOUT_FINAL = 0;
	private final static int INITIALIZE_VARIABLE_LEANGTH = 3;
	private final static int NAME_INDEX_INITIALIZE = 2;
	private final static int NAME_INDEX = 0;
	private final static int INITIALIZE_INDEX = 1;
	private final static int FIRST_INDEX_TYPE_OR_FINAL = 0;
	
	/**
	 * default constructor.
	 */
	public Variable(){
	}
	
	/**
	 * Constructor that gets all the definition of the fields, and sets them.
	 * @param variable
	 * @param type
	 * @param isFinal
	 * @param isDefined
	 */
	public Variable(String variable, String type, boolean isFinal, boolean isDefined) {
		setVariableName(variable);
		setVariableType(type);
		setFinal(isFinal);
		setDefined(isDefined);
	}
	
	/**
	 * getter of the variable value.
	 * @return variableValue
	 */
	public String getVariableValue() {
		return variableValue;
	}
	
	/**
	 * setter of the variable value.
	 * @param value
	 */
	public void setVariableValue(String value) {
		variableValue = value;
	}

	/**
	 * getter of the variable type.
	 * @return variable type
	 */
	public String getVariableType() {
		return variableType;
	}
	
	/**
	 * setter of the variable type.
	 * @param variableType
	 */
	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}
	
	/**
	 * getter of the variable name.
	 * @return variableName.
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * setter of the variable name.
	 * @param variableName
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	/**
	 * getter of the flag - if the variable is final.
	 * @return isFinal
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * setter of the flag - if the variable is final.
	 * @param isFinal
	 */
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	/**
	 * getter of the flag - if the variable is defined.
	 * @return isDefined.
	 */
	public boolean isDefined() {
		return isDefined;
	}

	/**
	 * setter of the flag - if the variable is defined.
	 * @param isDefined
	 */
	public void setDefined(boolean isDefined) {
		this.isDefined = isDefined;
	}
	
	/**
	 * Checks if the name is valid, (for example - can't starts with numbers).
	 * @param variableName
	 * @return true if name is valid, else false
	 */
	public boolean isValidName(String variableName) {
		Pattern p = Pattern.compile(RegexMenu.VALID_NAME.getValue());
		Matcher m = p.matcher(variableName);
		if (m.matches()){
			return true;
		}
		return false;
	}


	@Override
	public boolean isLineValid(String line) { 
		String[] lineParts;
		String[] variableArr;
		String[] variableArrNew = null;
		TypeChecker typeChecker = new TypeChecker();
		int lineWithFinal = LINE_WITHOUT_FINAL; 
		Pattern p = Pattern.compile(RegexMenu.WITHOUT_SUFFIX.getValue());
		Matcher m = p.matcher(line);
		
		if (m.find()) {
			line = m.group(1);
			lineParts = line.split(RegexMenu.COMMA.getValue());
			for (int i = 0; i < lineParts.length; i++) {
				variableArr = lineParts[i].split(RegexMenu.SPACE.getValue());
				if (i == FIRST_INDEX_TYPE_OR_FINAL) {
					lineWithFinal = LINE_WITHOUT_FINAL;
					if (ReservedWords.FINAL.getValue().equals(variableArr[FIRST_INDEX_TYPE_OR_FINAL])){
						lineWithFinal = LINE_WITH_FINAL;
					}
					variableArrNew = fixArray(variableArr, lineWithFinal);
				} else {
					variableArrNew = fixArray(variableArr, LINE_WITHOUT_FINAL);
				}
				if (variableArrNew.length != VARIABLE_LEANGTH && 
						variableArrNew.length != INITIALIZE_VARIABLE_LEANGTH) {
					return false;
				}
				if (!isValidName(variableArrNew[NAME_INDEX])) {
					return false;
				} 
				if (variableArrNew.length == INITIALIZE_VARIABLE_LEANGTH || lineWithFinal == LINE_WITH_FINAL){
					// if we have a line with final but without initialize
					if (variableArrNew.length != INITIALIZE_VARIABLE_LEANGTH){ 
						return false;
					}
					if (variableArrNew[INITIALIZE_INDEX].equals(RegexMenu.EQUAL.getValue())) {
						if (typeChecker.whichType(variableArrNew[NAME_INDEX_INITIALIZE]) == null){
							if (!isValidName(variableArrNew[NAME_INDEX_INITIALIZE])){
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * gets the array, goes over and remove the items to new array without the first reserved word.
	 */
	private String[] fixArray(String[] variableArr, int lineWithFinal){
		String[] variableArrNew = null;
		variableArrNew = new String[variableArr.length-VARIABLE_LEANGTH-lineWithFinal];
		for (int j = 0; j < variableArrNew.length; j++) {
			variableArrNew[j] = variableArr[j+1+lineWithFinal];
		}
		return variableArrNew;
	}
}
						

