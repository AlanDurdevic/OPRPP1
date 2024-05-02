package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of key-value set. One key represents one value (duplicate keys can not exist).
 * Elements are placed in set depending on theirs key hash value.
 * @param <K> key type
 * @param <V> value type
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{

	/**
	 * Class that represents TableEntry of one key and one value.
	 * @param <K> key type
	 * @param <V> value type
	 * @since 1.0.0.
	 */
	
	public static class TableEntry<K, V> {
		
		/**
		 * Key.
		 * @since 1.0.0.
		 */
		
		private K key;
		
		/**
		 * Value.
		 * @since 1.0.0.
		 */
		
		private V value;
		
		/**
		 * Next element with same place in table.
		 * @since 1.0.0.
		 */
		
		private TableEntry<K, V> next;
		
		/**
		 * Default constructor.
		 * @param key key
		 * @param value value
		 * @throws NullPointerException if <code>key<code> is <code>null</code>
		 */

		public TableEntry(K key, V value) {
			if (key == null)
				throw new NullPointerException();
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		/**
		 * Getter for key.
		 * @return key
		 * @since 1.0.0.
		 */

		public K getKey() {
			return this.key;
		}
		
		/**
		 * Getter for value.
		 * @return value
		 * @since 1.0.0.
		 */

		public V getValue() {
			return this.value;
		}
		
		/**
		 * Setter for value.
		 * @return value
		 * @since 1.0.0.
		*/

		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */

		@Override
		public String toString() {
			return this.key + "=" + this.value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			if(this == obj) return true;
			if(!(obj instanceof TableEntry)) return false;
			TableEntry<?, ?> other = (TableEntry<?, ?>) obj;
			return this.key.equals(other.key) && Objects.equals(this.value, other.value);
		}
	}
	
	/**
	 * Table of elements.
	 * @since 1.0.0.
	 */

	private TableEntry<K, V>[] table;
	
	/**
	 * Number of elements in set.
	 * @since 1.0.0.
	 */

	private int size;
	
	/**
	 * Number of modifications in set.
	 * @since 1.0.0.
	 */
	
	private long modificationCount;
	
	/**
	 * Default constructor.
	 * @since 1.0.0.
	 */

	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.size = 0;
		this.table = (TableEntry<K, V>[]) new TableEntry[16];
		this.modificationCount = 0;
	}
	
	/**
	 * Constructor with default capacity.
	 * @param capacity default capacity
	 * @throws IllegalArgumentException if <code>capacity</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException();
		double log = Math.log10(capacity) / Math.log10(2);
		int upper = (int) log + 1;
		int lower = (int) log;
		double powUpper = Math.pow(2, (double) upper);
		double powLower = Math.pow(2, (double) lower);
		if (Math.abs(powUpper - capacity) < Math.abs(powLower) - capacity) {
			this.table = (TableEntry<K, V>[]) new TableEntry[(int) powUpper];
		} else {
			this.table = (TableEntry<K, V>[]) new TableEntry[(int) powLower];
		}
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Puts pair of key-value in the dictionary. If key already exists old value becomes new value.
	 * @param key key
	 * @param value value
	 * @return old value
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException();
		TableEntry<K, V> newEntry = new TableEntry<>(key, value);
		int slot = this.getSlot(key);
		if (this.table[slot] == null) {
			if (!this.checkCapacity()) {
				this.table[slot] = newEntry;
				this.size++;
				this.modificationCount++;
				return null;
			}
			return this.put(key, value);
		} else {
			TableEntry<K, V> tempEntry = this.table[slot];
			if (key.equals(tempEntry.getKey())) {
				V oldValue = tempEntry.getValue();
				tempEntry.setValue(value);
				return oldValue;
			} else {
				while (tempEntry.next != null) {
					tempEntry = tempEntry.next;
					if (key.equals(tempEntry.getKey())) {
						V oldValue = tempEntry.getValue();
						tempEntry.setValue(value);
						return oldValue;
					}
				}
				if (!this.checkCapacity()) {
					tempEntry.next = newEntry;
					this.size++;
					this.modificationCount++;
					return null;
				}
				return this.put(key, value);
			}
		}
	}
	
	/**
	 * Method that doubles table size if current number of elements is 75% of table length.
	 * @return <code>true</code> if table size is doubled; <code>false</code> otherwise
	 */

	@SuppressWarnings("unchecked")
	private boolean checkCapacity() {
		if ((double)this.size / (double)this.table.length < 0.75)
			return false;
		TableEntry<K, V>[] tempArray = (TableEntry<K, V>[]) this.toArray();
		this.clear();
		this.table = (TableEntry<K, V>[]) new TableEntry[this.table.length * 2];
		for (int i = 0; i < tempArray.length; i++) {
			this.put(tempArray[i].getKey(), tempArray[i].getValue());
		}
		return true;
	}
	
	/**
	 * Return value of desired key.
	 * @param key desired key
	 * @return value of desired key if key exists; otherwise <code>null</code>
	 * @since 1.0.0.
	 */

	public V get(Object key) {
		if (key == null)
			return null;
		int slot = this.getSlot(key);
		TableEntry<K, V> tempEntry = this.table[slot];
		while (tempEntry != null) {
			if (key.equals(tempEntry.getKey())) {
				return tempEntry.value;
			}
			tempEntry = tempEntry.next;
		}
		return null;
	}
	
	/**
	 * Returns number of elements in Hashtable.
	 * @return number of elements.
	 * @since 1.0.0.
	 */

	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if Hashtable contains key.
	 * @param key to check
	 * @return <code>true</code> if contains; <code>false</code> otherwise.
	 * @since 1.0.0.
	 */

	public boolean containsKey(Object key) {
		if (key == null)
			return false;
		int slot = this.getSlot(key);
		TableEntry<K, V> tempEntry = this.table[slot];
		while (tempEntry != null) {
			if (key.equals(tempEntry.getKey())) {
				return true;
			}
			tempEntry = tempEntry.next;
		}
		return false;
	}
	
	/**
	 * Checks if Hashtable contains value.
	 * @param value to check
	 * @return <code>true</code> if contains; <code>false</code> otherwise.
	 * @since 1.0.0.
	 */

	public boolean containsValue(Object value) {
		for (int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> tempEntry = this.table[i];
			while (tempEntry != null) {
				if (Objects.equals(tempEntry.value, value)) {
					return true;
				}
				tempEntry = tempEntry.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes element with given key from Hashtable.
	 * @param key of element to be removed
	 * @return value of removed element; <code>null</code> if key does not exit
	 * @since 1.0.0.
	 */

	public V remove(Object key) {
		if (key == null)
			return null;
		int slot = this.getSlot(key);
		V oldValue;
		TableEntry<K, V> tempEntry = this.table[slot];
		if(tempEntry == null) return null;
		if (key.equals(tempEntry.getKey())) {
			oldValue = tempEntry.getValue();
			this.table[slot] = tempEntry.next;
			this.size--;
			this.modificationCount++;
			return oldValue;
		}
		while (tempEntry.next != null) {
			if (key.equals(tempEntry.next.getKey())) {
				oldValue = tempEntry.next.getValue();
				tempEntry.next = tempEntry.next.next;
				this.size--;
				this.modificationCount++;
				return oldValue;
			}
			tempEntry = tempEntry.next;
		}
		return null;
	}
	
	/**
	 * Checks if Hashtable is empty.
	 * @return <code>true</code> if it is empty; <code>false</code> otherwise.
	 * @since 1.0.0.
	 */

	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		TableEntry<K, V>[] newArray = (TableEntry<K, V>[]) this.toArray();
		for (int i = 0; i < this.size - 1; i++) {
			sb.append(newArray[i].toString());
			sb.append(", ");
		}
		if (this.size > 0)
			sb.append(newArray[this.size - 1].toString());
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Returns all elements in Hashtable as array.
	 * @return array of elements.
	 * @since 1.0.0.
	 */

	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] newArray = (TableEntry<K, V>[]) new TableEntry[this.size];
		int index = 0;
		for (int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> tempEntry = this.table[i];
			while (tempEntry != null) {
				newArray[index] = tempEntry;
				tempEntry = tempEntry.next;
				index++;
			}
		}
		return newArray;
	}
	
	/**
	 * Removes all elements from Hashtable.
	 * @since 1.0.0.
	 */

	public void clear() {
		this.size = 0;
		for (int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		}
		this.modificationCount++;
	}
	
	/**
	 * Returns table slot for given key.
	 * @param key 
	 * @return slot
	 * @since 1.0.0.
	 */
	
	private int getSlot(Object key) {
		return Math.abs(key.hashCode()) % this.table.length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(this == obj) return true;
		if(!(obj instanceof SimpleHashtable)) return false;
		SimpleHashtable<?, ?> other = (SimpleHashtable<?, ?>) obj;
		return this.size == other.size && Arrays.equals(this.toArray(), other.toArray());
	}
	
	/**
	 * Creates iterator for Hashtable.
	 * @return iterator
	 * @since 1.0.0.
	 */

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Class that represents iterator of Hashtable.
	 * @since 1.0.0.
	 */
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		/**
		 * Number of modifications when iterator was created.
		 * @since 1.0.0.
		 */
		
		private long savedModificationCount;
		
		/**
		 * Current slot.
		 * @since 1.0.0.
		 */
		
		private int currentSlot;
		
		/**
		 * Next element of iterator.
		 * @since 1.0.0.
		 */
		
		private TableEntry<K, V> nextTableEntry;
		
		/**
		 * Last element of iterator.
		 * @since 1.0.0.
		 */
		
		private TableEntry<K, V> lastTableEntry;
		
		/**
		 * Default constructor.
		 * @since 1.0.0.
		 */
		
		private IteratorImpl() {
			this.savedModificationCount = modificationCount;
			this.currentSlot = -1;
			this.nextTableEntry = this.findNextTableEntry();
			this.lastTableEntry = null;
		}
		
		/**
		 * Method that finds next element for iterator.
		 * @return next element for iterator.
		 * @since 1.0.0.
		 */

		private TableEntry<K, V> findNextTableEntry() {
			if(this.lastTableEntry == null || this.lastTableEntry.next == null) {
				this.currentSlot++;
				if(this.currentSlot > (table.length - 1)) return null;
				while(table[this.currentSlot] == null) {
					this.currentSlot++;
					if(this.currentSlot > (table.length - 1)) return null;
				}
				return table[this.currentSlot];
			}
			return lastTableEntry.next;
		}
		
		/**
		 * Checks if Hashtable has more elements to iterate.
		 * @return <code>true</code> if there is more elements; <code>false</code> otherwise
		 * @throws ConcurrentModificationException if modifications were made in meantime of iterator usage.
		 * @since 1.0.0.
		 */

		@Override
		public boolean hasNext() {
			if(this.savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			return this.nextTableEntry != null;
		}
		
		/**
		 * Returns next element of iteration.
		 * @return next element.
		 * @throws ConcurrentModificationException if modifications were made in meantime of iterator usage.
		 * @throws NoSuchElementException if there is no elements to iterate
		 * @since 1.0.0.
		 */

		@Override
		public TableEntry<K, V> next() {
			if(this.savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			if(!this.hasNext()) throw new NoSuchElementException();
			this.lastTableEntry = this.nextTableEntry;
			this.nextTableEntry = this.findNextTableEntry();
			return this.lastTableEntry;
		}
		
		/**
		 * Removes last element of iteration.
		 * @throws ConcurrentModificationException if modifications were made in meantime of iterator usage.
		 * @throws IllegalStateException if last element is removed earlier.
		 * @since 1.0.0.
		 */
		
		@Override
		public void remove() {
			if(this.savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			if(this.lastTableEntry == null) throw new IllegalStateException();
			SimpleHashtable.this.remove(lastTableEntry.getKey());
			this.lastTableEntry = null;
			this.savedModificationCount = modificationCount;
		}
		
	}
}
