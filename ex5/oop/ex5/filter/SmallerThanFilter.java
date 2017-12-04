package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on size filter.
 * Gets upper bound, and passes only the files that their size is smaller.
 */
public class SmallerThanFilter extends SizeFilter implements Filter {
	
	/**
	 * The constructor of this filter, save the upper bound.
	 * @param upperBound
	 */
	public SmallerThanFilter(String upperBound){
		this.upperBound = upperBound;
	}
	
	@Override
	public boolean isPass(File file) {
		if (fileSizeKb(file) < Double.parseDouble(upperBound)){
			return true;
		}
		return false;
	}

}
