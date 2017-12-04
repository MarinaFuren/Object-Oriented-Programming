package oop.ex5.filter;

/**
 * This kind of filter, response on the properties filters.
 */
public abstract class PropertiesFilter implements Filter {
	
	protected boolean flag = false;
	private String value;
	public static final String HAS_PERMITION = "YES";
	public static final String HAS_NOT_PERMITION = "NO";
	
	/**
	 * The constructor of this kind of filter, saves the value of the string
	 * can be "YES" or "NO".
	 * @param value
	 */
	public PropertiesFilter(String value){
		this.value = value;
	}
	
	/**
	 * Getter of the value.
	 * @return String - the value 
	 */
	public String getValue(){
		return value;
	}

	/**
	 * Changes the flag accordingly to the suffix of the filter.
	 * @param suffix
	 */
	public void parserSuffix(String suffix) {
		if (HAS_PERMITION.equals(suffix)){
			flag = true;	
		} else {
			flag = false;
		}
	}
	

}
