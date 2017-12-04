package oop.ex5.order;

/**
 * The order factory, according to the name creates new order.
 * If the suffix is "REVERSE" creates the "REVERSE" filter that returns exactly the opposite
 * order.
 */
public class OrderFactory {
	private static final String REVERSE = "REVERSE";
	private static final String ABS = "abs";
	private static final String TYPE = "type";
	private static final String SIZE = "size";
	private static final int MAXIMAL_LENGTH = 2;
	private static final int MINIMAL_LENGTH = 1;
	
	/**
	 * This method gets the name of the order and creates the correct order. 
	 * And checks for validity, if something wrong (for example the name of the order), 
	 * throws type two exception.
	 * @param orderName
	 * @param orderParts
	 * @return Order
	 * @throws InvalidOrderError
	 */
	public static Order createOrder(String orderName, String[] orderParts) throws InvalidOrderError{
		if (isValid(orderParts)) {
			if (orderName.equals(ABS)){
				if (orderParts.length == MAXIMAL_LENGTH){
					return new ReverseOrder(new AbsOrder());
				}
				return new AbsOrder();
			} else if (orderName.equals(TYPE)){
				if (orderParts.length == MAXIMAL_LENGTH){
					return new ReverseOrder(new TypeOrder());
				}
				return new TypeOrder();
			} else if (orderName.equals(SIZE)){
				if (orderParts.length == MAXIMAL_LENGTH){
					return new ReverseOrder(new SizeOrder());
				}
				return new SizeOrder();
			} else {
				throw new InvalidOrderError();
			}
		} else {
			throw new InvalidOrderError();
		}
	}
	
	/**
	 * Creates the default order (Abs).
	 * @return Order
	 */
	public static Order createDefaultOrder(){
		return new AbsOrder();
	}
	
	
	/*
	 * Check if the number of the elements at the name of the order is legal.
	 * return true if it's valid, else false.
	 */
	private static boolean isValid(String[] partsOrder){
		if (partsOrder.length == MAXIMAL_LENGTH || partsOrder.length == MINIMAL_LENGTH){
			if (partsOrder.length == MAXIMAL_LENGTH){
				if (REVERSE.equals(partsOrder[1])){
					return true;
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
