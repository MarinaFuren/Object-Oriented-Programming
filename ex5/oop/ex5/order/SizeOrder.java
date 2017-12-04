package oop.ex5.order;

import java.io.File;

/**
 * The size order sort files by file size, going from smallest
 * to largest. If two files equals by this order, uses the default order (abs).
 */
public class SizeOrder extends AbsOrder implements Order  {

	@Override
	public int compare(File file1, File file2) {
		Long fileSize1 = new Long((long) file1.length());
		Long fileSize2 = new Long((long) file2.length());
		int result = fileSize1.compareTo(fileSize2);
		if (result != EQUAL){
			return result;
		} else {
			return super.compare(file1, file2);
		}
	}
	
}
