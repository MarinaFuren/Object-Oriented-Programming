package oop.ex6.main;

/**
 * Class that responsible to get a line and to fix it, skip spaces etc.
 */
public class LineFixer {

	/**
	 * this function fix the given line to standard line
	 * @param line we would like to fix
	 * @return the fixed line
	 */
	public static String lineFix(String line) {
		line = line.trim();
		line = line.replaceAll(RegexMenu.EQUAL.getValue(), RegexMenu.EQUAL_WITH_SPACE.getValue());
		line = line.replaceAll(RegexMenu.MULTI_SPACE.getValue(), RegexMenu.SPACE.getValue());
		return line;
	}
	
	
}
