package hr.fer.oprpp1.hw04.db;

/**
 * Enum that represents token types for {@link QueryLexer}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public enum QueryTokenType {
	
	/**
	 * Represents tokens for {@link StudentRecord} fields.
	 * @since 1.0.0.
	 */
	
	FIELD,
	
	/**
	 * Represents tokens for {@link IComparisonOperator}.
	 * @since 1.0.0.
	 */
	COMPARISON_OPERATOR,
	
	/**
	 * Represents token for string literals.
	 * @since 1.0.0.
	 */
	
	STRING,
	
	/**
	 * Represents token for AND operator.
	 * @since 1.0.0.
	 */
	
	AND,
	
	/**
	 * Represents token for end of query.
	 * @since 1.0.0.
	 */
	
	EOQ

}
