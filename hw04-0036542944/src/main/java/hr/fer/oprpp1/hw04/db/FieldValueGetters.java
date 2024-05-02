package hr.fer.oprpp1.hw04.db;

/**
 * Class that provides some implementations of {@link IFieldValueGetter}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class FieldValueGetters {
	
	/**
	 * FieldValueGetter that returns student's first name.
	 * @since 1.0.0.
	 */
	
	public static final IFieldValueGetter FIRST_NAME = new IFieldValueGetter() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public String get(StudentRecord record) {
			if(record == null) throw new NullPointerException("Record can not be null!");
			return record.getFirstName();
		}
	};
	
	/**
	 * FieldValueGetter that returns student's last name.
	 * @since 1.0.0.
	 */
	
	public static IFieldValueGetter LAST_NAME = new IFieldValueGetter() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public String get(StudentRecord record) {
			if(record == null) throw new NullPointerException("Record can not be null!");
			return record.getLastName();
		}
	};
	
	/**
	 * FieldValueGetter that returns student's JMBAG.
	 * @since 1.0.0.
	 */
	
	public static IFieldValueGetter JMBAG = new IFieldValueGetter() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public String get(StudentRecord record) {
			if(record == null) throw new NullPointerException("Record can not be null!");
			return record.getJmbag();
		}
	};

}
