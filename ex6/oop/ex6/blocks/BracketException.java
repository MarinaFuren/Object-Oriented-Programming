package oop.ex6.blocks;


/**
 * Bracket exception it's a syntax exception, happen when the block didn't close at the proper way.
 */
public class BracketException extends CloseMethodException{
	
	private static final String ERR_MSG = "block hasn't been closed as needed";
	private static final long serialVersionUID = 1L;

	@Override
	public void printMsg() {
		System.err.println(ERR_MSG);
		
	}

}
