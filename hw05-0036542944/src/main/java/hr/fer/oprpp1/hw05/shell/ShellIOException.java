package hr.fer.oprpp1.hw05.shell;

/**
 * Exception used fo {@link MyShell}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @since 1.0.0
	 */
	
	public ShellIOException() {
		super();
	}
	
	/**
	 * Constructor with message parameter.
	 * @param message message of exception
	 * @since 1.0.0.
	 */
	
	public ShellIOException(String message) {
		super(message);
	}

}
