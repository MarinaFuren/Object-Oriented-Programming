package oop.ex6.main;

import java.util.regex.*;


/**
 * A regular check for every line, if it's ends correctly, with the right mark.
 */
public class EndLineChecker{
	private static final int ONE_CLOSE_MARK = 1;

	/**
	 * this function check if the end of the given line is valid or not
	 * @param line the line we would like to check
	 * @return true if the end is valid, false otherwise
	 */
	public static boolean check_end(String line){
		Pattern p_count = Pattern.compile(RegexMenu.CLOSE_MARK.getValue());
		Matcher m_count = p_count.matcher(line);
		int count = 0;
		// counts the number of close mark in the line
		while (m_count.find())
		    count++;
		// if we have only one close, as needed
		if (count == ONE_CLOSE_MARK) {
			// checks if nothing appears after
			Pattern p = Pattern.compile(RegexMenu.NOTHING_AFTER_CLOSE_MARK.getValue());
			Matcher m = p.matcher(line); 
			return m.find();
		}
		// if we have more or less than one close mark
		return false;
		

		
	}
}
