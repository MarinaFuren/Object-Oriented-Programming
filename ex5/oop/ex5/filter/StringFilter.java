package oop.ex5.filter;

/**
 * This kind of filter, response on the name of the filter.
 */
public abstract class StringFilter implements Filter {
	
	private String value;
	protected static final int NOT_EXIST = -1;
	
	/**
	 * The constructor
	 * @param value
	 */
	public StringFilter(String value){
		this.value = value;
	}
	
	/*
	 * Getter of the value.
	 */
	protected String getValue(){
		return value;
	}

}
