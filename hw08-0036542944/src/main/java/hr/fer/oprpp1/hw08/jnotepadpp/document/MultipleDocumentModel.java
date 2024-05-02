package hr.fer.oprpp1.hw08.jnotepadpp.document;

import java.nio.file.Path;
import javax.swing.JComponent;

/**
 * Represents a model capable of holding zero, one or more documents,
 * where each document and having a concept of current document – the one which is shown to the
 * user and on which user works.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Method that return graphical component which is responsible for displaying
	 * entire {@link MultipleDocumentModel}'s user interface.
	 * @return graphical component.
	 * @since 1.0.0.
	 */
	
	JComponent getVisualComponent();
	
	/**
	 * Method that creates new document in {@link MultipleDocumentModel}.
	 * @return new document represented as {@link SingleDocumentModel} object
	 * @since 1.0.0.
	 */

	SingleDocumentModel createNewDocument();
	
	/**
	 * Method that return document that is currently active in {@link MultipleDocumentModel}.
	 * @return document that is currently active represented as {@link SingleDocumentModel}.
	 * @since 1.0.0.
	 */

	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Method that loads existing document to {@link MultipleDocumentModel}.
	 * @param path path of existing document to be loaded
	 * @return loaded document represented as {@link SingleDocumentModel}
	 * @since 1.0.0.
	 */

	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Method that saves given document on new path.
	 * If <code>newPath</code> is <code>null</code>, document should be saved using path
	 * associated from document.
	 * Document's path is updated to <code>newPath</code> if <code>newPath</code> is not <code>null</code>.
	 * @param model {@link SingleDocumentModel} of document to be saved
	 * @param newPath new path of document to be saved
	 * @since 1.0.0.
	 */

	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Method that closes given {@link SingleDocumentModel}.
	 * @param model {@link SingleDocumentModel} of document to be closed.
	 * @since 1.0.0.
	 */

	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Method for adding {@link MultipleDocumentListener} to {@link MultipleDocumentModel}.
	 * @param l listener to be added
	 * @since 1.0.0.
	 */

	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Method for removing {@link MultipleDocumentListener} to {@link MultipleDocumentModel}.
	 * @param l listener to be removed
	 * @since 1.0.0.
	 */

	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Method that gives current number of stored documents in {@link MultipleDocumentModel}.
	 * @return current number of stored documents
	 * @since 1.0.0.
	 */

	int getNumberOfDocuments();
	
	/**
	 * Method that returns document from {@link MultipleDocumentModel} at desired index.
	 * @param index desired index
	 * @return {@link SingleDocumentModel} representation of document on desired index 
	 * @since 1.0.0.
	 */

	SingleDocumentModel getDocument(int index);
	
	/**
	 * Method that returns document from {@link MultipleDocumentModel} with desired <code>path</code> value.
	 * @param path desired path
	 * @return {@link SingleDocumentModel} representation of document with desired <code>path</code> if such model exists;
	 * otherwise <code>null</code>
	 * @since 1.0.0.
	 */

	SingleDocumentModel findForPath(Path path);
	
	/**
	 * Method that returns index of {@link SingleDocumentModel} stored in {@link MultipleDocumentModel}.
	 * @param doc desired document
	 * @return index of stored {@link SingleDocumentModel} if is present; otherwise -1
	 * @since 1.0.0.
	 */

	int getIndexOfDocument(SingleDocumentModel doc);
}
