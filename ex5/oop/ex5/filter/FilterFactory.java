package oop.ex5.filter;

/**
 * The filter factory, according to the name creates new filter.
 * And passes to the constructor the parameters (if has), if the suffix
 * is "NOT" creates the "NOT" filter that returns exactly the opposite.
 */
public class FilterFactory {
	private static final String NOT = "NOT";
	private static final String HAS_PERMITION = "YES";
	private static final String HAS_NOT_PERMITION = "NO";
	private static final String GREATER = "greater_than";
	private static final String SMALLER = "smaller_than";
	private static final String BETWEEN = "between";
	private static final String FILE = "file";
	private static final String CONTAINS = "contains";
	private static final String PREFIX = "prefix";
	private static final String SUFFIX = "suffix";
	private static final String WRITABLE = "writable";
	private static final String HIDDEN = "hidden";
	private static final String EXECUTABLE = "executable";
	private static final String ALL = "all";
	private static final int MAXIMAL_LENGTH = 4;
	private static final int MINIMAL_LENGTH = 1;
	private static final int LENGTH_ONE_PARAM_NOT = 3;
	private static final int LENGTH_NO_PARAM_NOT = 2;
	
	/**
	 * This method gets the name of the filter, and the filter parts,
	 * and creates the correct filter, and checks for validity, if something wrong,
	 * throws type two exception.
	 * @param filterName
	 * @param filterParts
	 * @return Filter
	 * @throws InvalidFilterError
	 */
	public static Filter createFilter(String filterName, String[] filterParts) throws InvalidFilterError{
		if (isValid(filterParts)){
			if (filterName.equals(GREATER) && isValidNumber(filterParts[1], null)){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new GreaterThanFilter(filterParts[1]));
				}
				return new GreaterThanFilter(filterParts[1]);
			} else if (filterName.equals(SMALLER) && isValidNumber(filterParts[1], null)){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new SmallerThanFilter(filterParts[1]));
				}
				return new SmallerThanFilter(filterParts[1]);
			} else if (filterName.equals(BETWEEN) && isValidNumber(filterParts[1], filterParts[2])){
				if (checkSuffix(MAXIMAL_LENGTH, filterParts)){
					return new NotFilter(new BetweenFilter(filterParts[1], filterParts[2]));
				}
				return new BetweenFilter(filterParts[1], filterParts[2]);
			} else if (filterName.equals(FILE)){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new FileFilter(filterParts[1]));
				}
				return new FileFilter(filterParts[1]);
			} else if (filterName.equals(CONTAINS)){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new ContainsFilter(filterParts[1]));
				}
				return new ContainsFilter(filterParts[1]);
			} else if (filterName.equals(PREFIX)){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new PrefixFilter(filterParts[1]));
				}
				return new PrefixFilter(filterParts[1]);
			} else if (filterName.equals(SUFFIX)){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new SuffixFilter(filterParts[1]));
				}
				return new SuffixFilter(filterParts[1]);
			} else if (filterName.equals(WRITABLE) && isValidParameter(filterParts[1])){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new WritableFilter(filterParts[1]));
				}
				return new WritableFilter(filterParts[1]);
			} else if (filterName.equals(EXECUTABLE) && isValidParameter(filterParts[1])){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new ExecutableFilter(filterParts[1]));
				}
				return new ExecutableFilter(filterParts[1]);
			} else if (filterName.equals(HIDDEN) && isValidParameter(filterParts[1])){
				if (checkSuffix(LENGTH_ONE_PARAM_NOT, filterParts)){
					return new NotFilter(new HiddenFilter(filterParts[1]));
				}
				return new HiddenFilter(filterParts[1]);
			} else if (filterName.equals(ALL)){
				if (checkSuffix(LENGTH_NO_PARAM_NOT, filterParts)){
					return new NotFilter(new AllFilter());
				}
				return new AllFilter();
			} else {
				throw new InvalidFilterError();
			}
		}
		return null;
	}
	
	/**
	 * Creates default filter, AllFilter.
	 */
	public static Filter createDefaultFilter(){
		return new AllFilter();
	}
	
	/*
	 * Checks if the properties filter got valid parameter- "YES" or "NO."
	 * returns true if it's valid, else false.
	 */
	private static boolean isValidParameter(String str) {
		if (HAS_PERMITION.equals(str) || HAS_NOT_PERMITION.equals(str)){
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Checks if the numbers parameters to the size filter are valid.
	 * If they are non-negative, and if has 2 parameters that the first bigger from the second.
	 * returns true if it's valid, else false.
	 */
	private static boolean isValidNumber(String number1, String number2) {
		double number1Double = Double.parseDouble(number1);
		
		if (number1Double >= 0){
			if (number2 != null){
				double number2Double = Double.parseDouble(number2);
				if (number2Double >= 0 && number2Double >= number1Double){
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/*
	 * Checks if the number of the elements at the filter name is legal. 
	 * returns true if it's valid, else false.
	 */
	private static boolean isValid(String[] filterParts){
		if (filterParts.length > MAXIMAL_LENGTH || filterParts.length < MINIMAL_LENGTH){
			return false;
		} else {
			return true;
		}	
	}
	
	/*
	 * Checks if the filter has the suffix by the number of valid parameters.
	 * And if it has it can be only the "NOT" suffix.
	 * returns true if it's valid, else false.
	 */
	private static boolean checkSuffix(int legalLength, String[] filterParts) throws InvalidFilterError{
		if (filterParts.length == legalLength){
			if (NOT.equals(filterParts[legalLength-1])){
				return true;
			} else {
				throw new InvalidFilterError();
			}
		} else {
			return false;
		}
	}
	
	
}
