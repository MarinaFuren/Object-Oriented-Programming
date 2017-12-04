package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on Properties Filter, checks if the file
 * is Executable, and checks the suffix of the filter name, if it's
 * yes o not and passes accordingly.
 */
public class ExecutableFilter extends PropertiesFilter implements Filter {

	/**
	 * Constructor of this filter, saves the parameter.
	 * @param isExecutable
	 */
	public ExecutableFilter(String isExecutable){
		super(isExecutable);
	}
	
	@Override
	public boolean isPass(File file) {
		parserSuffix(getValue());
		if (file.canExecute() == flag){
			return true;
		}
		return false;
	}
}
