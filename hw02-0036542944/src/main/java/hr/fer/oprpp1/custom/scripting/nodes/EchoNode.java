package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output
 * dynamically. It inherits from {@link Node} class.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class EchoNode extends Node {

	/**
	 * Property elements.
	 * 
	 * @since 1.0.0.
	 * 
	 */

	private final Element[] elements;
	
	/**
	 * Constructor for EchoNode
	 * @param elements of EchoNode
	 * @throws NullPointerException if elements is null
	 * @since 1.0.0.
	 */

	public EchoNode(Element[] elements) {
		if (elements == null)
			throw new NullPointerException();
		this.elements = elements;
	}
	
	/**
	 * Getter for elements.
	 * @return elements.
	 * @since 1.0.0.
	 */

	public Element[] getElements() {
		return this.elements;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ ");
		for (Element element : this.elements) {
			sb.append(element.asText()).append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			throw new NullPointerException();
		if(this == obj) return true;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		if(this.elements.length != other.elements.length) return false;
		for(int i = 0; i < this.elements.length; i++) {
			if(!this.elements[i].equals(other.elements[i])) return false;
		}
		return true;
	}

}
