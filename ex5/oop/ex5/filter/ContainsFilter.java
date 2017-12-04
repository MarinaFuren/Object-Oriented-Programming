package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on string filter.
 * Checks if the file name has the substring.
 */
public class ContainsFilter extends StringFilter implements Filter {
	
	/**
	 * The constructor of this filter, saves the substring.
	 * @param subString
	 */
	public ContainsFilter(String subString){
		super(subString);
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean isPass(File file) {
		if (file.getName().indexOf(getValue()) != super.NOT_EXIST){
			return true;
		}
		return false;
	}

}
