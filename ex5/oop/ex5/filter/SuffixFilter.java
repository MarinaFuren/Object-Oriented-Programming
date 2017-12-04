package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on string filter.
 * Checks the file name, if it's ends with the same suffix, the file passes.
 */
public class SuffixFilter extends StringFilter implements Filter {

	/**
	 * The constructor of this filter, saves the suffix.
	 * @param suffix
	 */
	public SuffixFilter(String suffix){
		super(suffix);
	}

	@Override
	public boolean isPass(File file) {
		if (file.getName().endsWith(getValue())){
			return true;
		}
		return false;
	}

}
