package oop.ex5.filescript;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import oop.ex5.filter.*;
import oop.ex5.order.Order;
import oop.ex5.order.OrderFactory;


/**
 * The parse class is response to read the command file,
 * to check the validity of the format, to create the filters and
 * the orders, and to create sections, when every section has filter, 
 * order and warnings (if something was wrong at the section).
 *
 */
public class Parser {
	private ArrayList<Section> sections;
	private Filter newFilter;
	private Order newOrder;
	private int[] warnings;
	private static final String FILTER = "FILTER";
	private static final String ORDER = "ORDER";
	private static final String SEPARATOR  = "#";
	private static final int WARNINGS_PER_SECTION = 2;
	private static final int WARNINGS_FILTER = 0;
	private static final int WARNINGS_ORDER = 1;
	private static final int NAME_PLACE = 0;
	
	/**
	 * The constructor creates new array of sections.
	 */
	public Parser(){
		sections = new ArrayList<Section>();
	}

	/**
	 * The method reads the command file, calls to the factories of the filter
	 * and the order and creates according to the command file the filter and order.
	 * If the factories throws the two type exception, it catch it and put at the warning 
	 * array the number of the line.
	 * @param commandFile
	 * @return ArrayList<Section>
	 * @throws IOException
	 * @throws BadInputError
	 */
	@SuppressWarnings("resource")
	public ArrayList<Section> parseFile(File commandFile) throws IOException, BadInputError{
		String filterName;
		String filterLine;
		String[] partsFilter;
		String orderName = null;
		String orderLine = null;
		String[] partsOrder = null;
		int lineNumber = 0;
		
		FileReader fileReader = new FileReader(commandFile);
		BufferedReader br = new BufferedReader(fileReader);
		
		while ((filterLine = br.readLine()) != null){
			warnings = new int[WARNINGS_PER_SECTION];
			if (FILTER.equals(filterLine) || FILTER.equals(orderLine)){
				if (!FILTER.equals(orderLine)){
					filterLine = br.readLine();
					lineNumber++;
				}
				lineNumber++;
				partsFilter = filterLine.split(SEPARATOR);
				filterName = partsFilter[NAME_PLACE];
				try {
					newFilter = FilterFactory.createFilter(filterName, partsFilter);
				} catch (WarningException e){
					newFilter = FilterFactory.createDefaultFilter();
					warnings[WARNINGS_FILTER] = lineNumber;
				}
			} else {
				throw new BadInputError();
			}
			if (ORDER.equals(br.readLine())){
				lineNumber++;
				orderLine = br.readLine();
				lineNumber++;
				if (orderLine != null){
					if (FILTER.equals(orderLine)){
						break;
					}
					partsOrder = orderLine.split(SEPARATOR);
					orderName = partsOrder[NAME_PLACE];	
					try {
						newOrder = OrderFactory.createOrder(orderName, partsOrder);
					} catch (WarningException e){
						newOrder = OrderFactory.createDefaultOrder();
						warnings[WARNINGS_ORDER] = lineNumber;
					}
				} else {
					newOrder = OrderFactory.createDefaultOrder();
				}

			} else {
				throw new BadInputError();
			}
			Section section = new Section(newFilter, newOrder, warnings);
			sections.add(section);
		}
		br.close();
		fileReader.close();
		return sections;
	}
}
