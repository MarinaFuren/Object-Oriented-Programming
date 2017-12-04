package oop.ex5.order;

import java.io.File;

/**
 * The type order sort files by file type, going from 'a' to 'z'.
 * If two files equals by this order, uses the default order (abs).
 */
public class TypeOrder extends AbsOrder implements Order {
	
	private static final String EMPTY_STRING = ""; 
	private static final char SUFFIX_START = '.';
	
	@Override
	public int compare(File file1, File file2) {
		String extension1 = EMPTY_STRING;
		String extension2 = EMPTY_STRING;
		int i = file1.getName().lastIndexOf(SUFFIX_START);
		int j = file2.getName().lastIndexOf(SUFFIX_START);
		if (i > 0 && j > 0) {
		    extension1 = file1.getName().substring(i+1);
		    extension2 = file2.getName().substring(j+1);
		}
		int result = extension1.compareTo(extension2);
		if (result != EQUAL){
			return result;
		} else {
			return super.compare(file1, file2);
		}
	}

}
