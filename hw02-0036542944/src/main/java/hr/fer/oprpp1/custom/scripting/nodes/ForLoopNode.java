package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits from
 * {@link Node} class.
 * 
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class ForLoopNode extends Node {
	
	/**
	 * Property <code>variable</code>.
	 * @since 1.0.0.
	 */

	private final ElementVariable variable;
	
	/**
	 * Property <code>startExpression</code>.
	 * @since 1.0.0.
	 */

	private final Element startExpression;
	
	/**
	 * Property <code>endExpression</code>.
	 * @since 1.0.0.
	 */

	private final Element endExpression;
	
	/**
	 * Property <code>stepExpression</code>.
	 * @since 1.0.0.
	 */

	private final Element stepExpression;
	
	/**
	 * Constructor for ForLoopNode.
	 * @param variable variable
	 * @param startExpression startExpression
	 * @param endExpression endExpression
	 * @param stepExpression stepExpression
	 * @throws NullPointerException if variable or startExpression or endExpression is null
	 * @since 1.0.0.
	 */

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (variable == null || startExpression == null || endExpression == null)
			throw new NullPointerException();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Getter for variable.
	 * 
	 * @return variable
	 * @since 1.0.0.
	 */

	public ElementVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * Getter for startExpression.
	 * 
	 * @return startExpression
	 * @since 1.0.0.
	 */

	public Element getStartExpression() {
		return this.startExpression;
	}
	
	/**
	 * Getter for endExpression.
	 * 
	 * @return endExpression
	 * @since 1.0.0.
	 */

	public Element getEndExpression() {
		return this.endExpression;
	}
	
	/**
	 * Getter for stepExpression.
	 * 
	 * @return stepExpression
	 * @since 1.0.0.
	 */

	public Element getStepExpression() {
		return this.stepExpression;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{$ FOR ");
		sb.append(this.variable.asText()).append(" ");
		sb.append(this.startExpression.asText()).append(" ");
		sb.append(this.endExpression.asText()).append(" ");
		if (this.stepExpression != null)
			sb.append(this.stepExpression.asText()).append(" ");
		sb.append("$}");
		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		sb.append("{$END$}");
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
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		if(!other.startExpression.equals(this.startExpression)|| !other.endExpression.equals(this.endExpression) || !other.variable.equals(this.variable)) return false;
		if(this.stepExpression == null && other.stepExpression == null) return true;
		if(this.stepExpression != null && other.stepExpression == null) return false;
		if(this.stepExpression == null && other.stepExpression != null) return false;
		return this.stepExpression.equals(other.stepExpression);
	}

}
