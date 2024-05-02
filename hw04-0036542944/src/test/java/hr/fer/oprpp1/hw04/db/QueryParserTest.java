package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	QueryParser parser;
	
	@Test
	public void testConstructor() {
		assertThrows(NullPointerException.class, ()-> new QueryParser(null));
	}
	
	@Test
	public void test1() {
		parser = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals(true, parser.isDirectQuery());
		assertEquals("0123456789", parser.getQueriedJMBAG());
		assertEquals(1, parser.getQuery().size());
	}
	
	@Test
	public void test2() {
		parser =  new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(false, parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
		assertEquals(2, parser.getQuery().size());
	}
	
	@Test
	public void testEqualQueries() {
		QueryParser parser1 = new QueryParser("      lastName=\"Bosnić\"");
		QueryParser parser2 = new QueryParser("lastName     =\"Bosnić\"");
		QueryParser parser3 = new QueryParser("lastName=        \"Bosnić\"");
		QueryParser parser4 = new QueryParser("lastName     =    \"Bosnić\"");
		assertEquals(parser1.getQuery().size(), parser2.getQuery().size());
		assertEquals(parser1.getQuery().size(), parser3.getQuery().size());
		assertEquals(parser1.getQuery().size(), parser3.getQuery().size());
		assertEquals(parser1.getQuery().get(0).comparisonOperator, parser2.getQuery().get(0).comparisonOperator);
		assertEquals(parser1.getQuery().get(0).comparisonOperator, parser3.getQuery().get(0).comparisonOperator);
		assertEquals(parser1.getQuery().get(0).comparisonOperator, parser4.getQuery().get(0).comparisonOperator);
		assertEquals(parser1.getQuery().get(0).fieldGetter, parser2.getQuery().get(0).fieldGetter);
		assertEquals(parser1.getQuery().get(0).fieldGetter, parser3.getQuery().get(0).fieldGetter);
		assertEquals(parser1.getQuery().get(0).fieldGetter, parser4.getQuery().get(0).fieldGetter);
		assertEquals(parser1.getQuery().get(0).stringLiteral, parser2.getQuery().get(0).stringLiteral);
		assertEquals(parser1.getQuery().get(0).stringLiteral, parser3.getQuery().get(0).stringLiteral);
		assertEquals(parser1.getQuery().get(0).stringLiteral, parser4.getQuery().get(0).stringLiteral);
	}
	
	@Test
	public void testFalseQueries() {
		assertThrows(QueryParserException.class, () -> new QueryParser("jmbag=\"0000000003\" AND"));
		assertThrows(QueryParserException.class, () -> new QueryParser("LIKE jmbag=\"0000000003\" AND"));
		assertThrows(QueryParserException.class, () -> new QueryParser("LIKE jmbag=\"0000000003\" AND jmbag="));
		assertThrows(QueryParserException.class, () -> new QueryParser("LIKE jmbag=\"0000000003\" AND jmbag=\"21\" \"123\""));
	}

}