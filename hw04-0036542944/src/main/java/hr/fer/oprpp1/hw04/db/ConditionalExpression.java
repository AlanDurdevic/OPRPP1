package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class that represents conditional expression for {@link StudentDatabase}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ConditionalExpression {
	
	/**
	 * {@link IFieldValueGetter} of expression.
	 * @since 1.0.0.
	 */
	
	IFieldValueGetter fieldGetter;
	
	/**
	 * String literal of expression.
	 * @since 1.0.0.
	 */
	
	String stringLiteral;
	
	/**
	 * {@link IComparisonOperator} of expression.
	 * @since 1.0.0.
	 */
	
	IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor which gets all needed values for expression.
	 * @param fieldGetter fieldGetter
	 * @param stringLiteral stringLiteral
	 * @param comparisonOperator comparisonOperator
	 * @throws NullPointerException if one or more parameters are <code>null</code>
	 * @since 1.0.0.
	 */

	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = Objects.requireNonNull(fieldGetter, "FieldGetter can not be null!");
		this.stringLiteral = Objects.requireNonNull(stringLiteral, "StringLiteral can not be null!");
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator, "ComparisonOperator can not be null!");
	}
	
	/**
	 * Getter for {@link IFieldValueGetter} of expression.
	 * @return fieldGetter
	 * @since 1.0.0.
	 */

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	/**
	 * Getter for string literal of expression.
	 * @return stringLiteral
	 * @since 1.0.0.
	 */

	public String getStringLiteral() {
		return stringLiteral;
	}
	
	/**
	 * Getter for {@link IComparisonOperator} of expression.
	 * @return comparisonOperator
	 * @since 1.0.0.
	 */

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	

}
