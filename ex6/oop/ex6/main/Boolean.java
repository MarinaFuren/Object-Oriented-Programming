package oop.ex6.main;

/**
 * This enum of Boolean reserved words.
 */
public enum Boolean {
	TRUE("true"), FALSE("false");
	
	private String value;
	
	/* 
	 * constructor
	 * param value - the value using string of the type
	 */
	private Boolean(String value){
		this.value = value;
	}
	
	/**
	 * @return the current type value
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * checks if the enum contains a given string
	 * @param name the name we would like to check if the enum contains 
	 * @return true if the enum contains, false otherwise
	 */
	public static boolean contains(String name){
		for (Boolean curr : Boolean.values()) {
			if(curr.name().equals(name))
				return true;
		}
		return false;
	}
}
