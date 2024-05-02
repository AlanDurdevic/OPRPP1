package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class that represents token for {@link  QueryLexer}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class QueryToken {
	
	/**
	 * QueryToken type.
	 * @since 1.0.0.
	 */
	
	private QueryTokenType type;
	
	/**
	 * Value of QueryToken.
	 * @since 1.0.0.
	 */
	
	private String value;
	
	/**
	 * Constructor to create QueryToken.
	 * @param type {@linkplain QueryToken}
	 * @param value token value
	 * @throws NullPointerException if <code>type</code> is <code>null</code> or
	 * type is not {@link QueryTokenType}.EOQ and <code>value</code> is <code>null</code>.
	 * @since 1.0.0.
	 */
	
	public QueryToken(QueryTokenType type, String value) {
		this.type = Objects.requireNonNull(type, "Type can not be null!");
		if(value == null && type != QueryTokenType.EOQ) throw new NullPointerException("Value can not be null if QueryTokenType is not EOQ");
		this.value = value;
	}
	
	/**
	 * Getter for QueryToken type.
	 * @return type of token.
	 * @since 1.0.0.
	 */

	public QueryTokenType getType() {
		return type;
	}
	
	/**
	 * Getter for QueryToken value.
	 * @return value of token.
	 * @since 1.0.0.
	 */

	public String getValue() {
		return value;
	}
	

}
