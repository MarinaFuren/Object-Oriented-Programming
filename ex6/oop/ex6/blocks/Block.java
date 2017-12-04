package oop.ex6.blocks;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.RegexMenu;
import oop.ex6.variable.Variable;

/**
 * This class is a block class, has all the information about the current block- if it's method or not,
 * who is his previews block, and the list of variables that the block has.
 */
public class Block {
	private LinkedList<Variable> variableList;
	private Block prev_scope;
	private boolean isMethod;
	private static final int VARIABLE_NAME = 1;
	private static final int VARIABLE_TYPE = 0;
	private static final int INITIALIZE_LENGTH = 3;
	/**
	 * Default constructor, initialize the list of variables.
	 */
	public Block(){
		variableList = new LinkedList<Variable>();
	}
	
	/**
	 * constructor
	 * @param line - the line we would like to to build a new block from
	 */
	public Block(String line){
		variableList = new LinkedList<Variable>();
		Pattern p2 = Pattern.compile(RegexMenu.BETWEEN_BRACKETS.getValue());
		Matcher m2 = p2.matcher(line);
		if (m2.find()){// checks if we have arguments in line
			line = m2.group(1);
		}
		if (!line.equals(RegexMenu.EMPTY_LINE.getValue())) {
			line = line.replaceAll(RegexMenu.COMMA_WITH_SPACE.getValue(), RegexMenu.COMMA.getValue());
			String[] lineParts = line.split(RegexMenu.COMMA.getValue());
			for (int i=0; i<lineParts.length; i++) {
				String[] typeVariable = lineParts[i].split(RegexMenu.SPACE.getValue());
				Variable curr_variable;
				// if the variable is initialized
				if(typeVariable.length == INITIALIZE_LENGTH) {
					curr_variable = new Variable(typeVariable[VARIABLE_NAME], typeVariable[VARIABLE_TYPE],
							true, true);
				} else {
					curr_variable = new Variable(typeVariable[VARIABLE_NAME], typeVariable[VARIABLE_TYPE], false, true);
				}
				variableList.add(curr_variable);
			}
		}

	}
	
	/**
	 * getter
	 * @return the block variable list
	 */
	public LinkedList<Variable> getVariableList(){
		return variableList;
	}
	
	/**
	 * add the given variable to the blocks variable list 
	 * @param variableToAdd
	 */
	public void addVariable(Variable variableToAdd){
		variableList.add(variableToAdd);
	}
	
	/**
	 * @return the previous scope to the current block 
	 */
	public Block getPrevScope() {
		return prev_scope;
	}

	/**
	 * sets previous scope to the current block
	 * @param prev_scope the new previous scope
	 */
	public void setPrevScope(Block prev_scope){
		this.prev_scope = prev_scope;
	}
	/**
	 * returns if the current block is method
	 * @return true if the current block is method, false otherwise
	 */
	public boolean getIsMethod(){
		return isMethod;
	}
	/**
	 * sets the current block as method
	 * @param bool
	 */
	public void setIsMethod(boolean bool){
		this.isMethod = bool;
	}





}
