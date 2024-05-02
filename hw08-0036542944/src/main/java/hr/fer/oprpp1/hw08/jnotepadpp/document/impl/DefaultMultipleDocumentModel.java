package hr.fer.oprpp1.hw08.jnotepadpp.document.impl;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.document.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;

/**
 * Class that represents default implementation of {@link MultipleDocumentModel}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link List} that stores all {@link SingleDocumentModel} of this {@link MultipleDocumentModel}.
	 * @since 1.0.0.
	 */

	private List<SingleDocumentModel> documents;
	
	/**
	 * Current document in this {@link MultipleDocumentModel}.
	 * @since 1.0.0.
	 */

	private SingleDocumentModel currentDocument;
	
	/**
	 * {@link List} that stores all {@link SingleDocumentListener} of this {@link MultipleDocumentModel}.
	 * @since 1.0.0.
	 */

	private List<MultipleDocumentListener> listeners;
	
	/**
	 * {@link FormLocalizationProvider} used for this {@link MultipleDocumentModel}.
	 * @since 1.0.0.
	 */

	private FormLocalizationProvider flp;
	
	/**
	 * Icon used for non-modified documents.
	 * @since 1.0.0.
	 */

	private ImageIcon greenIcon;
	
	/**
	 * Icon used for modified documents.
	 * @since 1.0.0.
	 */

	private ImageIcon redIcon;
	
	/**
	 * Constructor with {@link FormLocalizationProvider} parameter.
	 * @param flp {@link FormLocalizationProvider} used for this {@link MultipleDocumentModel}.
	 * @throws NullPointerException if <code>flp</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	public DefaultMultipleDocumentModel(FormLocalizationProvider flp) {
		documents = new ArrayList<>();
		currentDocument = null;
		listeners = new ArrayList<>();
		this.flp = Objects.requireNonNull(flp, "FormLocalizationProvider can not be null!");
		greenIcon = loadIcon("../../icons/GreenIcon.png");
		redIcon = loadIcon("../../icons/RedIcon.png");
		//listeners for changing tab titles based on localization that has no associated path
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				for (int i = 0; i < documents.size(); i++) {
					if (documents.get(i).getFilePath() == null) {
						setTitleAt(0, "(" + flp.getString("unnamed") + ")");
					}
				}
			}
		});
		//listener for changing currentDocument when selected tab changes
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel previousDocument = currentDocument;
				int index = getSelectedIndex();
				currentDocument = index == -1 ? null : getDocument(index);
				listeners.forEach((l) -> l.currentDocumentChanged(previousDocument, currentDocument));
			}
		});
	}
	
	/**
	 * Private method for loading icons associated with given path.
	 * @param path given path
	 * @return loaded {@link ImageIcon}
	 * @since 1.0.0.
	 */

	private ImageIcon loadIcon(String path) {
		Image icon = null;
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) {
				JNotepadPP.notifyUser(flp, getVisualComponent(), Paths.get(path), flp.getString("iconError"));
			}
			byte[] bytes = is.readAllBytes();
			icon = new ImageIcon(bytes).getImage();
		} catch (IOException e) {
			JNotepadPP.notifyUser(flp, getVisualComponent(), Paths.get(path), flp.getString("iconError"));
		}
		return new ImageIcon(icon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public JComponent getVisualComponent() {
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel previousModel = currentDocument;
		currentDocument = new DefaultSingleDocumentModel(null, null);
		addNewTab();
		listeners.forEach((l) -> l.currentDocumentChanged(previousModel, currentDocument));
		return currentDocument;
	}
	
	/**
	 * Method that adds new tab with appropriate parameters (title, tip...).
	 * @since 1.0.0.
	 */

	private void addNewTab() {
		documents.add(currentDocument);
		Path path = currentDocument.getFilePath();
		insertTab(path == null ? "(" + flp.getString("unnamed") + ")" : path.getFileName().toString(),
				currentDocument.isModified() ? redIcon : greenIcon,
				new JPanel().add(new JScrollPane(currentDocument.getTextComponent())),
				path == null ? "(" + flp.getString("unnamed") + ")" : path.toString(), documents.size() - 1);
		//listener used for selecting icons based on document modification status and title and tool tip based on path updating
		currentDocument.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified())
					setIconAt(getSelectedIndex(), redIcon);
				else
					setIconAt(getSelectedIndex(), greenIcon);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toString());
			}
		});
		setSelectedIndex(getIndexOfDocument(currentDocument));
		listeners.forEach((l) -> l.documentAdded(currentDocument));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Path loadedPath = path.toAbsolutePath().normalize();
		SingleDocumentModel loadedDocument = null;
		SingleDocumentModel previousDocument = currentDocument;
		boolean contains = false;
		for (SingleDocumentModel document : documents) {
			if (document.getFilePath() != null && document.getFilePath().equals(loadedPath)) {
				loadedDocument = document;
				contains = true;
				break;
			}
		}
		if (contains) {
			setSelectedIndex(getIndexOfDocument(loadedDocument));
		} else {
			byte[] documentBytes = null;
			try {
				documentBytes = Files.readAllBytes(loadedPath);
				loadedDocument = new DefaultSingleDocumentModel(loadedPath,
						new String(documentBytes, StandardCharsets.UTF_8));
				currentDocument = loadedDocument;
				addNewTab();
				setSelectedIndex(getIndexOfDocument(currentDocument));
				listeners.forEach((l) -> l.currentDocumentChanged(previousDocument, currentDocument));
			} catch (IOException exc) {
				JNotepadPP.notifyUser(flp, this, path, flp.getString("loadingError"));
			}
		}
		return loadedDocument;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>mode</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model, "Model can not be null!");
		if (newPath == null) {
			newPath = model.getFilePath();
		}
		newPath = newPath.toAbsolutePath().normalize();
		try {
			Files.writeString(newPath, model.getTextComponent().getText());
			model.setFilePath(newPath);
			model.setModified(false);
			listeners.forEach((l) -> l.currentDocumentChanged(model, this.currentDocument));
		} catch (IOException e) {
			JNotepadPP.notifyUser(flp, this, newPath, flp.getString("savingError"));
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (documents.contains(model)) {
			SingleDocumentModel previousDocument = currentDocument;
			int selectedIndex = getIndexOfDocument(currentDocument);
			documents.remove(model);
			removeTabAt(selectedIndex);
			listeners.forEach((l) -> l.currentDocumentChanged(previousDocument, currentDocument));
			listeners.forEach((l) -> l.documentRemoved(model));
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>l</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l, "Listener can not be null!"));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if <code>index</code> is greater than number of stored document or negative.
	 * @since 1.0.0.
	 */

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 && index >= documents.size())
			throw new IllegalArgumentException("Invalid index!");
		return documents.get(index);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>path</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	@Override
	public SingleDocumentModel findForPath(Path path) {
		Objects.requireNonNull(path, "Path can not be null");
		for (SingleDocumentModel d : documents) {
			if (d.getFilePath() != null && d.getFilePath().equals(path)) {
				return d;
			}
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		for (int i = 0; i < documents.size(); i++) {
			if (documents.get(i).equals(doc)) {
				return i;
			}
		}
		return -1;
	}

}
