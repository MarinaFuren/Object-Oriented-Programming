package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on size filter.
 * Gets lower bound, and passes only the files that their size is bigger.
 */
public class GreaterThanFilter extends SizeFilter implements Filter {

	/**
	 * The constructor of this filter, saves the upper bound.
	 * @param lowerBound
	 */
	public GreaterThanFilter(String lowerBound){
		this.lowerBound = lowerBound;
	}

	@Override
	public boolean isPass(File file) {
		if (fileSizeKb(file) > Double.parseDouble(lowerBound)){
			return true;
		}
		return false;
	}

}
