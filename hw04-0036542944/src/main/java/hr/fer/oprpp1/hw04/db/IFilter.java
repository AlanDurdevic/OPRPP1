package hr.fer.oprpp1.hw04.db;

/**
 * Interface that provides method for filtering students.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface IFilter {
	
	/**
	 * Method for filtering students.
	 * @param record {@link StudentRecord}
	 * @return <code>true</code> if student is accepted; otherwise <code>false</code>
	 * @since 1.0.0.
	 */
	
	 public boolean accepts(StudentRecord record);
	}

