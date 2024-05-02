package hr.fer.oprpp1.custom.collections;

/**
 * Interface that provides method for testing Objects.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface Tester {
	
	/**
	 * Method that tests given Object.
	 * @param obj given object
	 * @return <code>true</code> if object passes test; <code>false</code> otherwise
	 * @since 1.0.0.
	 */
	
	boolean test(Object obj);

}
