package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	private ArrayIndexedCollection collectionEmpty;
	private ArrayIndexedCollection collectionWithFewElements;
	private ArrayIndexedCollection collectionWithFewElementsCopy;
	private ArrayIndexedCollection collectionWithFullElements; 
	
	@BeforeEach
	public void setUp() {
		collectionEmpty = new ArrayIndexedCollection();
		
		collectionWithFewElements = new ArrayIndexedCollection();
		collectionWithFewElements.add(0);
		collectionWithFewElements.add(1);
		collectionWithFewElements.add(2);
		collectionWithFewElements.add(3);
		collectionWithFewElements.add(4);
		collectionWithFewElements.add(5);
		collectionWithFewElements.add(6);
		
		collectionWithFewElementsCopy = new ArrayIndexedCollection();
		collectionWithFewElementsCopy.add(0);
		collectionWithFewElementsCopy.add(1);
		collectionWithFewElementsCopy.add(2);
		collectionWithFewElementsCopy.add(3);
		collectionWithFewElementsCopy.add(4);
		collectionWithFewElementsCopy.add(5);
		collectionWithFewElementsCopy.add(6);
		
		collectionWithFullElements = new ArrayIndexedCollection();
		for(int i = 0; i < 16; i++) {
			collectionWithFullElements.add(i);
		}
		
		
	}
	
	@Test
	public void testConstructors() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 5));
	}
	
	@Test
	public void testIsEmptyMethod() {
		assertEquals(true, collectionEmpty.isEmpty());
		assertEquals(false, collectionWithFewElements.isEmpty());
		assertEquals(false, collectionWithFullElements.isEmpty());
	}
	
	@Test
	public void testSizeMethod() {
		assertEquals(0, collectionEmpty.size());
		assertEquals(7, collectionWithFewElements.size());
		assertEquals(16, collectionWithFullElements.size());
	}
	
	@Test
	public void testAddMethod() {
		assertThrows(NullPointerException.class, () -> collectionWithFewElements.add(null));
		for(int i = 0; i < 7; i++) {
			collectionEmpty.add(i);
		}
		assertEquals(true, collectionEmpty.equals(collectionWithFewElements));
	}
	
	@Test
	public void testContainsMethod() {
		assertEquals(false, collectionEmpty.contains(-5));
		assertEquals(false, collectionWithFewElements.contains(-5));
		assertEquals(false, collectionWithFullElements.contains(-5));
		assertEquals(true, collectionWithFewElements.contains(2));
		assertEquals(true, collectionWithFullElements.contains(2));		
	}
	
	@Test
	public void testRemoveValueMethod() {
		assertEquals(false, collectionEmpty.remove((Object)5));
		assertEquals(true, collectionWithFewElements.remove((Object)5));
		assertEquals(false, collectionWithFewElements.remove((Object)10));
	}
	
	@Test
	public void testEqualsMethod() {
		assertEquals(false, collectionEmpty.equals(collectionWithFewElements));
		assertEquals(true, collectionEmpty.equals(collectionEmpty));
		assertEquals(false, collectionWithFewElements.equals(collectionWithFullElements));
		assertEquals(true, collectionWithFewElements.equals(collectionWithFewElements));
		assertEquals(true, collectionWithFewElements.equals(collectionWithFewElementsCopy));	
	}
	
	@Test
	public void testToArrayMethod() {
		Object[] testObject = new Object[] {0, 1, 2, 3, 4, 5, 6};
		assertEquals(false, collectionWithFewElements.toArray() == testObject);
		assertEquals(true, Arrays.equals(collectionWithFewElements.toArray(), new Object[] {0, 1, 2, 3, 4, 5, 6}));
		assertEquals(false, collectionWithFullElements.toArray() == collectionWithFullElements.toArray());
	}
	
	@Test
	public void testAddAllMethod() {
		ArrayIndexedCollection collectionTest1 = new ArrayIndexedCollection();
		ArrayIndexedCollection collectionTest2 = new ArrayIndexedCollection();
		ArrayIndexedCollection collectionTest3 = new ArrayIndexedCollection();
		ArrayIndexedCollection collectionTest4 = new ArrayIndexedCollection();
		collectionTest1.add(7);
		collectionTest1.add(8);
		for(int i = 0; i < 9; i++) {
			collectionTest2.add(i);
		}
		
		for(int i = 0; i < 18; i++) {
			collectionTest3.add(i);
		}
		
		collectionTest4.add(16);
		collectionTest4.add(17);
		
		collectionEmpty.addAll(collectionWithFewElements);
		assertEquals(true, collectionEmpty.equals(collectionWithFewElements));
		collectionWithFewElements.addAll(collectionTest1);
		assertEquals(true, collectionWithFewElements.equals(collectionTest2));
		collectionWithFullElements.addAll(collectionTest4);
		assertEquals(true, collectionTest3.equals(collectionWithFullElements));
	}
	
	@Test
	public void testClearMethod() {
		collectionWithFewElements.clear();
		assertEquals(true, collectionEmpty.equals(collectionWithFewElements));
	}
	
	@Test
	public void testGetMethod() {
		assertThrows(IndexOutOfBoundsException.class,() ->  collectionWithFewElements.get(10));
		assertEquals(5, collectionWithFullElements.get(5));
	}
	
	@Test
	public void testIndexOfMethod() {
		assertEquals(-1, collectionWithFewElements.indexOf(-2));
		assertEquals(6, collectionWithFewElements.indexOf(6));
	}
	
	@Test
	public void testRemoveIndexMethod() {
		assertThrows(IndexOutOfBoundsException.class, () ->  collectionWithFullElements.remove(-2));
		assertThrows(IndexOutOfBoundsException.class, () ->  collectionWithFewElements.remove(10));
		collectionWithFewElements.remove(5);
		assertEquals(false, collectionWithFewElements.contains(5));
	}
	
	@Test
	public void testInsertMethod() {
		assertThrows(NullPointerException.class, ()->collectionWithFewElements.insert(null, 0));
		assertThrows(IndexOutOfBoundsException.class, ()->collectionWithFewElements.insert(5, 10));
		Object[] testArray = collectionWithFewElements.toArray();
		collectionWithFewElements.insert(-2, 0);
		assertEquals(testArray[0], collectionWithFewElements.toArray()[1]);
		testArray = collectionWithFullElements.toArray();
		collectionWithFullElements.insert(90, 16);
		assertEquals(testArray[15], collectionWithFullElements.toArray()[15]);
	}
	
	
}