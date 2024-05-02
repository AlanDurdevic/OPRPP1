package hr.fer.oprpp1.custom.collections;

/** Class <code>Collection</code> is class that represents some general
 * collection of objects.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class Collection {
	
	/**
	 * Default constructor for class <code>Collection</code>
	 * @since 1.0.0
	 */

	protected Collection() {

	}
	
	/**
	 * Method that checks if <code>Collection</code> is empty.
	 * @return <code>true</code> if collection contains no objects;
	 * <code>false</code> if collection contains at least one object
	 * @since 1.0.0
	 */

	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**Returns the number of currently stored objects in this collections. Here it always
	 * return 0. Needs to be Override.
	 * @return number of stored Objects
	 * @since 1.0.0
	 */

	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection.
	 * @param value Object given to be stored. Here does nothing. Needs to be Override.
	 * @since 1.0.0
	 */

	public void add(Object value) {

	}
	
	/**
	 * Method that checks if the Object is in the collection. Here always returns false.
	 * Needs to be Override.
	 * @param value Object that needs to be checked.
	 * @return <code>true</code> if object is in the collection;
	 * <code>false</code> if object is not in the collection
	 * @since 1.0.0
	 */

	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes given Object from the collection. Here always returns false.
	 * Needs to be Override.
	 * @param value Object that is to be removed.
	 * @return <code>true</code> if collection contains desired Object;
	 * <code>false</code> if collection does not contain desired Object
	 * @since 1.0.0
	 */

	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Method that creates new Array of this collection. Here always throws
	 * <code>UnsupportedOperationException</code>. Needs to be Override.
	 * @return new Array of collection
	 * @since 1.0.0
	 */

	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process(.) for each element of this collection. Here does
	 * nothing. Needs to be Override.
	 * @param processor Processor that processes collection elements.
	 * @since 1.0.0
	 */

	public void forEach(Processor processor) {

	}
	
	/**
	 * Method adds into the current collection all elements from the given collection.
	 * @param other Collection that contains elements to be added.
	 * @throws NullPointerException if other <code>other</code> is null.
	 * @since 1.0.0
	 */

	public void addAll(Collection other) {
		if(other == null) throw new NullPointerException();
		
		/** Class <code>AddNewElementsProcessor</code> is local class that
		 * creates an object that is capable of adding passed object to <code>Collection</code>.
		 * @since 1.0.0
		 */
		
		class AddNewElementsProcessor extends Processor {

			/**Method that adds given Object to collection.
			 * @param value Object to be added.
			 * @since 1.0.0
			 */
			
			public void process(Object value) {
				add(value);
			}

		}
		
		other.forEach(new AddNewElementsProcessor());
		
	}

	/**
	 * Removes all elements from this collection. Here does nothing.
	 * Needs to be Override.
	 * @since 1.0.0
	 */
	
	public void clear() {

	}

}
