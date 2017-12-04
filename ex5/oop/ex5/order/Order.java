package oop.ex5.order;

import java.io.File;
import java.util.Comparator;

/**
 * The order interface, has one method 'compare' because this interface extends
 * the Comparator interface, and the method 'sort'.
 */
public interface Order extends Comparator<File> {
	
	static final int EQUAL = 0;
	static final int LESS = -1;
	static final int GREATER = 1;
	
}
