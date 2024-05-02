package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Class that represents integer for {@link SmartScriptParser}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ElementConstantInteger extends Element{
	
	/**
	 * Integer value.
	 * @since 1.0.0.
	 */
	
	private final int value;
	
	/**
	 * Constructor with parameter value.
	 * @param value of element
	 * @since 1.0.0.
	 */
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * @return value
	 * @since 1.0.0.
	 */
	
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Method that returns this object as text.
	 * @return this object as text
	 * @since 1.0.0.
	 */

	@Override
	public String asText() {
		return String.format("%d", this.value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) throw new NullPointerException();
		if(this == obj) return true;
		if(!(obj instanceof ElementConstantInteger)) return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		return this.value == other.value ;
	}
	
}
