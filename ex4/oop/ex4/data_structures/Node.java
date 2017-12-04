package oop.ex4.data_structures;

/**
 * Node class, the Avl tree created from nodes, every node
 * has a value (int), a height and pointers to the left node to 
 * the right node, and up to the parent., (If the node is the root
 * then the parent is null).
 * @author Marina
 */
public class Node {
	private Node right;
	private Node left;
	private Node parent;
	private int data;
	private int height;
	private final static int INITIAL_HEIGHT = 0;
	private final static int INITIAL_DATA = 0;
	
	/**
	 * A default constructor.
	 */
	public Node(){
		Init();
		setData(INITIAL_DATA);
	}
	
	/**
	 * A constructor that gets the value and put it in the node.
	 * @param data
	 */
	public Node(int data){
		Init();
		this.setData(data);
	}
	
	/*
	 *This method contains everything that in common to the constructors.  
	 */
	private void Init(){
		setRight(null);
		setLeft(null);
		setParent(null);
		setHeight(INITIAL_HEIGHT);
	}

	/**
	 * @return the value of the node.
	 */
	public int getData() {
		return data;
	}

	/**
	 * @param data that the node receives.
	 */
	public void setData(int data) {
		this.data = data;
	}

	/**
	 * @return the left node.
	 */
	public Node getLeft() {
		return left;
	}

	/**
	 * Sets the left node.
	 * @param left
	 */
	public void setLeft(Node left) {
		this.left = left;
	}

	/**
	 * @return the right node.
	 */
	public Node getRight() {
		return right;
	}

	/**
	 * Sets the right node.
	 * @param right
	 */
	public void setRight(Node right) {
		this.right = right;
	}

	/**
	 * @return the height of the node.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the node.
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the parent of the node.
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Sets the parent of the node.
	 * @param parent
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}
}
