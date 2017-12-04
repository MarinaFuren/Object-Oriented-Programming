package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Avl tree is a self balancing BST. In an Avl tree the differs
 * between two children of any node is not bigger then one.
 * Inserting or deleting may require balancing the tree by one or more
 * rotations.
 * @author Marina
 */

public class AvlTree implements Iterable<Integer> {
	private Node root;
	private int size;
	private boolean isInserted;
	private boolean isRemoved;
	private boolean found;
	private int deapth;
	private final static int AVL_CONDITION = 1;
	private final static int ROOT_HEIGHT = 1;
	private final static int NO_HEIGHT = -1;
	private final static int NO_FOUND = -1;
	private final static int MINIMAL_ELEMENTS = 2;
	private final static int INITIAL_SIZE = 0;
	private final static int STOP_CONDITION_1 = 0;
	private final static int RESULT_1 = 1;
	private final static int STOP_CONDITION_2 = 1;
	private final static int RESULT_2 = 2;
	private final static int MINIMUM_NODES = 1;
	
	/**
	 * The default constructor.
	 */
	public AvlTree(){
		Init();
	}
	
	/**
	 * A constructor that builds the tree by adding the elements
	 * in the input array one by one. If a value appears more than once
	 * in the list, only the first appearance is added.
	 * @param data the values to add to tree.
	 */
	public AvlTree(int[] data){
		Init();
		for(int elem: data){
			add(elem);
		}
	}
	
	/**
	 * A copy constructor that creates a deep copy of the given AvlTree.
	 * This means that for every node or any other internal object of the
	 * given tree, a new, identical object, is instantiated for the new tree
	 * (the internal object is not simply referenced from it).
	 * The new tree must contain all the values of the given tree, but not
	 * necessarily in the same structure.
	 * @param AvlTree an AVL tree.
	 */
	public AvlTree(AvlTree AvlTree){
		Init();
		for(int elem: AvlTree){
			add(elem);
		}
	}
	
	/*
	 * This method contains everything that in common to the constructors. 
	 */
	private void Init(){
		root = null;
		size = INITIAL_SIZE;
		isInserted = false;
	}
	
	/**
	 * Add a new node with the given key to the tree.
	 * @param newValue the value of the new node to add.
	 * @return true if the value to add is not already in the tree
	 * and if was successfully added, false otherwise.
	 */
	public boolean add(int newValue){
		root = insert(newValue, root);
		return isInserted;
	}
	

	/*
	 * Adding new node to the tree.
	 */
	private Node addNewNode(int data, Node node){
		node = new Node(data);
		isInserted = true;
		size ++;
		return node;
	}
	
	/*
	 * Helper function to add a node, receives the data and the root.
	 * Returns the root.
	 */	
	private Node insert(int data, Node node) {
		Node newNode = null;
		isInserted = false;
		if (node == null){
			node = addNewNode(data, node);
			return node;
		}
		if (data < node.getData()) {
			if (node.getLeft() == null){
				newNode = addNewNode(data, node);
				node.setLeft(newNode);
				newNode.setParent(node);
				setNewHeight(node);
				rebalance(data, node);
			} else {
				node = insert(data, node.getLeft());
				rebalance(data, node);
			}			
		} else if (data > node.getData()) {
			if (node.getRight() == null){
				newNode = addNewNode(data, node);
				node.setRight(newNode);
				newNode.setParent(node);
				setNewHeight(node);
				rebalance(data, node);
			} else {
				node = insert(data, node.getRight());
				rebalance(data, node);
			}
			
		// Duplicate- do nothing (data == node.getData())
		} else { 
			isInserted = false;
		}
		setNewHeight(node);
		if (node.getParent() != null){
			return node.getParent();
		}
		return node;
	}
	
	/*
	 * If the tree nod balanced  (the difference between he height of
	 * the sons is bigger then 1) can happen after add anew node, or
	 * delete node, then we need to check the balance of the tree,
	 * and if need to fix it by rotation.
	 */
	private void rebalance(int data, Node node) {
		if (size > MINIMAL_ELEMENTS){ //can't happen if at the tree only 2 elements
			if (node.getParent() != null){
				node = node.getParent();
			}
			if (checkBalance(node)){
				if (height(node.getRight()) > height(node.getLeft())){
					if (node.getRight() != null && (data > node.getRight().getData()
							|| (isRemoved && (node.getRight().getRight()) != null))){
						node = rotateWithRightChild(node);
					} else {
						node = doubleWithRightChild(node);
					}
				} else if (height(node.getRight()) < height(node.getLeft())){
					if (node.getLeft() != null && (data < node.getLeft().getData()
							|| (isRemoved && (node.getLeft().getLeft()) != null))){
						node = rotateWithLeftChild(node);
					} else {
						node = doubleWithLeftChild(node);
					}
				}
			}
		}
	}

	/*
	 * Updates the height of the node.
	 */
	private void setNewHeight(Node node) {
		node.setHeight(Math.max(height(node.getRight()), 
				height(node.getLeft())) + ROOT_HEIGHT);		
	}

	/*
	 * Checks the balance of the tree by the formula: |h(T(x.left)) - h(T(x.right))|<=1
	 * if it's not will return true- that need to balance the tree.
	 */
	private boolean checkBalance(Node node) {  
		if (Math.abs((height(node.getRight()) - height(node.getLeft()))) > AVL_CONDITION){
			return true;
			}
		return false;
	}


	/*
	 * The RL case - will balance the tree by double rotate,
	 * first right child with its left child, then node with new right child.
	 */
	private Node doubleWithRightChild(Node node) {
		node.setRight(rotateWithLeftChild(node.getRight()));
		return rotateWithRightChild(node);
	}
	

	/*
	 * The RR case - will balance the tree by one rotate with right child.
	 */
	private Node rotateWithRightChild(Node node) {
		Node newNode = node.getRight();
		node.setRight(newNode.getLeft());
		if (newNode.getLeft() != null){
			(newNode.getLeft()).setParent(node);
		}
		newNode.setLeft(node);
		newNode.setParent(node.getParent());
		if (node.getParent() != null && newNode.getData() < (node.getParent()).getData()){
			node.getParent().setLeft(newNode);
		} else if (node.getParent() != null){
			node.getParent().setRight(newNode);
		}
		node.setParent(newNode);
		setNewHeight(node);
		setNewHeight(newNode);
		return newNode;
	}

	/*
	 * The LR case - will balance the tree by double rotate,
	 * first left child with its right child, then node with new left child.
	 */
	private Node doubleWithLeftChild(Node node) {
		node.setLeft(rotateWithRightChild(node.getLeft()));
		return rotateWithLeftChild(node);
	}

	/*
	 * The LL case - will balance the tree by one rotate with left child.
	 */
	private Node rotateWithLeftChild(Node node) {
		Node newNode = node.getLeft();
		node.setLeft(newNode.getRight());
		if (newNode.getRight() != null){
			(newNode.getRight()).setParent(node);//
		}
		newNode.setRight(node);
		newNode.setParent(node.getParent());
		if (node.getParent() != null && newNode.getData() > (node.getParent()).getData()){
			node.getParent().setRight(newNode);
		} else if (node.getParent() != null){
			node.getParent().setLeft(newNode);
		}
		node.setParent(newNode);
		setNewHeight(node);
		setNewHeight(newNode);
		return newNode;
	}

	/*
	 * Returns the height of the node, if it's null (no element) the height is -1.
	 */
	private int height(Node node) {
		if (node == null){
			return (NO_HEIGHT);
		} else {
			return node.getHeight();
		}
	}

	/**
	 * Check whether the tree contains the given input value.
	 * 
	 * @param searchValue the value to search for.
	 * @return the depth of the node (0 for the root) with the given value if
	 * it was found in the tree, -1 otherwise.
	 */
	public int contains(int searchValue){
		deapth = 0;
		search(searchValue, root);
		if (found){
			return deapth; 
		}
		return NO_FOUND;
	}
	
	/*
	 * Helper function to the contains, receives the root if the tree.
	 * Returns a boolean if the value was found or not.
	 */
	private boolean search(int searchValue, Node node) {
		found = false;
		while (node != null && !found){
			int nodeData = node.getData();
			if (searchValue < nodeData){
				node = node.getLeft();
			} else if (searchValue > nodeData){
				node = node.getRight();
			} else {
				found = true;
				break;
			}
			deapth ++;
			found = search(searchValue, node);
		}
		return found;
	}
	

	/**
	 * Removes the node with the given value from the tree, if it exists.
	 * 
	 * @param toDelete the values to remove from the tree.
	 * @return true if the value was found and deleted, false otherwise.
	 */
	public boolean delete(int toDelete){
		if (contains(toDelete) == NO_FOUND){
			return false;
		}
		root = remove(toDelete, root);
		return isRemoved;
	}
	
	/*
	 * Helper function, receives the root too, returns the root of the tree
	 * (can be changed after deletion). In order to delete a node we need to
	 * check how many sons it has, and then delete in each case in different way.
	 */
	private Node remove(int toDelete, Node node) {
		isRemoved = false;
		if (toDelete > node.getData()){
			node = remove(toDelete, node.getRight());
			setNewHeight(node);
			rebalance(node.getData(), node);
		} else if (toDelete < node.getData()){
			node = remove(toDelete, node.getLeft());
			setNewHeight(node);
			rebalance(node.getData(), node);
		} else { //toDelete == node.getData()){		
			checkCaseForRemove(node);
		}
		setNewHeight(node);
		if (node.getParent() != null){
			return node.getParent();
		}
		return node;
	}

	/*
	 * Checks in which way we should delete the element,
	 * if no sons it's the easy, just delete it.
	 * one son- need to connect between the grandpa and the
	 * son.
	 * 2 sons - need to find the successor, to replace the places, 
	 * and then delete the element.
	 */
	private void checkCaseForRemove(Node node) {
		//first case - no sons
		if (node.getLeft() == null && node.getRight() == null){ 
			if (node.getParent() != null && node.getParent().getLeft() == node){
				node.getParent().setLeft(null);
			} else if (node.getParent() != null && node.getParent().getRight() == node){
				node.getParent().setRight(null);
			}
			size --;
			isRemoved = true;
		//second case - one son
		} else if ((node.getLeft() == null && node.getRight() != null) || 
				(node.getLeft() != null && node.getRight() == null)){
			Node son;
			Node grandpa = node.getParent();
			if (node.getLeft() == null){ //right son
				son = node.getRight();
				son.setParent(grandpa);
				grandpa.setLeft(son);
			} else {
				son = node.getLeft(); // left son
				son.setParent(grandpa);
				grandpa.setRight(son);
			}
			size --;
			isRemoved = true;	
		// third case - has 2 sons
		} else if (node.getLeft() != null && node.getRight() != null) {
			Node successor = findSuccessor(node);
			Node temp = successor;
			remove(successor.getData(), node);
			temp.setRight(node.getRight());
			if (node.getRight() != null){
				node.getRight().setParent(temp);
			}
			temp.setLeft(node.getLeft());
			if (node.getLeft() != null){
				node.getLeft().setParent(temp);
			}
			temp.setParent(node.getParent());
			node.setData(temp.getData());
			if (node.getParent() != null){
				if (node.getParent().getLeft() == node){
					node.getParent().setLeft(temp);
				} else if (node.getParent().getRight() == node){
					node.getParent().setRight(temp);
				}
			}
			size --;
			isRemoved = true;
		}
		
	}

	/*
	 * Finds the next node with the value that bigger then the value at the
	 * given node.
	 */
	private Node findSuccessor(Node node) {
		if (node.getRight() != null){
			return findMinimum(node.getRight());
		}
		Node parent = node.getParent();
		while (parent != null && parent.getRight() == node) {
			node = parent;
			parent = parent.getParent();
		}
		return parent;
	}

	/*
	 * Finds the node with the minimal value (at the avl tree always
	 * at the left subtree the most leftist leave).
	 */
	private Node findMinimum(Node node) {
		if (node.getLeft() == null){
			return (node);
		}
		return findMinimum(node.getLeft());	
	}

	/**
	 * @return the number of nodes in
	 * the tree.
	 */
	public int size(){
		return size;
	}

	
	/** 
	 *@return an iterator on the Avl Tree. The returned iterator
	 *iterates over the tree nodes in an ascending order, and does not
	 *implement the remove() method.
	 *@throws NoSuchElementException and UnsupportedOperationException.
	 */
	@Override
	public Iterator<Integer> iterator() {
		
		/*
		 *A local class of the Avl Iterator of integers.
		 */
		class AvlIterator implements Iterator<Integer>{
			Node currentNode;
			
			/**
			 * A default constructor.
			 */
			AvlIterator(){
				currentNode = findMinimum(root);
			}
			
			@Override
			public boolean hasNext() {
				if (currentNode != null){
					return true;
				}
				return false;
			}

			@Override
			public Integer next() {
				if (hasNext()){
					Node nodeToReturn = currentNode;
					currentNode = findSuccessor(currentNode);
					return (nodeToReturn).getData();
				}
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new java.lang.UnsupportedOperationException("Remove method not supported.");
			}
			
		}
		return new AvlIterator();
	}

	
	/**
	 * Calculates the minimum number of nodes in an Avl Tree of height h.
	 * @param h the height of the tree ( anon-negative number) in question.
	 * @return the minimum number of nodes in an Avl tree of the given height.
	 */
	public static int findMinNodes(int h){
		if (h == STOP_CONDITION_1){
			return RESULT_1;
		}
		if (h == STOP_CONDITION_2){
			return RESULT_2;
		}
		return (MINIMUM_NODES + findMinNodes(h-1) + findMinNodes(h-2));
	}
}
