package hr.fer.oprpp1.hw08.jnotepadpp.document;

/**
 * Class that represents listener used for {@link SingleDocumentModel}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface SingleDocumentListener {
	
	/**
	 * Method that notifies all listeners if document modification status changed.
	 * @param model model of document which modification status changed
	 * @since 1.0.0.
	 */
	
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Method that notifies all listeners if document path changed.
	 * @param model model of document which path changed
	 * @since 1.0.0.
	 */

	void documentFilePathUpdated(SingleDocumentModel model);
	
}
