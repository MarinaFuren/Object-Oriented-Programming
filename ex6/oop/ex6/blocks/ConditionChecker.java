package oop.ex6.blocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.Compileable;
import oop.ex6.main.RegexMenu;
import oop.ex6.variable.*;

/**
 * This class is a condition checker, implements the interface of Compileable, has the method that checks the 
 * syntax of the line, and has the logic check for the condition if it's boolean.
 */
public class ConditionChecker implements Compileable {
	private static final int LAST_INDEX = 1;

	
	@Override
	public boolean isLineValid(String line){
		Pattern p = Pattern.compile(RegexMenu.IF_WHILE.getValue());
		Matcher m = p.matcher(line);
		int count = 0;
		int curr_index = 0;
		while (m.find()) {
		    count++;
			curr_index = m.end()-LAST_INDEX;
		}
		line = line.substring(curr_index);
		if (count != 1){
			return false;
		} else {
			curr_index = 0;
			// if we found syntax that are not valid for condition
			p = Pattern.compile(RegexMenu.UNVALID_SYNTAX.getValue());
			m = p.matcher(line);
			if (m.find()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * this function checks if the current line can be a valid condition
	 * @param line the line we would like to check 
	 * @param currBlock the current block the condition will be
	 * @return true if the the given line can be condition, false otherwise
	 */
	public boolean checkCondition(String line, Block currBlock){
		VariableChecker check_variable = new VariableChecker();
		TypeChecker check_type = new TypeChecker();
		
		Pattern p = Pattern.compile(RegexMenu.IF_WHILE.getValue());
		Matcher m = p.matcher(line);
		int curr_index = 0;
		while (m.find()) {
			curr_index = m.end()-LAST_INDEX;
		}
		line = line.substring(curr_index);	
		// check the inside of of the brackets
		p = Pattern.compile(RegexMenu.INSIDE_BRACKETS.getValue());
		m = p.matcher(line);
		if (m.find()){
			line = m.group(1);
		}
		String[] lineParts = line.split(RegexMenu.OR_AND.getValue());
		for (int i = 0; i< lineParts.length; i++){
			lineParts[i] = lineParts[i].trim();
			// checks for each variable if he can be a condition
			Type conditionType = check_type.whichType(lineParts[i]);
			if (!(conditionType == Type.BOOLEAN || conditionType == Type.INT ||conditionType == Type.DOUBLE)){
				if (!check_variable.checkIfCanBeCondition(lineParts[i], currBlock)){
					return false;
				}
			}
			
		}
		return true;
	}
}
