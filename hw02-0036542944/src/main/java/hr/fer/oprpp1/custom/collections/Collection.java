package hr.fer.oprpp1.custom.collections;

/**Interface <code>Collection</code> is interface that represents some general methods
 * for collection of objects.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public interface Collection {
	
	/**
	 * Method that checks if <code>Collection</code> is empty.
	 * @return <code>true</code> if collection contains no objects;
	 * <code>false</code> if collection contains at least one object
	 * @since 1.0.0
	 */

	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**Returns the number of currently stored objects in this collections.
	 * @return number of stored Objects
	 * @since 1.0.0
	 */

	int size();

	
	/**
	 * Adds the given object into this collection.
	 * @param value Object given to be stored.
	 * @since 1.0.0
	 */

	void add(Object value);

	
	/**
	 * Method that checks if the Object is in the collection.
	 * @param value Object that needs to be checked.
	 * @return <code>true</code> if object is in the collection;
	 * <code>false</code> if object is not in the collection
	 * @since 1.0.0
	 */

	boolean contains(Object value);

	
	/**
	 * Removes given Object from the collection.
	 * @param value Object that is to be removed.
	 * @return <code>true</code> if collection contains desired Object;
	 * <code>false</code> if collection does not contain desired Object
	 * @since 1.0.0
	 */

	boolean remove(Object value);
	
	
	/**
	 * Method that creates new Array of this collection.
	 * @return new Array of collection
	 * @since 1.0.0
	 */

	Object[] toArray();
	
	
	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * @param processor Processor that processes collection elements.
	 * @since 1.0.0
	 */

	default void forEach(Processor processor) {
		if(processor == null) throw new NullPointerException();
		ElementsGetter elementsGetter = this.createElementsGetter();
		while(elementsGetter.hasNextElement()) {
			processor.process(elementsGetter.getNextElement());
		}
	}

	/**
	 * Method adds into the current collection all elements from the given collection.
	 * @param other Collection that contains elements to be added.
	 * @throws NullPointerException if other <code>other</code> is null.
	 * @since 1.0.0
	 */

	default void addAll(Collection other) {
		if(other == null) throw new NullPointerException();
		
		/** Class <code>AddNewElementsProcessor</code> is local class that
		 * creates an object that is capable of adding passed object to <code>Collection</code>.
		 * @since 1.0.0
		 */
		
		class AddNewElementsProcessor implements Processor {

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
	 * Removes all elements from this collection.
	 * @since 1.0.0.
	 */
	
	void clear();
	
	/**
	 * Method that creates new <code>ElementsGetter</code> of this collection.
	 * @return <code>ElementsGetter</code> of collection.
	 * @since 1.0.0.
	 */
	
	ElementsGetter createElementsGetter();
	
	/**
	 * Method that adds all elements from other collection that satisfies given tester.
	 * @param col other collection
	 * @param tester given tester
	 * @throws NullPointerException if <code>col</code> or <code>tester</code> is <code>null</code>
	 * @since 1.0.0.
	 */
	
	default void addAllSatisfying(Collection col, Tester tester) {
		if(col == null || tester == null) throw new NullPointerException();
		ElementsGetter elementsGetter = col.createElementsGetter();
		while(elementsGetter.hasNextElement()) {
			Object element = elementsGetter.getNextElement();
			if(tester.test(element)) {
				this.add(element);
			}
		}
	}


}
