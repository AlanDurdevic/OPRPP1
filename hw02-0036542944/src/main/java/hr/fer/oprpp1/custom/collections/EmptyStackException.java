package hr.fer.oprpp1.custom.collections;

/**
 * Exception used when <code>stack</code> is empty.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @since 1.0.0
	 */

	public EmptyStackException() {
		super();
	}
	
	/**
	 * Constructor with message parameter.
	 * @param message message of exception
	 * @since 1.0.0.
	 */
	
	public EmptyStackException(String message) {
		super(message);
	}
	
}
