package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * Class <code>ArrayIndexedCollection</code> is implementation of resizable
 * array-backed collection of objects.
 * Duplicate elements are allowed, storage of <code>null</code> references it not allowed.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class ArrayIndexedCollection extends Collection {

	/**
	 * Current size of collection (number of elements actually stored in elements
	 * array).
	 * @since 1.0.0
	 */

	private int size;

	/**
	 * An array of object references which length determines its current capacity.
	 * @since 1.0.10
	 */

	private Object[] elements;

	/**
	 * Static variable that defines default capacity if it is not given.
	 * @since 1.0.0
	 */

	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Default constructor.
	 * @since 1.0.0
	 */

	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor with initialCapacity parameter.
	 * @param initialCapacity maximum initial length of array
	 * @throws IllegalArgumentException if initialCapacity < 1
	 * @since 1.0.0
	 */

	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException();
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}

	/**
	 * Constructor that creates <code>ArrayIndexedCollection</code> that is copy of
	 * another collection. 
	 * @param other another collection
	 * @throws NullPointerException if <code>other</code> is null
	 * @since 1.0.0
	 */

	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that creates <code>ArrayIndexedCollection</code> that is copy of
	 * another collection and has defined initialCapacity. If capacity > 16 then it
	 * is used. 
	 * @param other another collection
	 * @param initialCapacity initialCapacity
	 * @throws NullPointerException     if <code>other</code> is null
	 * @throws IllegalArgumentException if initialCapacity < 1
	 * @since 1.0.0
	 */

	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null)
			throw new NullPointerException();
		if (initialCapacity < 1)
			throw new IllegalArgumentException();
		if (initialCapacity < other.size()) {
			this.elements = new Object[other.size()];
		} else {
			this.elements = new Object[initialCapacity];
		}
		this.addAll(other);
	}
	

	/**Returns the number of currently stored objects in this collections.
	 * @since 1.0.0
	 */

	@Override
	public int size() {
		return this.size;
	}

	/**Adds the given object into this collection.
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 * @since 1.0.0
	 */

	@Override
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException("Can not add null element into collection!");
		if (this.size == this.elements.length)
			this.reallocateArray();
		this.elements[this.size] = value;
		this.size++;
	}

	/**
	 * Method that resizes (doubling its size) array if it is too small.
	 * @since 1.0.0
	 */

	private void reallocateArray() {
		Object[] temp = this.elements.clone();
		elements = new Object[size * 2];
		for (int i = 0; i < temp.length; i++) {
			this.elements[i] = temp[i];
		}
	}
	
	/**Method that checks if the Object is in the collection.
	 * @since 1.0.0
	 */

	@Override
	public boolean contains(Object value) {
		if (this.indexOf(value) >= 0) {
			return true;
		}
		return false;
	}

	/**Removes given Object from the collection.
	 * @since 1.0.0
	 */
	
	@Override
	public boolean remove(Object value) {
		if (this.contains(value)) {
			this.remove(this.indexOf(value));
			return true;
		}
		return false;
	}
	
	/**Method that creates new Array of this collection.
	 * @since 1.0.0
	 */

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(this.elements, this.size);
	}

	/**Method adds into the current collection all elements from the given collection.
	 * @throws NullPointerException if <code>processor</code> is <code>null</code>
	 * @since 1.0.0
	 */
	
	@Override
	public void forEach(Processor processor) {
		if (processor == null)
			throw new NullPointerException();
		for (int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}
	
	/**Removes all elements from this collection.
	 * @since 1.0.0
	 */

	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	/**Returns the object that is stored in backing array at position index. 
	 * @param index index of desired element
	 * @return Object at desired index
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size of collections
	 * @since 1.0.0
	 */

	public Object get(int index) {
		if (index < 0 || index > (size - 1))
			throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * Inserts the given value at the given position in array.
	 * All elements at greater position are shifted one place toward the end.
	 * @param value value that is to be inserted
	 * @param position position of insertion
	 * @throws NullPointerException if value is <code>null</code>
	 * @throws IndexOutOfBoundsException if position < 0 or position > size
	 * @since 1.0.0
	 */

	public void insert(Object value, int position) {
		if (value == null)
			throw new NullPointerException();
		if (position < 0 || position > this.size)
			throw new IndexOutOfBoundsException();
		if (position != this.size) {
			Object[] temp = Arrays.copyOfRange(this.elements, position, this.size);
			int startingSize = this.size;
			for (int i = position; i < startingSize; i++) {
				this.remove(position);
			}
			this.elements[position] = value;
			this.size++;
			for (int i = 0; i < temp.length; i++) {
				this.add(temp[i]);
			}
		} else
			this.add(value);
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the given value.
	 * @param value Object value of index to be returned
	 * @return index of desired value if it is founded, if it is not founded -1
	 * @since 1.0.0
	 */
	
	public int indexOf(Object value) {
		if(value == null) return -1;
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value))
				return i;
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection.
	 * Element that was previously at location index+1 after this operation is on location index, etc.
	 * @param index index of element to be removed
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size
	 * @since 1.0.0
	 */

	public void remove(int index) {
		if (index < 0 || index > (this.size - 1))
			throw new IndexOutOfBoundsException();
		this.elements[index] = null;
		this.organiseElementsAfterRemoval(index);
		this.size--;
	}
	
	/**
	 * Shifts all elements whose positions are index + 1 one place towards beginning.
	 * @param index index of starting
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size
	 * @since 1.0.0
	 */

	private void organiseElementsAfterRemoval(int index) {
		if (index < 0 || index > (this.size - 1))
			throw new IndexOutOfBoundsException();
		for (int i = index; i < (this.size - 1); i++) {
			this.elements[i] = this.elements[i + 1];
		}
		this.elements[size - 1] = null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ArrayIndexedCollection)) return false;
		ArrayIndexedCollection testCollection = (ArrayIndexedCollection) obj;
		return Arrays.equals(this.toArray(), testCollection.toArray());
	}
	
}
