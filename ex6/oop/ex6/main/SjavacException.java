package oop.ex6.main;

/**
 * abstract class of Sjava exception that extends an Exception class. Has one abstract method that prints the
 * msg.
 */
public abstract class SjavacException extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
	 * abstract method that prints for every exception why it happen.
	 */
	public abstract void printMsg();

}
