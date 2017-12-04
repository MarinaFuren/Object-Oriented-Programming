package oop.ex6.variable;

import oop.ex6.main.SjavacException;

/**
 *This class is an exception of variables.
 */
public class InvalidVariable extends SjavacException {
	private static final String ERR_MSG = "variable can not be created";
	private static final long serialVersionUID = 1L;

	@Override
	public void printMsg() {
		System.err.println(ERR_MSG);
		
	}

}
