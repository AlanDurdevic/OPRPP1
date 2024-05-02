package hr.fer.oprpp1.hw04.db;

/**
 * Class <code>QueryLexer</code> it class that represents lexical analyzer.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class QueryLexer {
	
	/**
	 * Input text.
	 * 
	 * @since 1.0.0
	 */

	private char[] data;
	
	/**
	 * Current token.
	 * 
	 * @since 1.0.0.
	 */

	private QueryToken token;
	
	/**
	 * Index of first not tokenized character.
	 * 
	 * @since 1.0.0.
	 */

	private int currentIndex;
	
	/**
	 * Constructor that gets query to be tokenized.
	 * 
	 * @param query to be tokenized.
	 * @throws NullPointerException if <code>query</code> is <code>null<code>
	 * @since 1.0.0.
	 */

	public QueryLexer(String query) {
		if (query == null)
			throw new NullPointerException("Query can not be null!");
		this.data = query.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Method that generates and returns next token.
	 * @return next token
	 * @throws QueryLexerException if there is no more tokens or next token is in invalid form.
	 * @since 1.0.0.
	 */

	public QueryToken nextToken() {
		if (token != null && token.getType() == QueryTokenType.EOQ) {
			throw new QueryLexerException();
		}
		skipBlanks();
		if (currentIndex >= data.length) {
			token = new QueryToken(QueryTokenType.EOQ, null);
			return token;
		}
		if (Character.isLetter(data[currentIndex])) {
			token = findWord();
		}
		else if(data[currentIndex] == '"') {
			currentIndex++;
			token = findQuotation();
		}
		else if(data[currentIndex] == '>' || data[currentIndex] == '<' || data[currentIndex] == '=' || data[currentIndex] == '!') {
			token = findOperator();
		}
		else throw new QueryLexerException("False token");
		return token;
	}
	
	/**
	 * Method that finds {@link IComparisonOperator} and returns token.
	 * @return token.
	 * @throws QueryLexerException if next token is invalid form.
	 * @since 1.0.0.
	 */

	private QueryToken findOperator() {
		StringBuilder sb  = new StringBuilder();
		if(currentIndex + 1 < data.length) {
			sb.append(data[currentIndex]);
			sb.append(data[currentIndex + 1]);
			switch(sb.toString()){
			case ">=":
				currentIndex += 2;
				return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, ">=");
			case "<=":
				currentIndex += 2;
				return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "<=");
			case "!=":
				currentIndex += 2;
				return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "!=");
			}
		}
		switch(data[currentIndex]) {
		case '>':
			currentIndex++;
			return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, ">");
		case '<':
			currentIndex++;
			return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "<");
		case '=':
			currentIndex++;
			return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "=");
		default:
			throw new QueryLexerException("Invalid operator!");
		}

	}
	
	/**
	 * Method that finds string literal and return token.
	 * @return token
	 * @throws QueryLexerException if token is in invalid form.
	 * @since 1.0.0.
	 */

	private QueryToken findQuotation() {
		StringBuilder sb = new StringBuilder();
		while(currentIndex < data.length && data[currentIndex] != '"') {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		if(currentIndex == data.length) throw new QueryLexerException("Closing \" not found.");
		currentIndex++;
		return new QueryToken(QueryTokenType.STRING, sb.toString());
	}
	
	/**
	 * Method that finds {@link IFieldValueGetter} or {@link IComparisonOperator}.LIKE  or AND operator and return token.
	 * @return token
	 * @throws QueryLexerException if token is in invalid form.
	 * @since 1.0.0.
	 */

	private QueryToken findWord() {
		StringBuilder sb = new StringBuilder();
		QueryToken newToken;
		while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		switch (sb.toString()) {
		case "jmbag":
			newToken = new QueryToken(QueryTokenType.FIELD, "jmbag");
			break;
		case "lastName":
			newToken = new QueryToken(QueryTokenType.FIELD, "lastName");
			break;
		case "firstName":
			newToken = new QueryToken(QueryTokenType.FIELD, "firstName");
			break;
		case "LIKE":
			newToken = new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "LIKE");
			break;
		default:
			if(sb.toString().toUpperCase().equals("AND")) {
				newToken = new QueryToken(QueryTokenType.AND, "AND");
			}
			else throw new QueryLexerException(sb.toString() + " is not field name or valid operator");
		}
		return newToken;
	}
	
	/**
	 * Returns current token.
	 * @return current token
	 * @throws NullPointerException if current token is <code>null</code>
	 * @since 1.0.0.
	 */

	public QueryToken getToken() {
		if (this.token == null)
			throw new QueryLexerException();
		return token;
	}
	
	/**
	 * Method that skips all connected blanks in query.
	 * @since 1.0.0.
	 */

	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
				this.currentIndex++;
			} else
				break;
		}
	}

}
