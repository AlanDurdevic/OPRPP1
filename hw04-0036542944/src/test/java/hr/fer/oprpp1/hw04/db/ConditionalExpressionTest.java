package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {
	
    private ConditionalExpression expr = new ConditionalExpression(
            FieldValueGetters.LAST_NAME,
            "Bos*",
            ComparisonOperators.LIKE);
	
	@Test
	public void testConstructor() {
		assertThrows(NullPointerException.class, () -> new ConditionalExpression(null, null, null));
	}
	
	@Test
	public void testGetFieldGetter() {
		assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldGetter());
		assertNotEquals(FieldValueGetters.JMBAG, expr.getFieldGetter());
	}
	
	@Test
	public void testGetStringLiteral() {
		assertEquals("Bos*", expr.getStringLiteral());
		assertNotEquals("Bos", expr.getStringLiteral());
	}
	
	@Test
	public void testGetComparisonOperator() {
		assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());
		assertNotEquals(ComparisonOperators.EQUALS, expr.getComparisonOperator());
	}
	
	@Test
	public void testFunctioning() {
		StudentRecord studentRecord = new StudentRecord("0000000001", "Boss", "Marko", 4);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(studentRecord), 
				 expr.getStringLiteral()
				);
		assertEquals(true, recordSatisfies);
		studentRecord = new StudentRecord("0000000001", "Bomar", "Marko", 4);
		recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(studentRecord), 
				 expr.getStringLiteral()
				);
		assertEquals(false, recordSatisfies);
	}

}
