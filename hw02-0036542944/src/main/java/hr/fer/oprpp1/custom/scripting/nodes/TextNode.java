package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node representing a piece of textual data. It inherits from {@link Node}
 * class.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class TextNode extends Node {

	/**
	 * Text of TextNode.
	 * 
	 * @since 1.0.0.
	 */

	private final String text;

	/**
	 * Constructor that has parameter text
	 * 
	 * @param text of TextNode
	 * @throws NullPointerException if text is <code>null</code>
	 * @since 1.0.0.
	 */

	public TextNode(String text) {
		if (text == null)
			throw new NullPointerException();
		this.text = text;
	}

	/**
	 * Getter for text of TextNode.
	 * 
	 * @return text of TextNode
	 * @since 1.0.0.
	 */

	public String getText() {
		return this.text;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		return this.text.replace("\\", "\\\\").replace("{", "\\{");
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public boolean equals(Object obj) {
		if(obj == null) throw new NullPointerException();
		if(!(obj instanceof TextNode)) return false;
		TextNode other = (TextNode) obj;
		return this.text.equals(other.text);
	}

}
