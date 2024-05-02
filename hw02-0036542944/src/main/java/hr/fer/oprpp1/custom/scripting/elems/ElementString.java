package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Class that represents string for {@link SmartScriptParser}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ElementString extends Element{
	
	/**
	 * String value.
	 * @since 1.0.0.
	 */
	
	private final String value;
	
	/**
	 * Constructor with parameter value.
	 * @param value of string
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public ElementString(String value) {
		if(value == null) throw new NullPointerException();
		this.value = value;
	}
	
	/**
	 * Getter for name.
	 * @return name
	 * @since 1.0.0.
	 */
	
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Method that returns this object as text.
	 * @return this object as text
	 * @since 1.0.0.
	 */

	@Override
	public String asText() {
		return this.value;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) throw new NullPointerException();
		if(this == obj) return true;
		if(!(obj instanceof ElementString)) return false;
		ElementString other = (ElementString) obj;
		return this.value.equals(other.value);
	}

}
