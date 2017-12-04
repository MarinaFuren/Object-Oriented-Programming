package oop.ex6.main;

/**
 * enum of reserved words at Sjava.
 */
public enum ReservedWords {
	WHILE ("while"), IF("if"), VOID("void"), RETURN("return;"), FINAL("final"), CLOSER("}"), 
	OPENER("{"), COMENNT("//");
	
	private String value;
	
	/* 
	 * constructor
	 * param value - the value using string of the type
	 */
	private ReservedWords(String value){
		this.value = value;
	}
	
	/**
	 * @return the current type value
	 */
	public String getValue(){
		return value;
	}
	
}
