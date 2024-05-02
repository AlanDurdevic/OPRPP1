package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JMenu;

/**
 * Class that represents extended implementation that is used for localization
 * of {@link JMenu}.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class LJMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Localization key.
	 * @since 1.0.0.
	 */
	
	private String key;
	
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
	 * Constructor with <code>key</code> and <code>key</code> parameters.
	 * @param key localization key
	 * @param lp localization provider
	 * @throws NullPointerException if <code>key</code> or <code>lp</code> is <code>null</code>.
	 * @since 1.0.0.
	 */
	
	public LJMenu(String key, ILocalizationProvider lp) {
		this.key = Objects.requireNonNull(key, "Key can not be null!");
		prov = Objects.requireNonNull(lp, "Localization provider can not be null!");
		this.listener = this::updateText;
		updateText();
		lp.addLocalizationListener(listener);
	}
	
	/**
	 * Method that is used to update text.
	 * @since 1.0.0.
	 */
	
	private void updateText() {
		setText(prov.getString(key));
	}

}
