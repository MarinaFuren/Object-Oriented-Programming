package oop.ex5.filter;

import java.io.File;

/**
 * The filter interface, has one method if the file passes.
 */
public interface Filter {
	
	/**
	 * This method decides if the file is pass the filter or not,
	 * accordingly to the specific filter and parameters.
	 * @param file
	 * @return true if it's passed, else false.
	 */
	public boolean isPass(File file);
}
