package oop.ex5.filter;

import java.io.File;

/**
 * The filter that based on string filter.
 * Checks the file name, if it's the same then the file passes.
 */
public class FileFilter extends StringFilter implements Filter {

	/**
	 * The constructor of this filter, saves the file name.
	 * @param fileName
	 */
	public FileFilter(String fileName){
		super(fileName);
	}

	@Override
	public boolean isPass(File file) {
		if (file.getName().equals(getValue())){
			return true;
		}
		return false;
	}

}
