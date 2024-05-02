package hr.fer.oprpp1.hw02.prob1;

/**
 * Class that represents token for {@link Lexer}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Token {
	
	/**
	 * Token type.
	 * @since 1.0.0.
	 */
	
	private TokenType type;
	
	/**
	 * Value of Token.
	 * @since 1.0.0.
	 */
	
	private Object value;
	
	/**
	 * Contructor to create token.
	 * @param type {@linkplain TokenType}
	 * @param value token value
	 * @throws NullPointerException if <code>type</code> is <code>null</code> or
	 * type is not {@link TokenType}.EOF and <code>value</code> is <code>null</code>.
	 *  @since 1.0.0.
	 */

	public Token(TokenType type, Object value) {
		if(type == null) throw new NullPointerException();
		if(type != TokenType.EOF && value == null) throw new NullPointerException();
		this.type = type;
		this.value = value;
	}
	
	/**
	 *Method that return value of token.
	 *@return token value
	 *@since 1.0.0.
	 */
	
	public Object getValue() {
		return this.value;
	}
	
	/**
	 *Method that return type of token.
	 *@return token type
	 *@since 1.0.0.
	 */
	
	public TokenType getType() {
		return this.type;
	}

	
}
