package oop.ex5.order;

import java.io.File;

/**
 * This order, gets a regular order, and returns the opposite order, doing
 * reverse.
 */
public class ReverseOrder implements Order {
	private static final int OPPOSITE_ORDER = -1;

	private Order order;
	
	/**
	 * This is a constructor of this order, gets a regular order, and saves it.
	 * @param order
	 */
	public ReverseOrder(Order order){
		this.order = order;
	}
	
	@Override
	public int compare(File file1, File file2) {
		return (OPPOSITE_ORDER*(order.compare(file1, file2)));
	}

}
