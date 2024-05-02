package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class that provides some implementations of {@link IComparisonOperator}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ComparisonOperators {
	
	/**
	 * Comparison operator that checks if value1 is less that value2.
	 * @since 1.0.0.
	 */
	
	public final static IComparisonOperator LESS = new IComparisonOperator() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) < 0;
		}
	};
	
	/**
	 * Comparison operator that checks if value1 is less that value2, or if they are equal.
	 * @since 1.0.0.
	 */
	
	public final static IComparisonOperator LESS_OR_EQUALS = new IComparisonOperator() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) <= 0;
		}
	};
	
	/**
	 * Comparison operator that checks if value1 is greater that value2.
	 * @since 1.0.0.
	 */
	
	public final static IComparisonOperator GREATER = new IComparisonOperator() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) > 0;
		}
	};
		
	/**
	 * Comparison operator that checks if value1 is greater that value2, or if they are equal.
	 * @since 1.0.0.
	 */
	
	public final static IComparisonOperator GREATER_OR_EQUALS = new IComparisonOperator() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) >= 0;
		}
	};
	
	/**
	 * Comparison operator that checks if value1 equals value2.
	 * @since 1.0.0.
	 */
	
	public final static IComparisonOperator EQUALS = new IComparisonOperator() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.equals(value2);
		}
	};
	
	/**
	 * Comparison operator that checks if value1 not equals value2.
	 * @since 1.0.0.
	 */
	
	public final static IComparisonOperator NOT_EQUALS = new IComparisonOperator() {
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return !value1.equals(value2);
		}
	};
	
	/**
	 * Comparison operator that checks if value1 is like value2.
	 * @since 1.0.0.
	 */
	
	public final static IComparisonOperator LIKE = new IComparisonOperator() {
		
		/**
		 * {@inheritDoc}
		 * @throws NullPointerException if <code>value1</code> or <code>value2</code> is <code>null</code>
		 * @throws IllegalArgumentException if there is more than one Wildcard character (*)
		 */
		
		@Override
		public boolean satisfied(String value1, String value2) {
			Objects.requireNonNull(value1, "Value1 can not be null!");
			Objects.requireNonNull(value2, "Value2 can not be null!");
			int numberOfWildcard = 0;
			for(char c : value2.toCharArray()) {
				if(c == '*') numberOfWildcard++;
			}
			if(numberOfWildcard > 1) throw new IllegalArgumentException("Number of Wildcard can not be more than 1!");
			return value1.matches(value2.replace(String.valueOf("*"), ".*"));
		}
	};

}
