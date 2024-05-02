package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Class that represents token for {@link  SmartScriptLexer}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class SmartScriptToken {
	
	/**
	 * SmartScriptToken type.
	 * @since 1.0.0.
	 */

	private SmartScriptTokenType type;
	
	/**
	 * Value of SmartScriptToken.
	 * @since 1.0.0.
	 */

	private Object value;
	
	/**
	 * Contructor to create SmartScriptToken.
	 * @param type {@linkplain SmartScriptTokenType}
	 * @param value token value
	 * @throws NullPointerException if <code>type</code> is <code>null</code> or
	 * type is not {@link SmartScriptTokenType}.EOF and <code>value</code> is <code>null</code>.
	 *  @since 1.0.0.
	 */


	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		if(type == null) throw new NullPointerException();
		if(type != SmartScriptTokenType.EOF && value == null) throw new NullPointerException();
		this.type = type;
		this.value = value;
	}
	
	/**
	 *Method that return value of SmartScriptToken.
	 *@return token value
	 *@since 1.0.0.
	 */

	public Object getValue() {
		return this.value;
	}
	
	/**
	 *Method that return type of SmartScriptToken.
	 *@return token type
	 *@since 1.0.0.
	 */

	public SmartScriptTokenType getType() {
		return this.type;
	}

}
