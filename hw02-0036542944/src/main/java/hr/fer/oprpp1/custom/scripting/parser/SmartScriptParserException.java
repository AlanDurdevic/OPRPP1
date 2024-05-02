package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Exception used for {@link SmartScriptParser}.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class SmartScriptParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @since 1.0.0
	 */
	
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor with message parameter.
	 * @param message message of exception
	 * @since 1.0.0.
	 */
	
	public SmartScriptParserException(String message) {
		super(message);
	}

}
