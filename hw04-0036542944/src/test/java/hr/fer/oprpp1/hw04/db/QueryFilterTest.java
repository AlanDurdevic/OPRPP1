package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	
	
	@Test
	public void testContructor() {
		assertThrows(NullPointerException.class, () -> new QueryFilter(null));
	}
	
	@Test
	public void testSingleFilter() {
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "000000009", ComparisonOperators.LESS));
		QueryFilter filter = new QueryFilter(list);
		assertEquals(false, filter.accepts(new StudentRecord("1000000000", "Modrić", "Luka", 5)));
		assertEquals(true, filter.accepts(new StudentRecord("0000000001", "Modrić", "Luka", 5)));
	}
	
	@Test
	public void testMultipleFilter() {
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Al*", ComparisonOperators.LIKE));
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000009", ComparisonOperators.LESS));
		QueryFilter filter = new QueryFilter(list);
		assertEquals(false, filter.accepts(new StudentRecord("0000000005", "Modrić", "Luka", 5)));
		assertEquals(true, filter.accepts(new StudentRecord("0000000001", "Đurđević", "Alan", 4)));
	}

}
