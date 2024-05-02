package hr.fer.oprpp1.hw08.jnotepadpp.document;

import java.nio.file.Path;
import javax.swing.JTextArea;

/**
 * Represents a model of single document, having information about file path
 * from which document was loaded (can be null for new document), document modification status 
 * and reference to Swing component which is used for editing (each document has its own editor
 * component)
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface SingleDocumentModel {
	
	/**
	 * Method that returns text component of document.
	 * @return text component of document.
	 * @since 1.0.0.
	 */
	
	JTextArea getTextComponent();
	
	/**
	 * Method that returns path of document.
	 * @return path
	 * @since 1.0.0.
	 */

	Path getFilePath();
	
	/**
	 * Method that sets path of document.
	 * @since 1.0.0.
	 */

	void setFilePath(Path path);
	
	/**
	 * Method that shows if document is modified.
	 * @return if document is modified <code>true</code> else <code>false</code>
	 * @since 1.0.0.
	 */

	boolean isModified();
	
	/**
	 * Method that sets modification state for document.
	 * @param modified new modification state
	 * @since 1.0.0.
	 */

	void setModified(boolean modified);
	
	/**
	 * Method for adding listener.
	 * @param l listener
	 * @since 1.0.0.
	 */

	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Method for removing listener.
	 * @param l listener
	 * @since 1.0.0.
	 */

	void removeSingleDocumentListener(SingleDocumentListener l);
	
}
