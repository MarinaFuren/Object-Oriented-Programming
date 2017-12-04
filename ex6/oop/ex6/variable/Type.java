package oop.ex6.variable;

/**
 * This enum of types that sjava recognize.
 */
public enum Type {
	INT("int"), DOUBLE("double"), STRING("String"), CHAR("char"), BOOLEAN("boolean");

	private String value;
	
	/*
	 * Constructor of types, receives value (String).
	 */
	private Type (String value){
		this.value = value;
	}
	
	/**
	 * getter of the enum type.
	 * @return value
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * Checks if the type exist in this enums.
	 * @param line
	 * @return boolean
	 */
	public static boolean contains(String line){
		for (Type curr : Type.values()) {
			if(curr.getValue().equals(line))
				return true;
		}
		return false;
	}

} 

