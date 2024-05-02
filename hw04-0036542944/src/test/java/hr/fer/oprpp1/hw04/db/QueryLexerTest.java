package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class QueryLexerTest {
	
	private QueryLexer lexer;
	
	@Test
	public void testConstructor() {
		assertThrows(NullPointerException.class, () -> new QueryLexer(null));
	}
	
	@Test
	public void test1() {
		lexer = new QueryLexer("jmbag=\"0000000003\"");
		assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
		assertEquals("jmbag", lexer.getToken().getValue());
		assertEquals(QueryTokenType.COMPARISON_OPERATOR, lexer.nextToken().getType());
		assertEquals("=", lexer.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer.nextToken().getType());
		assertEquals("0000000003", lexer.getToken().getValue());
		assertEquals(QueryTokenType.EOQ, lexer.nextToken().getType());
		assertEquals(null, lexer.getToken().getValue());
		assertThrows(QueryLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void test2() {
		lexer = new QueryLexer("lastName = \"Blažić\"");
		assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
		assertEquals("lastName", lexer.getToken().getValue());
		assertEquals(QueryTokenType.COMPARISON_OPERATOR, lexer.nextToken().getType());
		assertEquals("=", lexer.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer.nextToken().getType());
		assertEquals("Blažić", lexer.getToken().getValue());
		assertEquals(QueryTokenType.EOQ, lexer.nextToken().getType());
		assertEquals(null, lexer.getToken().getValue());
	}
	
	@Test
	public void test3() {
		lexer = new QueryLexer("firstName>=\"A\" and lastName LIKE \"B*ć\"");
		assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
		assertEquals("firstName", lexer.getToken().getValue());
		assertEquals(QueryTokenType.COMPARISON_OPERATOR, lexer.nextToken().getType());
		assertEquals(">=", lexer.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer.nextToken().getType());
		assertEquals("A", lexer.getToken().getValue());
		assertEquals(QueryTokenType.AND, lexer.nextToken().getType());
		assertEquals("AND", lexer.getToken().getValue());
		assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
		assertEquals("lastName", lexer.getToken().getValue());
		assertEquals(QueryTokenType.COMPARISON_OPERATOR, lexer.nextToken().getType());
		assertEquals("LIKE", lexer.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer.nextToken().getType());
		assertEquals("B*ć", lexer.getToken().getValue());
		assertEquals(QueryTokenType.EOQ, lexer.nextToken().getType());
		assertEquals(null, lexer.getToken().getValue());
	}
	
	@Test
	public void test4() {
		lexer = new QueryLexer("firstName>=\"A\" and lastNameS LIKE \"B*ć\"");
		assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
		assertEquals("firstName", lexer.getToken().getValue());
		assertEquals(QueryTokenType.COMPARISON_OPERATOR, lexer.nextToken().getType());
		assertEquals(">=", lexer.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer.nextToken().getType());
		assertEquals("A", lexer.getToken().getValue());
		assertEquals(QueryTokenType.AND, lexer.nextToken().getType());
		assertEquals("AND", lexer.getToken().getValue());
		assertThrows(QueryLexerException.class, () -> lexer.nextToken());
	}

}
