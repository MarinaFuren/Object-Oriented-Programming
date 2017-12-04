package oop.ex6.main;

import java.io.File;
import java.io.IOException;
import oop.ex6.blocks.InvalidMethod;
import oop.ex6.main.BadInputError;
import oop.ex6.main.BadNumberArgs;
import oop.ex6.variable.InvalidVariable;


/**
 * The class that has the main, manager of the program.
 */
public class Sjavac {
	private static Parser parser = null;
	private static final int VALID_CODE = 0;
	private static final int FILE_PATH_LOCATION = 0;
	private static final int NOT_VALID_CODE = 1;
	private static final int ERRORS = 2;
	private static final int VALID_NUMBER_ARGS = 1;
	private static final String ERROR_MSG_INPUT = "ERROR - bad input error";
	private static final String ERROR_MSG_NUM_ARGS = "ERROR - bad number of arguments";
	private static final String NEW_LINE = "\n";
	
	
	/**
	 * The main, the manager of the program.
	 * @param args
	 * @throws InvalidVariable 
	 * @throws SyntaxException 
	 * @throws InvalidMethod 
	 * @throws IOException
	 */

	public static void main(String[] args) throws SyntaxException, InvalidVariable, InvalidMethod{
			try {
				if(args.length > VALID_NUMBER_ARGS) {
					throw new BadNumberArgs();
				}
				File codeFile = new File(args[FILE_PATH_LOCATION]);
				if (!codeFile.isFile()){
					throw new BadInputError();
				}	
				parser = Parser.getParser();
				parser.firstRun(codeFile);
				System.out.println(VALID_CODE);
			
			} catch (BadInputError error) {
				System.out.println(ERRORS);
				System.err.println(ERROR_MSG_INPUT);
				return;
			} catch (BadNumberArgs error) {
				System.out.println(ERRORS);
				System.err.println(ERROR_MSG_NUM_ARGS);
				return;
				
			} catch (IOException error) {
				System.out.println(ERRORS);
				return;
				
			} catch (SjavacException error) {
				System.out.println(NOT_VALID_CODE+NEW_LINE);
				error.printMsg();
			}
		}
}
