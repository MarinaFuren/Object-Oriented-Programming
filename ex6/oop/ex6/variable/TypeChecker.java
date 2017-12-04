package oop.ex6.variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.RegexMenu;

/**
 * This class checks the type.
 */
public class TypeChecker {
	private final static Pattern INT_P = Pattern.compile(RegexMenu.INT.getValue());
	private final static Pattern DOUBLE_P = Pattern.compile(RegexMenu.DOUBLE.getValue());
	private final static Pattern STRING_P = Pattern.compile(RegexMenu.STRING.getValue());
	private final static Pattern CHAR_P = Pattern.compile(RegexMenu.CHAR.getValue());
	private final static Pattern BOOLEAN_P = Pattern.compile(RegexMenu.BOOLEAN.getValue());

	/**
	 * This method receives name and can check which type is it.
	 * @param name
	 * @return Type or null
	 */
	public Type whichType(String name) {
		Matcher m = INT_P.matcher(name);
		if (m.matches()){
			return Type.INT;
		} else if (m.usePattern(DOUBLE_P) != null && m.matches()) {
			return Type.DOUBLE;
		} else if (m.usePattern(STRING_P) != null && m.matches()) {
			return Type.STRING;
		} else if (m.usePattern(CHAR_P) != null && m.matches()) {
			return Type.CHAR;
		} else if (m.usePattern(BOOLEAN_P) != null && m.matches()) {
			return Type.BOOLEAN;
		}
		return null;
	}
}
