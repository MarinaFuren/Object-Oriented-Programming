package oop.ex5.filter;

import java.io.File;

/**
 * This filter receives a filter, and returns the opposite (the files
 * that didn't pass the filter).
 *
 */
public class NotFilter implements Filter {

	private Filter filter;

	/**
	 * The constructor of this filter, gets a regular filter.
	 * @param filter
	 */
	public NotFilter(Filter filter) {
		this.filter = filter;
	}
	
	@Override
	public boolean isPass(File file) {
		return !(filter.isPass(file));
	}

}
