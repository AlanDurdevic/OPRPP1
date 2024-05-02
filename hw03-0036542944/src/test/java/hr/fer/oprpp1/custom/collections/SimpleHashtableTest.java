package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {
	
	SimpleHashtable<String, Integer> table;
	SimpleHashtable<String, Integer> tableCopy;
	SimpleHashtable<String, Integer> emptyTable;
	
	@BeforeEach
	void setUp() {
		 table = new SimpleHashtable<>(2);
		 emptyTable = new SimpleHashtable<>(2);
		 tableCopy = new SimpleHashtable<>(2);
		 table.put("Ivana", 2);
		 table.put("Ante", 2);
		 table.put("Jasna", 2);
		 table.put("Kristina", 5);
		 table.put("Vinko", null);
		 tableCopy.put("Ivana", 2);
		 tableCopy.put("Ante", 2);
		 tableCopy.put("Jasna", 2);
		 tableCopy.put("Kristina", 5);
		 tableCopy.put("Vinko", null);
	}
	
	@Test
	public void testTableEntry() {
		TableEntry<String, Integer> tableEntry1 = new TableEntry<String, Integer>("Marko", 4);
		TableEntry<String, Integer> tableEntry2 = new TableEntry<String, Integer>("Luka", 1);
		TableEntry<String, Integer> tableEntry3 = new TableEntry<String, Integer>("Marko", 4);
		assertThrows(NullPointerException.class, () -> new TableEntry<Object, Object>(null, 2));
		assertEquals("Marko", tableEntry1.getKey());
		assertEquals(4, tableEntry1.getValue());
		tableEntry2.setValue(5);
		assertEquals(5, tableEntry2.getValue());
		assertEquals("Marko=4", tableEntry1.toString());
		assertEquals(false, tableEntry1.equals(tableEntry2));
		assertEquals(true, tableEntry1.equals(tableEntry3));
	}
	
	@Test
	public void testConstructorException() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Object, Object>(0));
	}
	
	@Test
	public void testIsEmpty() {
		assertEquals(true, emptyTable.isEmpty());
		assertEquals(false, table.isEmpty());
	}
	
	@Test
	public void testSize() {
		assertEquals(0, emptyTable.size());
		assertEquals(5, table.size());
	}
	
	@Test
	public void testEquals() {
		assertEquals(false, table.equals(emptyTable));
		assertEquals(true, table.equals(tableCopy));
	}
	
	@Test
	public void testGet() {
		assertEquals(null, table.get("Alan"));
		assertEquals(2, table.get("Ante"));
		assertEquals(null, table.get("Vinko"));
	}
	
	@Test
	public void testContainsKey() {
		assertEquals(false, table.containsKey("Petar"));
		assertEquals(true, table.containsKey("Jasna"));
		assertEquals(false, table.containsKey(null));
	}
	
	@Test
	public void testContainsValue() {
		assertEquals(false, table.containsValue(7));
		assertEquals(true, table.containsValue(2));
		assertEquals(true, table.containsValue(null));
	}
	
	@Test
	public void testToString() {
		assertEquals("[]", emptyTable.toString());
		assertEquals("[Ante=2, Ivana=2, Jasna=2, Kristina=5, Vinko=null]", table.toString());
	}
	
	@Test
	public void testPut() {
		 assertEquals(null, emptyTable.put("Ivana", 2));
		 emptyTable.put("Ivana", 2);
		 emptyTable.put("Ante", 2);
		 emptyTable.put("Jasna", 2);
		 emptyTable.put("Kristina", 5);
		 emptyTable.put("Vinko", null);
		 assertEquals(emptyTable, table);
		 assertEquals(2, table.put("Ante", 2));
	}
	
	@Test
	public void testRemove() {
		assertEquals(null, table.remove("Mislav"));
		assertEquals(null, table.remove(null));
		assertEquals(2, table.remove("Ante"));
	}
	
	@Test
	public void testToArray() {
		assertEquals(0, emptyTable.toArray().length);
		assertEquals(5, table.toArray().length);
	}
	
	@Test
	public void testClear() {
		table.clear();
		assertEquals(emptyTable, table);
	}
	
	@Test
	public void testIterator() {
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = emptyTable.iterator();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = table.iterator();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter3 = tableCopy.iterator();
		assertEquals(false, iter1.hasNext());
		assertThrows(NoSuchElementException.class, () -> iter1.next());
		assertEquals(true, iter2.hasNext());
		assertEquals(iter2.next(), iter3.next());
		assertEquals("Ivana=2", iter2.next().toString());
		iter2.next();
		iter2.next();
		iter2.next();
		assertEquals(false, iter2.hasNext());
		assertThrows(NoSuchElementException.class, () -> iter2.next());
		iter3.next();
		iter3.remove();
		assertEquals(false, tableCopy.containsKey("Ivana"));
		assertThrows(IllegalStateException.class, () -> iter3.remove());
		tableCopy.remove("Ante");
		assertThrows(ConcurrentModificationException.class, () -> iter3.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> iter3.next());
		assertThrows(ConcurrentModificationException.class, () -> iter3.remove());
	}

}
