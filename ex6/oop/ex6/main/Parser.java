package oop.ex6.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.blocks.*;
import oop.ex6.variable.*;

/**
 * The class of the parser, that reads the code, analyzes the text and send the lines to different checks,
 * according to which type of line it is. The parser is singleton, and the program has only one instance of 
 * it.
 */
public class Parser {
	
	private String newLine;
	private String[] partsLine;
	private GlobalBlock globalBlock;
	private Block currBlock;
	private boolean wasMarked = false;
	private static Parser Parser = null;
	private static int counter = 0;
	private static final int WITHOUT_VOID = 5;
	private static final int BALANCED_BREACKETS = 0;
	private static final int LINE_TYPE = 0;
	private static final int TYPE_INDEX = 1;
	
	/*
	 * Private constructor suppresses generation of a (public) default constructor.
	 */
	private Parser() {
	}
	 
	/**
	 * getter of the parser, make sure that will be only one instance of the parser.
	 * @return Parser
	 */
	public static Parser getParser() {
	    if (Parser == null) {
	    	Parser = new Parser();
	      }
	    return Parser;
	}

	/**
	 * the first run is only saves the variables that are global, and saves the name of the methods at the 
	 * list.
	 * @param codeFile
	 * @return true if the code is valid, else throws exception
	 * @throws IOException
	 * @throws SyntaxException
	 * @throws InvalidVariable
	 * @throws InvalidMethod
	 */
	public boolean firstRun(File codeFile) throws IOException, SyntaxException, InvalidVariable, InvalidMethod {
		FileReader fileReader = new FileReader(codeFile);
		BufferedReader br = new BufferedReader(fileReader);
		Methods currMethods = new Methods();
		VariableChecker variableChecker = new VariableChecker();
		br.mark(8192);
		counter = 0;
		this.globalBlock = new GlobalBlock(); 
		this.currBlock = globalBlock;
		while ((newLine = br.readLine()) != null){
			if (wasMarked) {
				br.reset();
				newLine = br.readLine();
				wasMarked = false;
			}
			fixerLines(br);
			if (newLine != null && EndLineChecker.check_end(newLine)) {
				// declaration of method
				if (partsLine[LINE_TYPE].equals(ReservedWords.VOID.getValue()) && 
						counter == BALANCED_BREACKETS){
					counter ++;
					if (!currMethods.isLineValid(newLine.substring(WITHOUT_VOID))){
						currBlock = globalBlock;
						throw new InvalidMethod();
					} else {
						Methods methodToAdd = 
						new Methods(newLine.substring(WITHOUT_VOID).split(RegexMenu.METHOD_NAME.getValue())); 
						globalBlock.addMethod(methodToAdd);
					}
					if (searchCloseBlock(br) == false){
						currBlock = globalBlock;
						return false;
					} else {
						counter = BALANCED_BREACKETS;
					}
				// declaration of variable
				} else if ((Type.contains(partsLine[LINE_TYPE]))||
						((ReservedWords.FINAL.getValue()).equals(partsLine[LINE_TYPE])
								&& Type.contains(partsLine[TYPE_INDEX])) && counter == BALANCED_BREACKETS){
						if(!FactoryVariable.variableCreator(newLine, currBlock)){ 
							currBlock = globalBlock;
							throw new InvalidVariable();
							}
				// line of closing block
				} else if (partsLine[LINE_TYPE].equals(ReservedWords.CLOSER)){
					counter --;
				// line of initialize variable
				} else if (newLine.contains(RegexMenu.EQUAL.getValue())) { 
					return variableChecker.variableAssigned(null, newLine, currBlock);
				// unknown action of Sjava
				} else {
					currBlock = globalBlock;
					throw new UnknownAction();
				}
			// the file ended after skipping a line
			} else if (newLine == null){
				return endFirstRun(br, fileReader, codeFile);
			// the line ended not in a proper way
			} else {
				currBlock = globalBlock;
				throw new WrongSuffix();
			}
		// some block didn't closed properly
		}
		if (counter != BALANCED_BREACKETS){
			currBlock = globalBlock;
			throw new BracketException();
		}
		// the file ended
		if (newLine == null){
			return endFirstRun(br, fileReader, codeFile);
		}
		return false;
	}

	/**
	 * Second run is second read, that looks foe definition of method, and if it's exist gets into, create 
	 * a new block of a method, and call for a helper method, that is recursion function.
	 * @param codeFile
	 * @return true if the second run correct; else throws exception.
	 * @throws IOException
	 * @throws SyntaxException
	 * @throws InvalidVariable
	 */
	public boolean secondRun(File codeFile) throws IOException, SyntaxException, InvalidVariable { 
		FileReader fileReader = new FileReader(codeFile);
		BufferedReader br = new BufferedReader(fileReader);	
		Methods curr_method = new Methods();
		
		while ((newLine = br.readLine()) != null){
			fixerLines(br);
			if (newLine != null && EndLineChecker.check_end(newLine)) {
				if (partsLine[LINE_TYPE].equals(ReservedWords.VOID.getValue())) {
					openMethodBlock();
					helperMethodParser(br);
				} else if (partsLine[LINE_TYPE].equals(ReservedWords.RETURN) && counter > BALANCED_BREACKETS){
					if (!currBlock.getIsMethod()){
						throw new ReturnException();
					}
					if (curr_method.isValidEnd(newLine, br)){
						counter--;
						return true;
						}
				} else if (partsLine[LINE_TYPE].equals(ReservedWords.CLOSER) && counter > BALANCED_BREACKETS){
					closeBlock();
				}
			}
		}
		return true;
	}
	
	/*
	 * gets the br, and responsible on getting into the block, the case of if or while, if need to open new 
	 * block call to herself in recursion way. And reads all the current block.
	 * throws IOException SyntaxException InvalidVariable.
	 */
	private boolean helperMethodParser(BufferedReader br) throws IOException, SyntaxException, InvalidVariable {
		Methods currMethod = new Methods();
		ConditionChecker currCondition = new ConditionChecker();
		VariableChecker variableChecker = new VariableChecker();
		while ((newLine = br.readLine()) != null){
			fixerLines(br);
				if (EndLineChecker.check_end(newLine)) {
					if (((partsLine[LINE_TYPE]).startsWith(ReservedWords.IF.getValue())||
							(partsLine[0]).startsWith(ReservedWords.WHILE.getValue()))) {
						// Opens anew block and gets into the block in recursion.
						counter ++;
						Block block = new Block();
						block.setIsMethod(false);
						if (currCondition.isLineValid(newLine)){
							if(!currCondition.checkCondition(newLine, currBlock)){
								throw new InvalidCondition();
							}
							block.setPrevScope(currBlock);
							currBlock = block;
							if(!helperMethodParser(br)){
								return false;
							}
						} else {
							throw new InvalidCondition();
						}
					} else if (partsLine[LINE_TYPE].equals(ReservedWords.CLOSER.getValue()) && 
							counter > BALANCED_BREACKETS) {
						return closeBlock();
					} else if (Type.contains(partsLine[LINE_TYPE]) || 
							partsLine[LINE_TYPE].equals(ReservedWords.FINAL.getValue())) {
						if (!FactoryVariable.variableCreator(newLine, currBlock)){
							throw new InvalidVariable();
						}
					} else if (newLine.contains(RegexMenu.EQUAL.getValue())) { // variable assigned
						if (!variableChecker.variableAssigned(null, newLine, currBlock)){
							throw new InvalidVariable();
						}
					} else if (partsLine[LINE_TYPE].equals(ReservedWords.RETURN.getValue()) && 
							counter > BALANCED_BREACKETS) {
						if (currBlock.getIsMethod()){
							return closeMethodBlock(currMethod, br);
						}
					} else { //call to another method
						return checkCallMathod();
					}
				} else {
					throw new WrongSuffix();
				}
		}
		return true;
	}
	

	/*
	 * At the first run the code didn't reads what is in the block, so it looks for the right closer
	 * of the block and then keeps reading from it, at the global block.
	 */
	private boolean searchCloseBlock(BufferedReader br) throws IOException {
		Pattern p = Pattern.compile(RegexMenu.OPENER.getValue());
		Pattern p2 = Pattern.compile(RegexMenu.CLOSER.getValue());
		while ((newLine = br.readLine()) != null && (counter != BALANCED_BREACKETS)){
			fixerLines(br);
			Matcher m = p.matcher(newLine);
			if (m.find()){
				counter ++;
			} else if ((m.usePattern(p2)).find()){
				counter --;
			}
			br.mark(8192);
		}
		if (counter == BALANCED_BREACKETS){
			wasMarked = true;
			return true;
		}
		return false;
	}
	
	/*
	 * decides if need to skip lines, returns true is need to skip, else false.
	 */
	private boolean skipper(String line){
		Pattern p = Pattern.compile(RegexMenu.SKIP_LINE.getValue());
		Matcher m = p.matcher(line);
		if (m.find()){
			return true;
		}
		return false;
	}
	
	/*
	 * skips blanket lines at the code.
	 */
	private void skipLine(BufferedReader br) throws IOException {
		newLine = br.readLine();
		if (newLine == null){
			return;
		} else {
			partsLine = newLine.split(RegexMenu.SPACE.getValue());
		}
	}
	

	
	/*
	 * fixes the line, skips blanket lines.
	 */
	private void fixerLines(BufferedReader br) throws IOException{
		if(this.newLine != null){
			this.newLine = LineFixer.lineFix(this.newLine);
			partsLine = this.newLine.split(RegexMenu.SPACE.getValue());
			if (skipper(this.newLine)) {
				skipLine(br);
				fixerLines(br);
			}
		}
	}
	
	/*
	 * closes the block, updates the current block and the counter of brackets.
	 */
	private boolean closeBlock() {
		currBlock = currBlock.getPrevScope(); 
		counter --;
	 	return true;
	}
	
	/*
	 * close a block of a method.
	 * throws WrongSuffix, IOException
	 */
	private boolean closeMethodBlock(Methods currMethod, BufferedReader br) throws WrongSuffix, IOException{
		if (currMethod.isValidEnd(newLine, br)){
			return closeBlock();
		} else {
			throw new WrongSuffix();
		}
	}
	
	private void openMethodBlock(){
		counter ++;
		Block block = new Block(newLine);
		block.setIsMethod(true);
		block.setPrevScope(currBlock);
		currBlock = block;
	}
	
	/*
	 * checks if it's a call foe another method, if it was defined at the global block.
	 */
	private boolean checkCallMathod() throws UnknownAction{
		Pattern p = Pattern.compile(RegexMenu.CALL_METHOD.getValue());
		Matcher m = p.matcher(newLine);
		if (m.find()) {
			Methods newMethod = new Methods(newLine.split(RegexMenu.METHOD_NAME.getValue()));
			LinkedList<Methods> methodsList = globalBlock.getMethodsList();
			for(Methods check_method: methodsList){
				if (check_method.getMethodName().equals(newMethod.getMethodName())){
					return true;
				}
			}
		}
		throw new UnknownAction();
	}
	
	/*
	 * ends the first reading, and starts second run on the code.
	 */
	private boolean endFirstRun(BufferedReader br, FileReader fileReader,File codeFile) 
			throws IOException, SyntaxException, InvalidVariable{ 
		br.close();
		fileReader.close();
		return secondRun(codeFile);
	}

	
}
