package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * {@link ListModel} for generating prime numbers.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class PrimListModel implements ListModel<Integer>{
	
	/**
	 * List of prime numbers.
	 * @since 1.0.0.
	 */
	
	private List<Integer> primNumbers;
	
	/**
	 * List of listeners.
	 * @since 1.0.0.
	 */
	
	private List<ListDataListener> listeners;
	
	/**
	 * Default constructor.
	 * @since 1.0.0.
	 */
	
	public PrimListModel() {
		primNumbers = new ArrayList<>();
		primNumbers.add(1);
		listeners = new ArrayList<>();
	}
	
	/**
	 * Method for generating next prime number.
	 * @since 1.0.0.
	 */
	
	public void next() {
		int number = primNumbers.get(primNumbers.size() - 1);
		boolean notFound = true;
		while(notFound) {
			number++;
			notFound = false;
			for(int i = 2; i < number; i++) {
				if(number % i == 0) {
					notFound = true;
					break;
				}
			}	
		}
		primNumbers.add(number);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primNumbers.size() - 1, primNumbers.size() - 1);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public int getSize() {
		return primNumbers.size();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public Integer getElementAt(int index) {
		return primNumbers.get(index);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
