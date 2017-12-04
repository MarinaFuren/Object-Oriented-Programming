package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on Properties Filter, checks if the file
 * is hidden, and checks the suffix of the filter name, if it's
 * yes o not and passes accordingly.
 */
public class HiddenFilter extends PropertiesFilter implements Filter {

	/**
	 * The constructor of this filter, saves the parameter.
	 * @param isHidden
	 */
	public HiddenFilter(String isHidden){
		super(isHidden);
	}

	@Override
	public boolean isPass(File file) {
		parserSuffix(getValue());
		if (file.isHidden() == flag){
			return true;
		}
		return false;
	}
}
