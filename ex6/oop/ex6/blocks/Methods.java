package oop.ex6.blocks;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

import oop.ex6.main.*;
import oop.ex6.variable.*;

/**
 * This Methods class, has 2 constructors and implements the interface of Compileable so has a syntax check,
 * in addition has some checks for the methods- if the name is valid, if ends correctly etc.
 */
public class Methods implements Compileable {
	private String[] lineParts;
	private String[] typeVariable;
	private String methodName;
	private static final int FINAL = 1;
	private static final int VALID_LENGTH = 2;
	private static final int TYPE_PLACE = 0;
	private static final int NAME_PLACE = 1;
	private static final int METHOD_NAME = 0;
	private static final int METHOD_NAME_FIRST_INDEX = 0;
	private static final int LAST_INDEX = 1;
	
	/**
	 * Default constructor.
	 */
	public Methods() {
	}
	
	/**
	 * constructor
	 * @param splittedLine the line we would like to create method from
	 */
	public Methods(String[] splittedLine) {
		Pattern p2 = Pattern.compile(RegexMenu.OPEN_BRACKET_SPACE_AT_START.getValue());
		Matcher m2 = p2.matcher(splittedLine[METHOD_NAME]);
		// find the inside of the bracket
		if (m2.find()){
			int index = m2.end();
			setMethodName(splittedLine[METHOD_NAME].substring(METHOD_NAME_FIRST_INDEX, index-LAST_INDEX));
		} else {
			setMethodName(splittedLine[METHOD_NAME]);
		}
	}

	/**
	 * @return the current method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * this function change the method name
	 * @param methodName the new method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
	@Override
	public boolean isLineValid(String line) {
		Variable variable = new Variable();
		if (isValidNameMethod(line)){
			line = line.replaceAll(RegexMenu.OPEN_BRACKET_WITH_SPACE.getValue(), 
					RegexMenu.OPEN_BRACKET.getValue());
			line = line.replaceAll(RegexMenu.CLOSE_BRACKET_WITH_SPACE.getValue(),
					RegexMenu.CLOSE_BRACKET.getValue());
			Pattern p2 = Pattern.compile(RegexMenu.BETWEEN_BRACKETS.getValue());
			Matcher m2 = p2.matcher(line);
			
			if (m2.find()){
				line = m2.group(1);
				if (line.equals(RegexMenu.EMPTY_LINE.getValue())){
					return true;
				}
				int index = TYPE_PLACE;
				line = line.replaceAll(RegexMenu.COMMA_WITH_SPACE.getValue(), RegexMenu.COMMA.getValue());
				lineParts = line.split(RegexMenu.COMMA.getValue());
				// creates array of argument for the function
				ArrayList<String> arrayArgs = new ArrayList<String>();
				for (int i=0; i<lineParts.length; i++) {
					typeVariable = lineParts[i].split(RegexMenu.SPACE.getValue());
					// if we have final
					if (ReservedWords.FINAL.getValue().equals(typeVariable[TYPE_PLACE])) {
						index = FINAL;
					}
					// if one of the argument is invalid
					if (typeVariable.length != VALID_LENGTH+index || !Type.contains(typeVariable
							[TYPE_PLACE+index]) 
							|| !variable.isValidName(typeVariable[NAME_PLACE+index])){
						return false;	
					// if one of the given arguments is already exists
					} else if (!arrayArgs.contains(typeVariable[NAME_PLACE+ index])){
						arrayArgs.add(typeVariable[NAME_PLACE+index]);
					} else {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * check if the end of the line is valid
	 * @param line the line we would like to check
	 * @param br
	 * @return if the end of the line is valid
	 * @throws IOException
	 */
	public boolean isValidEnd(String line, BufferedReader br) throws IOException {
		Pattern p3 = Pattern.compile(RegexMenu.VALID_END.getValue());
		Matcher m3 = p3.matcher(line);
		if (m3.find()) {
			Pattern p4 = Pattern.compile(RegexMenu.CLOSER_WITH_SPACE.getValue());
			String currLine = br.readLine();
			currLine = LineFixer.lineFix(currLine);
			Matcher m4 = p4.matcher(currLine);
			if (m4.find()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if the method has a valid name
	 * @param line the line with the name
	 * @return if the name is valid or not
	 */
	private boolean isValidNameMethod(String line){
		Pattern p = Pattern.compile(RegexMenu.VALID_METHOD_NAME.getValue());
		Matcher m = p.matcher(line);
		if (m.find()) {
			return true;
		}
		return false;
	}



		
}
