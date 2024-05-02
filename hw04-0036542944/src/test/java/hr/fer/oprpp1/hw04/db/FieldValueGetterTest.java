package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FieldValueGetterTest {
	
	IFieldValueGetter getter;
	
	StudentRecord studentRecord = new StudentRecord("0036542944", "Đurđević", "Alan", 5);
	
	@Test
	public void testFieldValueGetterFIRST_NAME() {
		getter = FieldValueGetters.FIRST_NAME;
		assertThrows(NullPointerException.class, () -> getter.get(null));
		assertEquals("Alan", getter.get(studentRecord));
		assertNotEquals("Marko", getter.get(studentRecord));
	}
	
	@Test
	public void testFieldValueGetterLAST_NAME() {
		getter = FieldValueGetters.LAST_NAME;
		assertThrows(NullPointerException.class, () -> getter.get(null));
		assertEquals("Đurđević", getter.get(studentRecord));
		assertNotEquals("Matić", getter.get(studentRecord));
	}
	
	@Test
	public void testFieldValueGetterJMBAG() {
		getter = FieldValueGetters.JMBAG;
		assertThrows(NullPointerException.class, () -> getter.get(null));
		assertEquals("0036542944", getter.get(studentRecord));
		assertNotEquals("0000000001", getter.get(studentRecord));
	}

}
