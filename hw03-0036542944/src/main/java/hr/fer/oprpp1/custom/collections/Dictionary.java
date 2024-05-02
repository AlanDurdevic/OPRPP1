package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Implementation of key-value set. One key represents one value (duplicate keys can not exist).
 * @param <K> key type
 * @param <V> value type
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Dictionary<K, V> {
	
	/**
	 * Class that represents Pair of one key and one value.
	 * @param <K> key type
	 * @param <V> value type
	 * @since 1.0.0.
	 */
	
	private class Pair{
		
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
		 * Default constructor with key and value parameters.
		 * @param key key
		 * @param value value
		 * @throws NullPointerException if <code>key</code> is <code>null</code>
		 * @since 1.0.0.
		 */
		
		Pair(K key, V value){
			if(key == null) throw new NullPointerException();
			this.key = key;
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			if(!(obj instanceof Dictionary.Pair)) return false;
			Pair other = (Pair) obj;
			return this.key.equals(other.key) && Objects.equals(this.value, other.value);
		}

	}
	
	/**
	 * Collection of stored pairs in dictionary.
	 * @since 1.0.0.
	 */
	
	private ArrayIndexedCollection<Pair> collection;
	
	/**
	 * Default constructor.
	 * @since 1.0.0.
	 */
	
	public Dictionary() {
		this.collection = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Checks if dictionary is empty.
	 * @return <code>true</code> if it is empty; <code>false</code> otherwise.
	 * @since 1.0.0.
	 */
	
	public boolean isEmpty() {
		return this.collection.isEmpty();
	}
	
	/**
	 * Returns number of elements in dictionary.
	 * @return number of elements.
	 * @since 1.0.0.
	 */
	
	public int size() {
		return this.collection.size();
	}
	
	/**
	 * Removes all elements from dictionary.
	 * @since 1.0.0.
	 */
	
	public void clear() {
		this.collection.clear();
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
		if(key == null) throw new NullPointerException();
		int index = this.indexOfKey(key);
		if(index == -1) {
			this.collection.add(new Pair(key, value));
			return null;
		}
		Pair pair = this.collection.get(index);
		V oldValue = pair.value;
		pair.value = value;
		return oldValue;
	}
	
	/**
	 * Return value of desired key.
	 * @param key desired key
	 * @return value of desired key if key exists; otherwise <code>null</code>
	 * @since 1.0.0.
	 */
	
	public V get(Object key) {
		int index = this.indexOfKey(key);
		if(index == -1) return null;
		return this.collection.get(index).value;
	}
	
	/**
	 * Removes pair of key-value and returns old value.
	 * @param key desired key.
	 * @return old value if key exists; otherwise <code>null</code>
	 * @since 1.0.0.
	 */
	
	public V remove(K key) {
		int index = this.indexOfKey(key);
		if(index == -1) return null;
		V value = this.collection.get(index).value;
		this.collection.remove(index);
		return value;
	}
	
	/**
	 * Returns index of desired key.
	 * @param key desired key
	 * @return index of key if it exist; otherwise <code>null</code>
	 * @since 1.0.0.
	 */
	
	private int indexOfKey(Object key) {
		if(key == null) return -1;
		for(int i = 0; i < this.collection.size(); i++) {
			if(this.collection.get(i).key.equals(key)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Dictionary)) return false;
		Dictionary<?, ?> other = (Dictionary<?, ?>) obj;
		return this.collection.equals(other.collection);
	}

}
