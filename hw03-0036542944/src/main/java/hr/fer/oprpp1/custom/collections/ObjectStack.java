package hr.fer.oprpp1.custom.collections;

/**
 * Class <code>ObjectStack</code> is implementation of stack.
 * @param <T> type of objects in stack.
 * @author Alan Đurđević
 * @version 1.0.0
 */

public class ObjectStack<T> {
	
	/**
	 * Array that is use to store Objects.
	 * @since 1.0.0
	 */
	
	private ArrayIndexedCollection<T> arrayIndexedCollection;
	
	/**
	 * Default constructor.
	 * @since 1.0.0
	 */
	
	public ObjectStack() {
		this.arrayIndexedCollection = new ArrayIndexedCollection<T>();
	}
	
	/**
	 * Method that checks if <code>stack</code> is empty.
	 * @return <code>true</code> if <code>stack</code> contains no objects;
	 * <code>false</code> if <code>stack</code> contains at least one object.
	 * @since 1.0.0
	 */
	
	public boolean isEmpty() {
		return this.arrayIndexedCollection.isEmpty();
	}
	
	/**Returns the number of currently stored objects in this <code>stack</code>.
	 * @return number of stored Objects
	 * @since 1.0.0
	 */
	
	public int size() {
		return this.arrayIndexedCollection.size();
	}
	
	/**Pushes given value on the stack.
	 * @param value value to be pushed.
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 * @since 1.0.0
	 */
	
	public void push(T value) {
		if(value == null) throw new NullPointerException();
		this.arrayIndexedCollection.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * @return last value from stack
	 * @throws EmptyStackException if stack is empty
	 * @since 1.0.0
	 */
	
	public T pop() {
		if(this.isEmpty()) throw new EmptyStackException();
		T popObject = this.arrayIndexedCollection.get(this.size() - 1);
		this.arrayIndexedCollection.remove(this.size() - 1);
		return popObject;
	}
	
    /**
     * Returns last element placed on stack but does not delete it from stack. 
     * @return last value from stack
     * @throws EmptyStackException if stack is empty
	 * @since 1.0.0
     */
	
	public T peek() {
		if(this.isEmpty()) throw new EmptyStackException();
		return this.arrayIndexedCollection.get(this.size() - 1);
	}
	
	/**
	 * Removes all elements from stack.
	 * @since 1.0.0
	 */
	
	public void clear() {
		this.arrayIndexedCollection.clear();
	}

}
