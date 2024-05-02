package hr.fer.oprpp1.hw04.db;

/**
 * Exception used for {@link QueryLexer}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class QueryLexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 * @since 1.0.0
	 */
	
	public QueryLexerException() {
		super();
	}
	
	/**
	 * Constructor with message parameter.
	 * @param message message of exception
	 * @since 1.0.0.
	 */
	
	public QueryLexerException(String message) {
		super(message);
	}
}
