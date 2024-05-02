package hr.fer.oprpp1.custom.collections;

	/**Interface <code>Processor</code> is interface that provides method
	 * for processing Objects.
	 * @param <T> type of processed objects.
	 * @author Alan Đurđević
	 * @version 1.0.0
	 */

public interface Processor<T> {
	
	/** Method that processes given Object.
	 * @param value Object that is processed.
	 * @since 1.0.0
	 */
	
	void process(T value);

}
