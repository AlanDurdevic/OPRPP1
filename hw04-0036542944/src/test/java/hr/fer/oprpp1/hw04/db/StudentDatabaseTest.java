package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	private StudentDatabase studentDatabase;
	
	private class FilterTrue implements IFilter{

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
		
	}
	
	private class FilterFalse implements IFilter{

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
		
	}
	
	@BeforeEach
	void setUp() throws IOException {
		List<String> lines = Files.readAllLines(
				 Paths.get("./database.txt"),
				 StandardCharsets.UTF_8
				);
		studentDatabase = new StudentDatabase(lines);
	}
	
	@Test
	public void testForJMBAG() {
		assertEquals(null, studentDatabase.forJMBAG("0000000064"));
		assertEquals(new StudentRecord("0000000034", "Majić", "Diana", 3), studentDatabase.forJMBAG("0000000034"));
	}
	
	@Test
	public void testFilterTrue() {
		List<StudentRecord> list = studentDatabase.filter(new FilterTrue());
		assertEquals(63, list.size());
	}
	
	@Test
	public void testFilterFalse() {
		List<StudentRecord> list = studentDatabase.filter(new FilterFalse());
		assertEquals(0, list.size());
	}
	 

}
