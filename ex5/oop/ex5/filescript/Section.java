package oop.ex5.filescript;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import oop.ex5.filter.Filter;
import oop.ex5.order.Order;

/**
 * This class holds the filter and the order of each section.
 * Filters the files, and all the files that passes saves them in
 * new array, and then sorts them by the order.
 *
 */
public class Section {
	private Order order;
	private Filter filter;
	private ArrayList<File> fixedFileArray;
	private int[] warnings;
	
	/**
	 * Default constructor.
	 */
	public Section(){
	}
	
	/**
	 * Constructor that gets the filter, order, and warnings and saves them.
	 * @param filter
	 * @param order
	 * @param warnings
	 */
	public Section(Filter filter, Order order, int[] warnings) {
		this.order = order;
		this.filter = filter;
		this.warnings = warnings;
	}
	
	/**
	 * This method gets the array of the files, filter them, and then
	 * sort them by the order.
	 * @param fileArray
	 * @param sections
	 * @return
	 */
	public ArrayList<File> filterOrderSection(File[] fileArray){
		fixedFileArray = new ArrayList<File>();
		for (File file: fileArray){
			if (filter.isPass(file) && file.isFile()){
				fixedFileArray.add(file);
			}
		}
		Collections.sort(fixedFileArray, order);
		return (fixedFileArray);
	}
	
	/**
	 * Getter of the warnings array.
	 * @return int[]
	 */
	public int[] getWarnings(){
		return warnings;
	}
	
	/**
	 * Getter of the new file array (after passing the filter and with the 
	 * new order).
	 * @return ArrayList<File>
	 */
	public ArrayList<File> getFileArray(){
		return fixedFileArray;
	}

}
