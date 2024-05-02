package hr.fer.oprpp1.hw08.jnotepadpp.document.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.SingleDocumentModel;

/**
 * Class that represents default implementation of {@link SingleDocumentModel}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	/**
	 * Path of document.
	 * @since 1.0.0.
	 */

	private Path path;
	
	/**
	 * Document text before modification.
	 * @since 1.0.0.
	 */

	private String text;
	
	/**
	 * Text area used for document.
	 * @since 1.0.0.
	 */

	private JTextArea textArea;
	
	/**
	 * Boolean that shows if document is modified.
	 * @since 1.0.0.
	 */

	private boolean modified;
	
	/**
	 * List of {@link SingleDocumentListener}.
	 * @since 1.0.0.
	 */

	private List<SingleDocumentListener> listeners;
	
	/**
	 * Default constructor with <code>path</code> and <code>text</code> parameter.
	 * @param path path of file
	 * @param text text of file
	 * @since 1.0.0.
	 */

	public DefaultSingleDocumentModel(Path path, String text) {
		this.path = path == null ? path : path.toAbsolutePath().normalize();
		this.text = text == null ? "" : text;
		textArea = new JTextArea(text);
		listeners = new ArrayList<>();
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			/**
			 * {@inheritDoc}
			 * @since 1.0.0.
			 */

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(!DefaultSingleDocumentModel.this.text.equals(textArea.getText()));
			}
			
			/**
			 * {@inheritDoc}
			 * @since 1.0.0.
			 */

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(!DefaultSingleDocumentModel.this.text.equals(textArea.getText()));
			}
			
			/**
			 * {@inheritDoc}
			 * @since 1.0.0.
			 */

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(!DefaultSingleDocumentModel.this.text.equals(textArea.getText()));
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public Path getFilePath() {
		return path;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>path</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	@Override
	public void setFilePath(Path path) {
		this.path = Objects.requireNonNull(path, "Path can not be null!");
		listeners.forEach((l) -> l.documentFilePathUpdated(this));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public boolean isModified() {
		return modified;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		if (!modified)
			text = textArea.getText();
		listeners.forEach((l) -> l.documentModifyStatusUpdated(this));
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>l</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l, "Listener can not be null!"));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
