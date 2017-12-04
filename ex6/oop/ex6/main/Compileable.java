package oop.ex6.main;

/**
 * An interface that responsible of the check if the line is a valid line at Sjava.
 */
public interface Compileable {
	
	/**
	 * this function checks if the given line is valid
	 * @param line the line we would like to check
	 * @return true if the line if valid, false otherwise
	 */
	public boolean isLineValid(String line);

}
