package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Node {

	/**
	 * Collection of children.
	 * 
	 * @since 1.0.0.
	 */

	private ArrayIndexedCollection children;

	/**
	 * Method that adds given child to an internally managed collection of children.
	 * 
	 * @param child to be added.
	 * @throws NullPointerException if child is <code>null</code>
	 */

	public void addChildNode(Node child) {
		if (child == null)
			throw new NullPointerException();
		if (this.children == null) {
			this.children = new ArrayIndexedCollection();
		}
		this.children.add(child);
	}

	/**
	 * Returns a number of (direct) children.
	 * 
	 * @return number of (direct) children.
	 * @since 1.0.0.
	 */

	public int numberOfChildren() {
		if (this.children == null)
			return 0;
		return this.children.size();
	}

	/**
	 * Returns selected child.
	 * 
	 * @param index of wanted child.
	 * @return Node that represents wanted child
	 * @throws IndexOutOfBoundsException if index is invalid.
	 * @since 1.0.0.
	 */

	public Node getChild(int index) {
		if (index < 0 || index >= this.children.size())
			throw new IndexOutOfBoundsException();
		return (Node) this.children.get(index);
	}

}
