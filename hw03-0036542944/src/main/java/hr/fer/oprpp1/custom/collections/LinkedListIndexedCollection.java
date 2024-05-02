package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class <code>LinkedListIndexedCollection</code> is implementation of 
 * linked list-backed collection of objects.
 * Duplicate elements are allowed, storage of <code>null</code> references it not allowed.
 * @param <T> type of elements in collection.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class LinkedListIndexedCollection<T> implements List<T> {
	
	/**
	 * Private class that represents Node objects of <code>LinkedListIndexedCollection</code>.
	 *@since 1.0.0
	 */
	
	
	private static class ListNode<T> {
		
		/**
		 * Previous <code>ListNode</code>.
		 * @since 1.0.0
		 */
		
		private ListNode<T> previous;
		
		/**
		 * Next <code>ListNode</code>.
		 * @since 1.0.0
		 */
		
		private ListNode<T> next;
		
		/**
		 * Value of <code>ListNode</code>.
		 * @since 1.0.0
		 */
		
		private T value;
	}
	
	/**
	 * Current size of collection (number of elements actually stored in elements
	 * list).
	 * @since 1.0.0
	 */

	private int size;
	
	/**
	 * First <code>ListNode</code> of LinkedListIndexedCollection.
	 * @since 1.0.0
	 */
	
	private ListNode<T> first;
	
	/**
	 * Last <code>ListNode</code> of LinkedListIndexedCollection.
	 * @since 1.0.0
	 */
	
	private ListNode<T> last;
	
	/**
	 * Represents number of modifications on current collection.
	 */
	
	private long modificationCount = 0;
	
	/**
	 * Default constructor.
	 * @since 1.0.0
	 */

	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	/**
	 * Constructor that creates <code>LinkedListIndexedCollection</code> that is copy of
	 * another collection.
	 * @param other other <code>Collection</code> whose elements are copied into this newly constructed collection.
	 * @throws NullPointerException if <code>other</code> is <code>null</code>
	 */

	public LinkedListIndexedCollection(Collection<? extends T> other) {
		this();
		if (other == null)
			throw new NullPointerException();
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
	public void add(T value) {
		if (value == null)
			throw new NullPointerException();
		ListNode<T> newNode = new ListNode<T>();
		newNode.value = value;
		if (this.size == 0) {
			this.first = newNode;
			this.last = newNode;
		} else {
			this.last.next = newNode;
			newNode.previous = this.last;
			this.last = newNode;
		}
		this.modificationCount++;
		this.size++;
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
	
	/**
	 * Removes given Object from the collection.
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
		Object[] newArray = new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			newArray[i] = this.get(i);
		}
		return newArray;
	}
	
	
	/**Removes all elements from this collection.
	 * @since 1.0.0
	 */

	@Override
	public void clear() {
		this.first = this.last = null;
		this.modificationCount++;
		this.size = 0;
	}
	
	/**Returns the object that is stored in list at position index. 
	 * @param index index of desired element
	 * @return Object at desired index
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size of collections
	 * @since 1.0.0
	 */

	@Override
	public T get(int index) {
		if (index < 0 || index > (this.size - 1))
			throw new IndexOutOfBoundsException();
		return this.getNode(index).value;
	}
	
	/**
	 * Inserts the given value at the given position in list.
	 * All elements at greater position are shifted one place toward the end.
	 * @param value value that is to be inserted
	 * @param position position of insertion
	 * @throws NullPointerException if value is <code>null</code>
	 * @throws IndexOutOfBoundsException if position < 0 or position > size
	 * @since 1.0.0
	 */

	@Override
	public void insert(T value, int position) {
		if (value == null)
			throw new NullPointerException();
		if (position < 0 || position > this.size)
			throw new IndexOutOfBoundsException();
		ListNode<T> newNode = new ListNode<T>();
		newNode.value = value;
		if (position == 0 && this.size == 0) {
			this.first = newNode;
			this.last = newNode;
		} else if (position == 0 && this.size != 0) {
			newNode.next = this.first;
			newNode.next.previous = newNode;
			this.first = newNode;
		} else if (position == this.size) {
			newNode.previous = this.last;
			this.last.next = newNode;
			this.last = newNode;
		} else {
			ListNode<T> oldNode = this.getNode(position);
			newNode.previous = oldNode.previous;
			newNode.next = oldNode;
			oldNode.previous.next = newNode;
			oldNode.previous = newNode;
		}
		this.modificationCount++;
		this.size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value.
	 * @param value Object value of index to be returned
	 * @return index of desired value if it is founded, if it is not founded -1
	 * @since 1.0.0
	 */

	@Override
	public int indexOf(Object value) {
		if(value == null) return -1;
		ListNode<T> tempNode = this.first;
		int i = 0;
		while (tempNode != null) {
			if (tempNode.value.equals(value))
				return i;
			tempNode = tempNode.next;
			i++;
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

	@Override
	public void remove(int index) {
		if (index < 0 || index > (size - 1))
			throw new IndexOutOfBoundsException();
		if (index == 0 && size == 1) {
			this.clear();
		} else if (index == 0 && size != 1) {
			this.first = this.first.next;
			this.first.previous = null;
		} else if (index == (size - 1)) {
			this.last = this.last.previous;
			this.last.next = null;
		} else {
			ListNode<T> tempNode = this.getNode(index);
			tempNode.previous.next = tempNode.next;
			tempNode.next.previous = tempNode.previous;
		}
		this.modificationCount++;
		this.size--;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if (!(obj instanceof LinkedListIndexedCollection))
			return false;
		LinkedListIndexedCollection<?> other = (LinkedListIndexedCollection<?>) obj;
		if(this.size == 0 && other.size == 0) return true;
		Object[] thisArray = this.toArray();
		Object[] otherArray = other.toArray();
		if(!Arrays.equals(thisArray, otherArray)) return false;
		return this.first.value.equals(other.first.value) && this.last.value.equals(other.last.value);
	}
	
	/**
	 * Returns <code>ListNode</code> at specified index.
	 * @param index of desired Node
	 * @return <code>ListNode</code> at specified index in list.
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size
	 * @since 1.0.0
	 */

	private ListNode<T> getNode(int index) {
		if (index < 0 || index > (size - 1))
			throw new IndexOutOfBoundsException();
		ListNode<T> node;
		int i;
		if (index <= (this.size - index)) {
			node = this.first;
			i = 0;
			while (i != index) {
				node = node.next;
				i++;
			}
		} else {
			node = this.last;
			i = this.size - 1;
			while (i != index) {
				node = node.previous;
				i--;
			}
		}
		return node;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LinkedListIndexedElementsGetter<T>(this);
	}
	
	/**
	 * Privates class that represents {@link ElementsGetter} for
	 * {@link LinkedListIndexedCollection}.
	 * @since 1.0.0.
	 */
	
	private static class LinkedListIndexedElementsGetter<T> implements ElementsGetter<T> {
		
		/**
		 * Current <code>ListNode</code> of this <code>ElementsGetter</code>
		 */
		
		private ListNode<T> currentNode;
		
		/**
		 * Collection of <code>ElementsGetter</code>
		 */
		
		private LinkedListIndexedCollection<T> collection;
		
		/**
		 * Number of starting modifications.
		 */
		
		private long savedModificationCount;
		
		/**
		 * Constructor for <code>ElementsGetter</code>.
		 * @param collection reference to wanted collection.
		 * @throws NullPointerException if <code>collection</code> is <code>null</code>
		 */
		
		private LinkedListIndexedElementsGetter(LinkedListIndexedCollection<T> collection) {
			if(collection == null) throw new NullPointerException();
			this.collection = collection;
			this.currentNode = collection.first;
			this.savedModificationCount = collection.modificationCount;
		}
		
		/**
		 * @throws ConcurrentModificationException if current number of modifications is
		 * higher than starting number of modifications.
		 */

		@Override
		public boolean hasNextElement() {
			if(this.savedModificationCount != collection.modificationCount)
				throw new ConcurrentModificationException();
			return this.currentNode != null;
		}
		
		/**
		 * @throws ConcurrentModificationException if current number of modifications is
		 * higher than starting number of modifications.
		 * @throws NoSuchElementException if there is no more elements.
		 * @since 1.0.0.
		 */

		@Override
		public T getNextElement() {
			if(this.hasNextElement()) {
				ListNode<T> tempNode = this.currentNode;
				this.currentNode = this.currentNode.next;
				return tempNode.value;
			}
			throw new NoSuchElementException();
		}
		
	}

}
