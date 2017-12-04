package oop.ex5.filter;

import java.io.File;

/**
 * This kind of filter, response on the size filters.
 */
public abstract class SizeFilter implements Filter {
	
	protected String upperBound;
	protected String lowerBound;
	private static final long KB = 1024;

	/**
	 * Changes the bytes to KB.
	 * @param file
	 * @return long - the size in kb.
	 */
	public long fileSizeKb(File file){
		return file.length()/KB;
	}

}
