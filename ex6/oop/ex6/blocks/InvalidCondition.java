package oop.ex6.blocks;

import oop.ex6.main.SyntaxException;

/**
 * class of exception. if the condition isn't valid.
 */
public class InvalidCondition extends SyntaxException {
	private static final String ERR_MSG = "condition doesn't resolved as boolean";
	private static final long serialVersionUID = 1L;

	@Override
	public void printMsg() {
		System.err.println(ERR_MSG);
		
	}

}
