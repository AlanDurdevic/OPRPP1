package hr.fer.oprpp1.hw08.jnotepadpp.document;

/**
 * Class that represents listener used for {@link MultipleDocumentModel}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface MultipleDocumentListener {
	
	/**
	 * Method that notifies listeners when current document changed in {@link MultipleDocumentModel}.
	 * @param previousModel previous current document
	 * @param currentModel  new current document
	 * @since 1.0.0.
	 */
	
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method that notifies listeners when document is added in {@link MultipleDocumentModel}.
	 * @param model added document
	 * @since 1.0.0.
	 */
	
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Method that notifies listeners when document is removed in {@link MultipleDocumentModel}.
	 * @param model removed document
	 * @since 1.0.0.
	 */

	void documentRemoved(SingleDocumentModel model);
}
