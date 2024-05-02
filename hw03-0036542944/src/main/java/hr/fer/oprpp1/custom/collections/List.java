package hr.fer.oprpp1.custom.collections;

/**
 * Interface that provides some more methods for {@link Collection}.
 * @param <T> type of elements in list.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface List<T> extends Collection<T>{
	
	/**Returns the object that is stored in list at position index. 
	 * @param index index of desired element
	 * @return Object at desired index
	 * @since 1.0.0
	 */
	
	T get(int index);
	
	/**
	 * Inserts the given value at the given position in list.
	 * All elements at greater position are shifted one place toward the end.
	 * @param value value that is to be inserted
	 * @param position position of insertion
	 * @since 1.0.0
	 */
	
	void insert(T value, int position);
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value.
	 * @param value Object value of index to be returned
	 * @return index of desired value if it is founded, if it is not founded -1
	 * @since 1.0.0
	 */
	
	int indexOf(Object value);
	
	/**
	 * Removes element at specified index from collection.
	 * Element that was previously at location index+1 after this operation is on location index, etc.
	 * @param index index of element to be removed
	 * @since 1.0.0
	 */
	
	void remove(int index);

}
