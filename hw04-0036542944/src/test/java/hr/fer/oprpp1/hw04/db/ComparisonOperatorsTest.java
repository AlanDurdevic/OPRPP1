package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	IComparisonOperator oper;	
	
	@Test
	public void testComparisonOperatorLESS() {
		oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorLESS_OR_EQUALS() {
		oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorGREATER() {
		oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorGREATER_OR_EQUALS() {
		oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorEQUALS() {
		oper = ComparisonOperators.EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorNOT_EQUALS() {
		oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorLIKE() {
		oper = ComparisonOperators.LIKE;
		assertThrows(NullPointerException.class, () -> oper.satisfied(null, null));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Ana", "Jas**na"));
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAA", "*A"));
		
	}

}
