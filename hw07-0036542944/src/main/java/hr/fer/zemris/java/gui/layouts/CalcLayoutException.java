package hr.fer.zemris.java.gui.layouts;

/**
 * Exception used for {@link CalcLayout}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CalcLayoutException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @since 1.0.0.
	 */
	
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Constructor with message.
	 * @param message message
	 */
	
	public CalcLayoutException(String message) {
		super(message);
	}

}
