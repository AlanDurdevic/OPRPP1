package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.JButton;
import hr.fer.oprpp1.hw08.jnotepadpp.document.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.impl.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJToolBar;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import javax.swing.JMenu;

/**
 * Class that represents basic text editor application. It provides to user
 * functionalities like open document, close document, sort... It is localized
 * for 4 languages: English, Croatian, German and French.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class JNotepadPP extends JFrame {

	/**
	 * Class that represents clock for {@link JNotepadPP}.
	 * 
	 * @since 1.0.0.
	 */

	private class Clock {

		/**
		 * Variable that represents current date and time.
		 * 
		 * @since 1.0.0.
		 */
		
		private volatile String time;

		/**
		 * Boolean that shows if clock is request to stop updating time.
		 * 
		 * @since 1.0.0.
		 */

		private volatile boolean stopRequested;

		/**
		 * Instance of {@link DateFormatter}.
		 * 
		 * @since 1.0.0.
		 */

		private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		/**
		 * Default constructor.
		 * 
		 * @since 1.0.0.
		 */

		public Clock() {
			updateTime();

			Thread t = new Thread(() -> {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (Exception ex) {
					}
					if (stopRequested)
						break;
					SwingUtilities.invokeLater(() -> {
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}

		/**
		 * Method to set clock to stop updating time.
		 * 
		 * @since 1.0.0.
		 */

		private void stop() {
			stopRequested = true;
		}

		/**
		 * Method for updating time on label.
		 * 
		 * @since 1.0.0.
		 */

		private void updateTime() {
			time = formatter.format(LocalDateTime.now());
			dateTime.setText(time);
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Menu bar used for text editor.
	 * 
	 * @since 1.0.0.
	 */

	private JMenuBar menuBar;

	/**
	 * Tool bar used for text editor.
	 * 
	 * @since 1.0.0.
	 */

	private JToolBar toolBar;

	/**
	 * {@link MultipleDocumentModel} used for text editor.
	 * 
	 * @since 1.0.0.
	 */

	private MultipleDocumentModel mdm;

	/**
	 * {@link FormLocalizationProvider} used for text editor.
	 * 
	 * @since 1.0.0.
	 */

	private FormLocalizationProvider flp;

	/**
	 * Panel used for tool bar and {@link MultipleDocumentModel}.
	 * 
	 * @since 1.0.0.
	 */

	private JPanel mainPanel;

	/**
	 * Panel used for showing status.
	 * 
	 * @since 1.0.0.
	 */

	private JPanel statusPanel;

	/**
	 * Label that shows current date and time.
	 * 
	 * @since 1.0.0.
	 */

	private JLabel dateTime;

	/**
	 * {@link Clock} used for updating date and time.
	 * 
	 * @since 1.0.0.
	 */

	private Clock clock;

	/**
	 * Default constructor.
	 * 
	 * @since 1.0.0.
	 */

	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setTitle("JNotepad++");
		initGUI();
		// listener for closing text editor that checks if all documents are saved
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (checkAllSaved()) {
					dispose();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				clock.stop();
			}

		});
		// listener that changes title when language changes
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				changeTitle();
			}
		});
	}

	/**
	 * Method for initializing GUI.
	 * 
	 * @since 1.0.0.
	 */

	private void initGUI() {
		setLayout(new BorderLayout());
		mdm = new DefaultMultipleDocumentModel(flp);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(mdm.getVisualComponent(), BorderLayout.CENTER);

		statusPanel = new JPanel(new GridLayout(1, 0));
		statusPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
		LJLabel length = new LJLabel(flp, false, new String[] { "length" }, null);
		LJLabel otherInfo = new LJLabel(flp, false, new String[] { "ln", "col", "sel" }, null);
		dateTime = new JLabel();
		clock = new Clock();
		dateTime.setHorizontalAlignment(SwingConstants.RIGHT);
		statusPanel.add(length);
		statusPanel.add(otherInfo);
		statusPanel.add(dateTime);

		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.getContentPane().add(statusPanel, BorderLayout.PAGE_END);

		// listener that checks which document is currently active and checks
		// adding/removing documents
		mdm.addMultipleDocumentListener(new MultipleDocumentListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @since 1.0.0.
			 */

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if (mdm.getNumberOfDocuments() == 0) {
					disableActionWhenZeroDocuments();
				}
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @since 1.0.0.
			 */

			@Override
			public void documentAdded(SingleDocumentModel model) {
				enableActionWhenZeroDocuments();
				// listener used to track caret actions for each document
				model.getTextComponent().getCaret().addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						Caret caret = model.getTextComponent().getCaret();
						if (caret.getDot() == caret.getMark()) {
							disableCaretActions();
						} else
							enableCaretActions();
						pasteAction.setEnabled(Toolkit.getDefaultToolkit().getSystemClipboard()
								.isDataFlavorAvailable(DataFlavor.stringFlavor));
						setInfo(model);
					}
				});
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @since 1.0.0.
			 */

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				changeTitle();
				if (currentModel != null) {
					Caret caret = currentModel.getTextComponent().getCaret();
					if (caret.getDot() == caret.getMark()) {
						disableCaretActions();
					} else
						enableCaretActions();
					setInfo(currentModel);
					pasteAction.setEnabled(Toolkit.getDefaultToolkit().getSystemClipboard()
							.isDataFlavorAvailable(DataFlavor.stringFlavor));
				} else {
					length.setHasDocument(false);
					otherInfo.setHasDocument(false);
				}
			}

			/**
			 * Method that sets information on status bar.
			 * 
			 * @param currentModel current document
			 * @since 1.0.0.
			 */

			private void setInfo(SingleDocumentModel currentModel) {
				JTextComponent c = mdm.getCurrentDocument().getTextComponent();
				int pos = c.getCaretPosition();
				Document doc = c.getDocument();
				Element root = doc.getDefaultRootElement();
				int row = root.getElementIndex(pos);
				int col = pos - root.getElement(row).getStartOffset();
				int selection = c.getSelectionEnd() - c.getSelectionStart();
				length.setHasDocument(true);
				length.updateValues(new int[] { currentModel.getTextComponent().getDocument().getLength() });
				otherInfo.setHasDocument(true);
				otherInfo.updateValues(new int[] { row + 1, col + 1, selection });
			}
		});

		createActions();
		createMenus();
		createToolbars();
		disableActionWhenZeroDocuments();
	}

	/**
	 * Method that enables caret actions when mark position and caret position is
	 * not equal.
	 * 
	 * @since 1.0.0.
	 */

	private void enableCaretActions() {
		copyAction.setEnabled(true);
		cutAction.setEnabled(true);
		toUpperAction.setEnabled(true);
		toLowerAction.setEnabled(true);
		invertAction.setEnabled(true);
		ascendingAction.setEnabled(true);
		descendingAction.setEnabled(true);
		uniqueAction.setEnabled(true);
	}

	/**
	 * Method that disables caret actions when mark position and caret position are
	 * equal.
	 * 
	 * @since 1.0.0.
	 */

	private void disableCaretActions() {
		copyAction.setEnabled(false);
		cutAction.setEnabled(false);
		pasteAction.setEnabled(false);
		toUpperAction.setEnabled(false);
		toLowerAction.setEnabled(false);
		invertAction.setEnabled(false);
		ascendingAction.setEnabled(false);
		descendingAction.setEnabled(false);
		uniqueAction.setEnabled(false);
	}

	/**
	 * Method that disables actions when there is not any document active.
	 * 
	 * @since 1.0.0.
	 */

	private void disableActionWhenZeroDocuments() {
		saveAction.setEnabled(false);
		saveAsAction.setEnabled(false);
		closeAction.setEnabled(false);
		copyAction.setEnabled(false);
		cutAction.setEnabled(false);
		pasteAction.setEnabled(false);
		statisticsAction.setEnabled(false);
		toUpperAction.setEnabled(false);
		toLowerAction.setEnabled(false);
		invertAction.setEnabled(false);
		ascendingAction.setEnabled(false);
		descendingAction.setEnabled(false);
		uniqueAction.setEnabled(false);
	}

	/**
	 * Method that enables actions when there is document active.
	 * 
	 * @since 1.0.0.
	 */

	private void enableActionWhenZeroDocuments() {
		saveAction.setEnabled(true);
		saveAsAction.setEnabled(true);
		closeAction.setEnabled(true);
		pasteAction.setEnabled(true);
		statisticsAction.setEnabled(true);
	}

	/**
	 * Method that changes title based on current status in text editor.
	 * 
	 * @since 1.0.0.
	 */

	private void changeTitle() {
		SingleDocumentModel document = mdm.getCurrentDocument();
		if (document == null)
			setTitle("JNotepad++");
		else {
			setTitle((document.getFilePath() == null ? "(" + flp.getString("unnamed") + ")"
					: document.getFilePath().toString()) + " - JNotepad++");
		}
	}

	/**
	 * Action used to create new document.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction createAction;

	/**
	 * Action used to open existing document.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction openAction;

	/**
	 * Action used to save document.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction saveAction;

	/**
	 * Action used to open save document as.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction saveAsAction;

	/**
	 * Action used to close document.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction closeAction;

	/**
	 * Action used to cut text.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction cutAction;

	/**
	 * Action used to copy text.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction copyAction;

	/**
	 * Action used to paste text.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction pasteAction;

	/**
	 * Action used to change current language to English.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction englishAction;

	/**
	 * Action used to change current language to Croatian.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction croatianAction;

	/**
	 * Action used to change current language to German.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction germanAction;

	/**
	 * Action used to change current language to French.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction frenchAction;

	/**
	 * Action used to show statistics to user.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction statisticsAction;

	/**
	 * Action used to exit from text editor.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction exitAction;

	/**
	 * Action used to change selected text to upper case.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction toUpperAction;

	/**
	 * Action used to change selected text to lower case.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction toLowerAction;

	/**
	 * Action used to invert cases in selected text.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction invertAction;

	/**
	 * Action used to sort selected lines ascending.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction ascendingAction;

	/**
	 * Action used to sort selected lines descending.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction descendingAction;

	/**
	 * Action used to delete duplicate lines.
	 * 
	 * @since 1.0.0.
	 */

	private LocalizableAction uniqueAction;

	/**
	 * Method used for creating actions.
	 * 
	 * @since 1.0.0.
	 */

	private void createActions() {
		createAction = new LocalizableAction("New", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				mdm.createNewDocument();
			}
		};
		createAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));

		openAction = new LocalizableAction("Open...", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION)
					return;
				Path file = fc.getSelectedFile().toPath().toAbsolutePath().normalize();
				if (!Files.exists(file)) {
					notifyUser(flp, JNotepadPP.this, file, flp.getString("fileDontExist"));
				} else if (!Files.isWritable(file)) {
					notifyUser(flp, JNotepadPP.this, file, flp.getString("fileNotWritable"));
				} else
					mdm.loadDocument(file);
			}
		};
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));

		saveAction = new LocalizableAction("Save", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel document = mdm.getCurrentDocument();
				if (document.getFilePath() != null) {
					mdm.saveDocument(document, null);
				} else
					saveAsAction.actionPerformed(e);
			}
		};
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

		saveAsAction = new LocalizableAction("SaveAs", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				Path oldFile = mdm.getCurrentDocument().getFilePath();
				if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION)
					return;
				Path file = fc.getSelectedFile().toPath().toAbsolutePath().normalize();
				if ((oldFile != null && mdm.findForPath(file) != null && !oldFile.equals(file)) || (oldFile == null && mdm.findForPath(file) != null)) {
					notifyUser(flp, JNotepadPP.this, file, flp.getString("alreadyUsed"));
				} else {
					if ((oldFile != null && file.toFile().exists() && !oldFile.equals(file))
							|| (oldFile == null && file.toFile().exists())) {
						String[] options = new String[] { flp.getString("yes"), flp.getString("no"),
								flp.getString("cancel") };
						int res = JOptionPane.showOptionDialog(JNotepadPP.this,file.toString()  + " " + flp.getString("fileExists"),
								flp.getString("SaveAs"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								options, options[0]);
						switch (res) {
						case 0:
							mdm.saveDocument(mdm.getCurrentDocument(), file);
							break;
						case 1:
							break;
						case 2:
							break;
						case JOptionPane.CLOSED_OPTION:
							break;
						}
					} else
						mdm.saveDocument(mdm.getCurrentDocument(), file);
				}
			}
		};
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));

		closeAction = new LocalizableAction("Close", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				close(e);
			}
		};
		closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));

		cutAction = new LocalizableAction("Cut", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				new DefaultEditorKit.CutAction().actionPerformed(e);
			}
		};
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));

		copyAction = new LocalizableAction("Copy", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				new DefaultEditorKit.CopyAction().actionPerformed(e);
			}
		};
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));

		pasteAction = new LocalizableAction("Paste", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				new DefaultEditorKit.PasteAction().actionPerformed(e);
			}
		};
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));

		englishAction = new LocalizableAction("English", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};
		englishAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt E"));

		croatianAction = new LocalizableAction("Croatian", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};
		croatianAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt C"));

		germanAction = new LocalizableAction("German", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};
		germanAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt G"));

		frenchAction = new LocalizableAction("French", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("fr");
			}
		};
		frenchAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt F"));

		statisticsAction = new LocalizableAction("Statistics", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = mdm.getCurrentDocument().getTextComponent().getText();
				long numberOfCharacters = text.length();
				long numberOfNoBlank = text.chars().filter((c) -> c != '\n' && c != '\t' && c != ' ').count();
				long numberOfLines = text.chars().filter((c) -> c == '\n').count() + 1;
				String[] options = new String[] { flp.getString("ok") };
				int res = JOptionPane.showOptionDialog(JNotepadPP.this,
						createMessage(numberOfCharacters, numberOfNoBlank, numberOfLines), flp.getString("Statistics"),
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				switch (res) {
				case 0:
					break;
				case JOptionPane.CLOSED_OPTION:
					break;
				}
			}

			/**
			 * Method for creating message for statistics.
			 * 
			 * @param numberOfCharacters number of characters
			 * @param numberOfNoBlank    number of non blank characters
			 * @param numberOfLines      number of lines
			 * @return created message
			 * @since 1.0.0.
			 */

			private String createMessage(long numberOfCharacters, long numberOfNoBlank, long numberOfLines) {
				return String.format(
						flp.getString("statisticMessage") + ":\n%d " + flp.getString("characters") + "\n%d "
								+ flp.getString("nonBlank") + "\n%d " + flp.getString("lines"),
						numberOfCharacters, numberOfNoBlank, numberOfLines);

			}

		};
		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt Y"));

		exitAction = new LocalizableAction("Exit", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkAllSaved()) {
					dispose();
				}
			}
		};
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));

		toUpperAction = new LocalizableAction("uppercase", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextComponent c = mdm.getCurrentDocument().getTextComponent();
				Document doc = c.getDocument();
				int start = c.getSelectionStart();
				int end = c.getSelectionEnd();
				try {
					StringBuilder sb = new StringBuilder();
					String text = doc.getText(Math.min(start, end), Math.abs(start - end));
					for (int i = 0; i < text.length(); i++) {
						char ch = text.charAt(i);
						if (Character.isLowerCase(ch)) {
							sb.append(Character.toUpperCase(ch));
						} else
							sb.append(ch);
					}
					doc.remove(Math.min(start, end), Math.abs(start - end));
					doc.insertString(Math.min(start, end), sb.toString(), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};
		toUpperAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift U"));

		toLowerAction = new LocalizableAction("lowercase", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextComponent c = mdm.getCurrentDocument().getTextComponent();
				Document doc = c.getDocument();
				int start = c.getSelectionStart();
				int end = c.getSelectionEnd();
				try {
					StringBuilder sb = new StringBuilder();
					String text = doc.getText(Math.min(start, end), Math.abs(start - end));
					for (int i = 0; i < text.length(); i++) {
						char ch = text.charAt(i);
						if (Character.isUpperCase(ch)) {
							sb.append(Character.toLowerCase(ch));
						} else
							sb.append(ch);
					}
					doc.remove(Math.min(start, end), Math.abs(start - end));
					doc.insertString(Math.min(start, end), sb.toString(), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};
		toLowerAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));

		invertAction = new LocalizableAction("invert", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextComponent c = mdm.getCurrentDocument().getTextComponent();
				Document doc = c.getDocument();
				int start = c.getSelectionStart();
				int end = c.getSelectionEnd();
				try {
					StringBuilder sb = new StringBuilder();
					String text = doc.getText(Math.min(start, end), Math.abs(start - end));
					for (int i = 0; i < text.length(); i++) {
						char ch = text.charAt(i);
						if (Character.isLowerCase(ch)) {
							sb.append(Character.toUpperCase(ch));
						} else if (Character.isUpperCase(ch)) {
							sb.append(Character.toLowerCase(ch));
						} else
							sb.append(ch);
					}
					doc.remove(Math.min(start, end), Math.abs(start - end));
					doc.insertString(Math.min(start, end), sb.toString(), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};
		invertAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift I"));

		ascendingAction = new LocalizableAction("ascending", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Locale locale = Locale.of(LocalizationProvider.getInstance().getLanguage());
				Collator collator = Collator.getInstance(locale);
				sort(collator);
			}
		};
		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt A"));

		descendingAction = new LocalizableAction("descending", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Locale locale = Locale.of(LocalizationProvider.getInstance().getLanguage());
				Collator collator = Collator.getInstance(locale);
				sort(collator.reversed());
			}
		};
		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt D"));

		uniqueAction = new LocalizableAction("unique", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextComponent c = mdm.getCurrentDocument().getTextComponent();
				Document doc = c.getDocument();
				Element root = doc.getDefaultRootElement();
				int start = Math.min(c.getSelectionStart(), c.getSelectionEnd());
				int end = Math.max(c.getSelectionStart(), c.getSelectionEnd());
				Element e1 = root.getElement(root.getElementIndex(start));
				Element e2 = root.getElement(root.getElementIndex(end));
				start = e1.getStartOffset();
				end = e2.getEndOffset();
				try {
					String[] lines = doc.getText(start, Math.abs(end - start)).split("\n");
					LinkedHashSet<String> set = new LinkedHashSet<>(List.of(lines));
					StringBuilder sb = new StringBuilder();
					for (String line : set) {
						sb.append(line + "\n");
					}
					doc.remove(Math.min(start, end), Math.abs(start - end) - 1);
					String s = sb.toString();
					doc.insertString(start, s.substring(0, s.length() - 1), null);
				} catch (BadLocationException e3) {
					e3.printStackTrace();
				}
			}
		};
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt U"));

	}

	/**
	 * Method used to sort lines based on given comparator.
	 * 
	 * @param comparator comparator
	 * @since 1.0.0.
	 */

	private void sort(Comparator<Object> comparator) {
		JTextComponent c = mdm.getCurrentDocument().getTextComponent();
		Document doc = c.getDocument();
		Element root = doc.getDefaultRootElement();
		int start = Math.min(c.getSelectionStart(), c.getSelectionEnd());
		int end = Math.max(c.getSelectionStart(), c.getSelectionEnd());
		Element e1 = root.getElement(root.getElementIndex(start));
		Element e2 = root.getElement(root.getElementIndex(end));
		start = e1.getStartOffset();
		end = e2.getEndOffset();
		try {
			String[] lines = doc.getText(start, Math.abs(end - start)).split("\n");
			Arrays.sort(lines, comparator);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < lines.length; i++) {
				sb.append(lines[i]);
				if (i < lines.length - 1)
					sb.append("\n");
			}
			doc.remove(Math.min(start, end), Math.abs(start - end) - 1);
			doc.insertString(start, sb.toString(), null);
		} catch (BadLocationException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Method used for creating menu bar.
	 * 
	 * @since 1.0.0.
	 */

	private void createMenus() {

		menuBar = new JMenuBar();

		JMenu fileMenu = new LJMenu("File", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createAction));
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(closeAction));

		JMenu toolsMenu = new LJMenu("Tools", flp);
		menuBar.add(toolsMenu);

		toolsMenu.add(new JMenuItem(cutAction));
		toolsMenu.add(new JMenuItem(copyAction));
		toolsMenu.add(new JMenuItem(pasteAction));

		JMenu changeCase = new LJMenu("changeCase", flp);
		changeCase.add(new JMenuItem(toUpperAction));
		changeCase.add(new JMenuItem(toLowerAction));
		changeCase.add(new JMenuItem(invertAction));

		toolsMenu.add(changeCase);
		toolsMenu.add(new JMenuItem(uniqueAction));

		JMenu sortMenu = new LJMenu("sort", flp);
		menuBar.add(sortMenu);

		sortMenu.add(new JMenuItem(ascendingAction));
		sortMenu.add(new JMenuItem(descendingAction));

		JMenu statisticalMenu = new LJMenu("Statistics", flp);
		menuBar.add(statisticalMenu);

		statisticalMenu.add(new JMenuItem(statisticsAction));

		JMenu languageMenu = new LJMenu("Languages", flp);
		menuBar.add(languageMenu);

		languageMenu.add(new JMenuItem(englishAction));
		languageMenu.add(new JMenuItem(croatianAction));
		languageMenu.add(new JMenuItem(germanAction));
		languageMenu.add(new JMenuItem(frenchAction));

		menuBar.add(new JMenuItem(exitAction));

		setJMenuBar(menuBar);
	}

	/**
	 * Method used for creating tool bar.
	 * 
	 * @since 1.0.0.
	 */

	private void createToolbars() {
		toolBar = new LJToolBar("toolBar", flp);
		toolBar.setFloatable(true);

		toolBar.add(new JButton(createAction));
		toolBar.add(new JButton(openAction));
		toolBar.add(new JButton(saveAction));
		toolBar.add(new JButton(saveAsAction));
		toolBar.add(new JButton(closeAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(exitAction));
		mainPanel.add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Method used to create {@link JOptionPane} that notifies user.
	 * 
	 * @param flp  {@link FormLocalizationProvider}.
	 * @param cmp  {@link Component}
	 * @param path {@link Path}
	 * @param text notification text
	 * @since 1.0.0.
	 */

	public static void notifyUser(FormLocalizationProvider flp, Component cmp, Path path, String text) {
		String[] options = new String[] { flp.getString("ok") };
		int res = JOptionPane.showOptionDialog(cmp, path.toAbsolutePath().normalize().toString() + "\n" + text,
				flp.getString("notification"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options,
				options[0]);
		switch (res) {
		case 0:
			break;
		case JOptionPane.CLOSED_OPTION:
			break;
		}
	}

	/**
	 * Method used for closing document.
	 * 
	 * @param e
	 * @return true if document closed; otherwise false
	 * @since 1.0.0.
	 */

	private boolean close(ActionEvent e) {
		SingleDocumentModel document = mdm.getCurrentDocument();
		if (document.isModified()) {
			String[] options = new String[] { flp.getString("yes"), flp.getString("no"), flp.getString("cancel") };
			int res = JOptionPane.showOptionDialog(JNotepadPP.this,
					flp.getString("saveFile") + " \""
							+ (mdm.getCurrentDocument().getFilePath() == null ? "(" + flp.getString("unnamed") + ")"
									: mdm.getCurrentDocument().getFilePath())
							+ "\" ?",
					flp.getString("Save"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[0]);
			switch (res) {
			case 0:
				saveAction.actionPerformed(e);
				mdm.closeDocument(document);
				return true;
			case 1:
				mdm.closeDocument(document);
				return true;
			case 2:
				return false;
			case JOptionPane.CLOSED_OPTION:
				return false;
			}
		}
		mdm.closeDocument(document);
		return true;
	}

	/**
	 * Method that checks if all documents are saved.
	 * 
	 * @return true if all documents are saved; otherwise false
	 * @since 1.0.0.
	 */

	private boolean checkAllSaved() {
		int n = mdm.getNumberOfDocuments();
		for (int i = 0; i < n; i++) {
			if (!close(new ActionEvent(this, i, getName()))) {
				break;
			}
		}
		return mdm.getNumberOfDocuments() == 0;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 * @since 1.0.0.
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

}
