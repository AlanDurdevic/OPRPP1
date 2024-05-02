package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Class that represents double for {@link SmartScriptParser}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ElementConstantDouble extends Element {
	
	/**
	 * Double value.
	 * @since 1.0.0.
	 */

	private final double value;
	
	/**
	 * Constructor with parameter value.
	 * @param value of element
	 * @since 1.0.0.
	 */

	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * @return value
	 * @since 1.0.0.
	 */

	public double getValue() {
		return this.value;
	}
	
	/**
	 * Method that returns this object as text.
	 * @return this object as text
	 * @since 1.0.0.
	 */

	@Override
	public String asText() {
		return String.format("%f", this.value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) throw new NullPointerException();
		if(this == obj) return true;
		if(!(obj instanceof ElementConstantDouble)) return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		return this.value == other.value ;
	}

}
