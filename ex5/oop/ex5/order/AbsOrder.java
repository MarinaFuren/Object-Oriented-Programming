package oop.ex5.order;

import java.io.File;

/**
 * The default order is Abs order, sort files by the absolute name
 * going from 'a' to 'z'.
 *
 */
public class AbsOrder implements Order{

	@Override
	public int compare(File file1, File file2) {
		return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
	}

}
