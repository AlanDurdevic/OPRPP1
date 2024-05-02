package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JLabel;

/**
 * Class that represents extended implementation that is used for localization
 * of {@link JLabel}.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class LJLabel extends JLabel{
	
		private static final long serialVersionUID = 1L;
		
		/**
		 * Array of localization keys for label.
		 * @since 1.0.0.
		 */
		
		private String[] keys;
		
		/**
		 * Localization provider.
		 * @since 1.0.0.
		 */
		
		private ILocalizationProvider prov;
		
		/**
		 * Localization listener.
		 * @since 1.0.0.
		 */
		
		private ILocalizationListener listener;
		
		/**
		 * Boolean that shows if document is present.
		 * @since 1.0.0.
		 */
		
		private boolean hasDocument;
		
		/**
		 * Array of values for label.
		 * @since 1.0.0.
		 */
		
		private int[] values;
		
		/**
		 * Constructor with <code>lp</code> and <code>hasDocument</code> and <code>keys</code> and <code>values</code> parameters.
		 * @param lp localization provider
		 * @param hasDocument boolean that shows if document is present
		 * @param keys localization key
		 * @param values values for text
		 * @throws NullPointerException if <code>keys</code> or <code>lp</code> is <code>null</code>.
		 * @since 1.0.0.
		 */
		
		public LJLabel(ILocalizationProvider lp, boolean hasDocument, String[] keys, int[] values) {
			this.keys = Objects.requireNonNull(keys, "Keys can not be null!");
			prov = Objects.requireNonNull(lp, "Localization provider can not be null!");
			this.values = values;
			this.hasDocument = hasDocument;
			this.listener = this::updateText;
			lp.addLocalizationListener(listener);
			updateText();
		}
		
		/**
		 * Method for updating values.
		 * @param values values
		 * @since 1.0.0.
		 */
		
		public void updateValues(int[] values) {
			this.values = values;
			updateText();
		}
		
		/**
		 * Setter for hasDocument.
		 * @param hasDocument boolean that shows if document is present
		 * @since 1.0.0.
		 */
		
		public void setHasDocument(boolean hasDocument) {
			this.hasDocument = hasDocument;
			if(!hasDocument) updateText();
		}
		
		/**
		 * Method for updating text.
		 * @since 1.0.0.
		 */
		
		private void updateText() {
			StringBuilder sb = new StringBuilder();
			if(hasDocument) {
				for(int i = 0; i < keys.length; i++) {
					sb.append(prov.getString(keys[i]) + " : " + values[i] + " ");
				}
			}
			else {
				for(int i = 0; i < keys.length; i++) {
					sb.append(prov.getString(keys[i]) + " : - ");
				}
			}
			setText(sb.toString());
		}

}
