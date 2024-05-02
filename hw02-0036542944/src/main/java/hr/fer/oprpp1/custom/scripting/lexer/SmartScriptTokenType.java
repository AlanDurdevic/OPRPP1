package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enum that represents token types for {@link SmartScriptLexer}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public enum SmartScriptTokenType {
	
	/**
	 * Represents end of file token.
	 * @since 1.0.0.
	 */
	
	EOF,
	
	/**
	 * Represents double token.
	 * @since 1.0.0.
	 */
	
	DOUBLE,
	
	/**
	 * Represents integer token.
	 * @since 1.0.0.
	 */
	
	INTEGER,
	
	/**
	 * Represents function token.
	 * @since 1.0.0.
	 */
	
	FUNCTION,
	
	/**
	 * Represents operator token.
	 * @since 1.0.0.
	 */
	
	OPERATOR,
	
	/**
	 * Represents text token (everything outside TAG).
	 * @since 1.0.0.
	 */
	
	TEXT,
	
	/**
	 * Represents variable token.
	 * @since 1.0.0.
	 */
	
	VARIABLE,
	
	/**
	 * Represents tagStart token.
	 * @since 1.0.0.
	 */
	
	TAG_START,
	
	/**
	 * Represents tagEnd token.
	 * @since 1.0.0.
	 */
	
	TAG_END,
	
	/**
	 * Represents string token.
	 * @since 1.0.0.
	 */
	
	STRING
	
	
}
