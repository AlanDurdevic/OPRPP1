package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Class that represents variable for {@link SmartScriptParser}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ElementVariable extends Element{
	
	/**
	 * Variable name.
	 * @since 1.0.0.
	 */
	
	private final String name;
	
	/**
	 * Constructor with parameter name.
	 * @param name of variable
	 * @throws NullPointerException if <code>name</code> is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public ElementVariable(String name) {
		if(name == null) throw new NullPointerException();
		this.name = name;
	}
	
	/**
	 * Getter for name.
	 * @return name
	 * @since 1.0.0.
	 */
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Method that returns this object as text.
	 * @return this object as text
	 * @since 1.0.0.
	 */

	@Override
	public String asText() {
		return this.name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) throw new NullPointerException();
		if(this == obj) return true;
		if(!(obj instanceof ElementVariable)) return false;
		ElementVariable other = (ElementVariable) obj;
		return this.name.equals(other.name);
	}

}
