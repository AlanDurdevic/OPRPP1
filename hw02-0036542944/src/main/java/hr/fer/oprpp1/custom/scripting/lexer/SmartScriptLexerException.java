package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Exception used for {@link SmartScriptLexer}.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class SmartScriptLexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @since 1.0.0
	 */

	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * Constructor with message parameter.
	 * @param message message of exception
	 * @since 1.0.0.
	 */
	
	public SmartScriptLexerException(String message) {
		super(message);
	}

}
