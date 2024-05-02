package hr.fer.oprpp1.hw04.db;

/**
 * Interface that provides method that checks if values satisfied given condition.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface IComparisonOperator {
	
	/**
	 * Method that checks if condition is satisfied for given values.
	 * @param value1 value1
	 * @param value2 value2
	 * @return <code>true</code> if condition is satisfied; <code>false</code> otherwise
	 * @since 1.0.0.
	 */
	
	public boolean satisfied(String value1, String value2);

}
