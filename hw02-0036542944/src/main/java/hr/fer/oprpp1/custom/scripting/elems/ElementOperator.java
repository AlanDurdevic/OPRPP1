package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Class that represents operator for {@link SmartScriptParser}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ElementOperator extends Element{
	
	/**
	 * Operator symbol.
	 * @since 1.0.0.
	 */

	private final String symbol;
	
	/**
	 * Constructor with parameter symbol.
	 * @param symbol symbol
	 * @throws NullPointerException if <code>symbol</code> is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public ElementOperator(String symbol) {
		if(symbol == null) throw new NullPointerException();
		this.symbol = symbol;
	}
	
	/**
	 * Getter for symbol.
	 * @return symbol
	 * @since 1.0.0.
	 */
	
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Method that returns this object as text.
	 * @return this object as text
	 * @since 1.0.0.
	 */

	@Override
	public String asText() {
		return this.symbol;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) throw new NullPointerException();
		if(this == obj) return true;
		if(!(obj instanceof ElementOperator)) return false;
		ElementOperator other = (ElementOperator) obj;
		return this.symbol.equals(other.symbol);
	}
	
}
