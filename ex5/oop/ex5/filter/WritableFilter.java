package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on Properties Filter, checks if the file
 * is writable, and checks the suffix of the filter name, if it's
 * yes o not and passes accordingly.
 */
public class WritableFilter extends PropertiesFilter implements Filter {


	/**
	 * The constructor of this filter, saves the parameter.
	 * @param isWritable
	 */
	public WritableFilter(String isWritable){
		super(isWritable);
	}
	
	@Override
	public boolean isPass(File file) {
		parserSuffix(getValue());
		if (file.canWrite() == flag){
			return true;
		}
		return false;
	}

}
