package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on string filter.
 * Checks the file name, if it's starts with the same prefix, the file passes.
 */
public class PrefixFilter extends StringFilter implements Filter {

	/**
	 * The constructor of this filter, saves the prefix.
	 * @param prefix
	 */
	public PrefixFilter(String prefix){
		super(prefix);
	}

	@Override
	public boolean isPass(File file) {
		if (file.getName().startsWith(getValue())){
			return true;
		}
		return false;
	}

}
