package oop.ex6.blocks;

import oop.ex6.main.SjavacException;

/**
 * class of exception is the method isn't valid.
 */
public class InvalidMethod extends SjavacException{
	
	private static final String ERR_MSG = "method name is invalid";
	private static final long serialVersionUID = 1L;

	public void printMsg() {
		System.err.println(ERR_MSG);
		
	}

}
