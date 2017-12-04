package oop.ex6.blocks;

import java.util.LinkedList;

import oop.ex6.blocks.Block;

/**
 * This class is a global block, that every code has, his previews block is null, and it extends block. 
 * Has list of methods.
 */
public class GlobalBlock extends Block{
	private LinkedList<Methods> methodsList;
	
	/**
	 * Default constructor
	 */
	public GlobalBlock(){
		methodsList = new LinkedList<Methods>();
	}
	
	/**
	 * this function receives a method and add it to the method list
	 * @param methodsToAdd the method we would like to add
	 */
	public void addMethod(Methods methodsToAdd) {
		methodsList.add(methodsToAdd);
	}
	
	/**
	 * @return the global block method list
	 */
	public LinkedList<Methods> getMethodsList(){
		return methodsList;
	}
}
