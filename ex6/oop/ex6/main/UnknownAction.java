package oop.ex6.main;

/**
 * UnknownAction is sort of SyntaxException.
 */
public class UnknownAction extends SyntaxException {
	private static final String ERR_MSG = "Unknown given line";
	private static final long serialVersionUID = 1L;

	@Override
	public void printMsg() {
		System.err.println(ERR_MSG);
	}


}
