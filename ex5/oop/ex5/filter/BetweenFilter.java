package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on size filter.
 * gets two bounds, and passes only the files that in this range.
 */
public class BetweenFilter extends SizeFilter implements Filter {

	/**
	 * The constructor of this filter, saves the bounds.
	 * @param lowerBound
	 * @param upperBound
	 */
	public BetweenFilter(String lowerBound, String upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	public boolean isPass(File file) {
		if (fileSizeKb(file) >= Double.parseDouble(lowerBound) &&
				fileSizeKb(file) <= Double.parseDouble(upperBound)){
			return true;
		}
		return false;
	}

}
