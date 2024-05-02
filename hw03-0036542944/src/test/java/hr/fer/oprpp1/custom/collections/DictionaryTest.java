package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DictionaryTest{
	
	Dictionary<String, Integer> dictionary;
	Dictionary<String, Integer> dictionaryCopy;
	Dictionary<String, Integer> emptyDictionary;
	
	@BeforeEach
	void setUp() {
		dictionary = new Dictionary<>();
		dictionaryCopy = new Dictionary<>();
		emptyDictionary = new Dictionary<>();
		dictionary.put("Ivana", 1);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 2);
		dictionary.put("Vinko", null);
		dictionaryCopy.put("Ivana", 1);
		dictionaryCopy.put("Ante", 2);
		dictionaryCopy.put("Jasna", 2);
		dictionaryCopy.put("Vinko", null);
	}
	
	@Test
	public void testIsEmpty() {
		assertEquals(true, emptyDictionary.isEmpty());
		assertEquals(false, dictionary.isEmpty());
	}
	
	@Test
	public void testSize() {
		assertEquals(0, emptyDictionary.size());
		assertEquals(4,  dictionary.size());
	}
	
	@Test
	public void testEquals() {
		assertEquals(false, dictionary.equals(emptyDictionary));
		assertEquals(true, dictionary.equals(dictionaryCopy));
	}
	
	@Test
	public void testClear() {
		dictionary.clear();
		assertEquals(0, dictionary.size());
		assertEquals(dictionary, emptyDictionary);
	}
	
	@Test
	public void testGet() {
		assertEquals(null, emptyDictionary.get("Fran"));
		assertEquals(Integer.valueOf(2), dictionary.get("Ante"));
		assertEquals(null, dictionary.get(null));
	}
	
	@Test
	public void testRemove() {
		assertEquals(null, emptyDictionary.remove("Ante"));
		assertEquals(2, dictionary.remove("Ante"));
		assertEquals(null, dictionary.get("Mirko"));
	}
	
	@Test
	public void testPut() {
		assertEquals(null, dictionary.put("Karlo", 4));
		assertEquals(2, dictionary.put("Jasna", 5));
	}
	

	
}