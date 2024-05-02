package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node representing an entire document. It inherits from {@link Node} class.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class DocumentNode extends Node {

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
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
		if (!(obj instanceof DocumentNode))
			return false;
		DocumentNode other = (DocumentNode) obj;
		if (this.numberOfChildren() != other.numberOfChildren())
			return false;
		for (int i = 0; i < this.numberOfChildren(); i++) {
			if (!this.getChild(i).equals(other.getChild(i))) {
				return false;
			}
				
		}
		return true;
	}

}
