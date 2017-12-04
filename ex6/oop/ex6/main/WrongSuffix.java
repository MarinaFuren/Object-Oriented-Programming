package oop.ex6.main;

/**
 * WrongSuffix is sort of SyntaxException.
 */
public class WrongSuffix extends SyntaxException {
	private static final String ERR_MSG = "wrong end of line";
	private static final long serialVersionUID = 1L;

	@Override
	public void printMsg() {
		System.err.println(ERR_MSG);
		
	}


}
