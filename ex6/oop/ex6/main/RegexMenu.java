package oop.ex6.main;

/**
 * enum of the regular expression that we use at the program.
 */
public enum RegexMenu {	
	CLOSE_MARK ("[;{}]"),
	NOTHING_AFTER_CLOSE_MARK  ("[;{}]+(\\s)*(?!.)"),
	EQUAL_WITH_SPACE (" = "),
	EQUAL ("="),
	SPACE (" "),
	MULTI_SPACE ("[\\s]+"),
	METHOD_NAME (",|\\s"),
	SKIP_LINE ("(^\\/\\/)|(^\\n)|(^\\s*$)"),
	CALL_METHOD ("([A-Za-z]+[\\w]*)\\s?\\((.*?)\\)(\\s*)\\;{1}"),
	OPENER ("\\{"),
	CLOSER ("\\}"),
	BETWEEN_BRACKETS ("\\((.*?)\\)(\\s)?(\\{)"),
	COMMA_WITH_SPACE (", "),
	COMMA (","),
	EMPTY_LINE (""),
	OR_AND ("\\&\\&|\\|\\|"),
	IF_WHILE ("\\b^(if|while)+[\\s]?\\({1}"),
	INSIDE_BRACKETS ("\\({1}(.*)\\){1}\\s?\\{{1}"),
	UNVALID_SYNTAX ("\"|\'"),
	OPEN_BRACKET_SPACE_AT_START  ("\\s?\\("),
	VALID_METHOD_NAME  ("^[A-Za-z]+[\\w]*\\s?(\\({1}.*\\){1})\\s?\\{{1}"),
	VALID_END ("^(return;)$"),
	OPEN_BRACKET_WITH_SPACE ("\\(\\s"),
	CLOSE_BRACKET_WITH_SPACE ("\\s\\)"),
	CLOSE_BRACKET (")"),
	OPEN_BRACKET ("("),
	CLOSER_WITH_SPACE ("^}\\s?"),
	VARIABLE_LINE ("(final)?\\s?(\\w+)\\s(.*);{1}"), 
	SPACE_OR_END_LINE ("|;|\\s"), 
	VALID_NAME ("(\\s)?[A-Za-z]+[\\w]*|[_]+[\\w]+"), 
	WITHOUT_SUFFIX ("(.*?)(\\;)"), 
	DIGIT ("\\d+"), 
	INT ("^\\-?"+ DIGIT.getValue() +"+$"), 
	DOUBLE ("^\\s*\\-?\\s*"+ DIGIT.getValue() +"\\.{1}" + DIGIT.getValue() + "\\s*$"), 
	STRING ("^\\\"{1}.+\\\"{1}$"), 
	CHAR ("^\\\'{1}.{1}\\\'{1}$"), 
	BOOLEAN ("true|false");
	
	private final String value;
	
	/* 
	 * constructor
	 * param value - the value using string of the type
	 */
	private RegexMenu(String value){
		this.value = value;
	}
	
	/**
	 * @return the regular expression 
	 */
	public String getValue(){
		return value;
	}
}
