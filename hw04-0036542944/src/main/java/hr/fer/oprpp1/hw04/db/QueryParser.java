package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;

import java.util.List;

/**
 * Class that represents parser for query.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class QueryParser {
	
	/**
	 * Lexer of parser.
	 * @since 1.0.0.
	 */
	
	private final QueryLexer lexer;
	
	/**
	 * List of parsed expressions.
	 * @since 1.0.0.
	 */
	
	List<ConditionalExpression> expressions;
	
	/**
	 * Constructor for parser.
	 * @param query to be parsed
	 * @throws NullPointerException if <code>query</code> is <code>null</code>
	 * @throws QueryParserException if there is error in parsing.
	 * @since 1.0.0.
	 */
	
	public QueryParser(String query) {
		if(query == null) throw new NullPointerException("Query can not be null!");
		lexer = new QueryLexer(query);
		expressions = new ArrayList<>();
		try {
			lexer.nextToken();
			parse();
		}
		catch(QueryLexerException exc) {
			throw new QueryParserException(exc.getMessage());
		}
	}
	
	/**
	 * Method that parses.
	 * @throws QueryParserException if there is error in parsing
	 * @since 1.0.0.
	 */
	
	private void parse() {
		while(lexer.getToken().getType() != QueryTokenType.EOQ) {
			if(lexer.getToken().getType() == QueryTokenType.AND) lexer.nextToken();
			if(lexer.getToken().getType() != QueryTokenType.FIELD) throw new QueryParserException("Invalid number of arguments!");
			IFieldValueGetter fieldGetter = getFieldGetter();
			if(lexer.nextToken().getType() != QueryTokenType.COMPARISON_OPERATOR) throw new QueryParserException("Invalid number of arguments!");
			IComparisonOperator comparisonOperator = getComparisonOperator();
			if(lexer.nextToken().getType() != QueryTokenType.STRING) throw new QueryParserException("Invalid number of arguments!");
			String stringLiteral = lexer.getToken().getValue();
			expressions.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
			if(lexer.nextToken().getType() != QueryTokenType.AND && lexer.getToken().getType() != QueryTokenType.EOQ) throw new QueryParserException("Invalid number of arguments!");
		}
	}

	/**
	 * Method that finds {@link IComparisonOperator} in query.
	 * @return {@link IComparisonOperator}
	 * @throws QueryParserException if there is error in parsing.
	 * @since 1.0.0
	 */

	private IComparisonOperator getComparisonOperator() {
		switch(lexer.getToken().getValue()) {
		case ">":
			return ComparisonOperators.GREATER;
		case "<":
			return ComparisonOperators.LESS;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException("Invalid ComparisonOperator!");
		}
	}
	
	/**
	 * Method that finds {@link IFieldValueGetter} in query.
	 * @return {@link IFieldValueGetter}
	 * @throws QueryParserException if there is error in parsing.
	 * @since 1.0.0
	 */

	private IFieldValueGetter getFieldGetter() {
		switch(lexer.getToken().getValue()) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		default:
			throw new QueryParserException("Invalid FieldValueGetter!");
		}
	}
	
	/**
	 * Method that checks if query is direct (jmbag="xxx").
	 * @return <code>true</code> if query is direct; <code>false</code> otherwise
	 * @since 1.0.0.
	 */

	public boolean isDirectQuery() {
		if(expressions.size() == 1 && expressions.get(0).getFieldGetter() == FieldValueGetters.JMBAG &&
				expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method that returns JMBAG of direct query.
	 * @return JMBAG
	 * @throws IllegalStateException if query is not direct query
	 * @since 1.0.0.
	 */
	
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException();
		return expressions.get(0).getStringLiteral();
	}
	
	/**
	 * Method that returns {@link List} of expressions in query.
	 * @return {@link List} of expression
	 * @since 1.0.0.
	 */
	
	public List<ConditionalExpression> getQuery(){
		return expressions;
	}

}
