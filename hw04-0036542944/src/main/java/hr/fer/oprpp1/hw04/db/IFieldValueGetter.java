package hr.fer.oprpp1.hw04.db;

/**
 * Interface that provides method for getting value from specific student's field.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface IFieldValueGetter {
	
	/**
	 * Method that returns value from specified student's field.
	 * @param record record of student
	 * @return value of specified student's field
	 * @since 1.0.0.
	 */
	
	public String get(StudentRecord record);

}
