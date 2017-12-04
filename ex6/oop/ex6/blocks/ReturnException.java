package oop.ex6.blocks;


/**
 * Return exception, if the return statement not in correct place.
 */
public class ReturnException extends CloseMethodException {
	
	private static final String ERR_MSG = "return can appear only inside method";
	private static final long serialVersionUID = 1L;

	@Override
	public void printMsg() {
		System.err.println(ERR_MSG);
		
	}


}
