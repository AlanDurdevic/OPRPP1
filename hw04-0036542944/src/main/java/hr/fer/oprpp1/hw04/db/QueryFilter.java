package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class that represents filter for queries.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class QueryFilter implements IFilter{
	
	/**
	 * {@link List} of {@link ConditionalExpression} in query.
	 * @since 1.0.0.
	 */
	
	private List<ConditionalExpression> listOfConditionalExpressions;
	
	/**
	 * Constructor for QueryFilter.
	 * @param listConditionalExpressions {@link List} of conditional expressions
	 * @throws NullPointerException if list of expressions is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public QueryFilter(List<ConditionalExpression> listConditionalExpressions) {
		if(listConditionalExpressions == null) throw new NullPointerException("List of conditional expressions can not be null!");
		this.listOfConditionalExpressions = listConditionalExpressions;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression expression : listOfConditionalExpressions) {
			if(!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record), expression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
