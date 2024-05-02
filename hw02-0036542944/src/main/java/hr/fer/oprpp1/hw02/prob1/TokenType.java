package hr.fer.oprpp1.hw02.prob1;

/**
 * Enum that represents token types for {@link Lexer}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public enum TokenType {
	
	/**
	 * Represents end of file token.
	 * @since 1.0.0.
	 */
	
	EOF
	
	/**
	 * Represents word token.
	 * @since 1.0.0.
	 */
	
	, WORD
	
	/**
	 * Represents number token.
	 * @since 1.0.0.
	 */
	
	, NUMBER,
	
	/**
	 * Represents symbol token.
	 * @since 1.0.0.
	 */
	
	SYMBOL
}
