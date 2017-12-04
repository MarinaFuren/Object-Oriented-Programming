package oop.ex5.filter;

import java.io.File;

/**
 * The filter that passes all the files.
 */
public class AllFilter implements Filter {

	@Override
	public boolean isPass(File file) {
		return true;
	}

}
