package hr.fer.oprpp1.hw04.db;

/**
 * Exception used for {@link QueryParser}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class QueryParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @since 1.0.0
	 */
	
	public QueryParserException() {
		super();
	}
	
	/**
	 * Constructor with message parameter.
	 * @param message message of exception
	 * @since 1.0.0.
	 */
	
	public QueryParserException(String message) {
		super(message);
	}

}
