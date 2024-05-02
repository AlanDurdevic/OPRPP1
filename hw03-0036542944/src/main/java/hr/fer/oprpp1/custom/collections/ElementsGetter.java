package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface <code>ElementsGetter</code> is interface that provides methods for
 * retrieving Object from collections on user's demand.
 * @param <T> type of retrieved elements.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface ElementsGetter<T> {
	
	/**
	 * Checks if there is next element in collection.
	 * @return <code>true</code> if collection has next element; <code>false</code>
	 * otherwise
	 * @since 1.0.0
	 */
	
	boolean hasNextElement();
	
	/**
	 * Returns next element from collection.
	 * @return next element from collection.
	 * @throws NoSuchElementException if there is no element in collection.
	 * @since 1.0.0.
	 */
	
	T getNextElement();
	
	/**
	 * Processes the remaining elements from collection (elements given from
	 * <code>getNextElement</code> method).
	 * @param p desired processor
	 * @throws NullPointerException if given processor is null.
	 * @since 1.0.0.
	 */
	
	default void processRemaining(Processor<? super T> p) {
		if(p == null) throw new NullPointerException();
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}

}
