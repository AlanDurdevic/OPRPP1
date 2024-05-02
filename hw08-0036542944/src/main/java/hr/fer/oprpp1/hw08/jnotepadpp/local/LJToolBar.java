package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JToolBar;

/**
 * Class that represents extended implementation that is used for localization
 * of {@link JToolBar}.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class LJToolBar extends JToolBar {

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

	public LJToolBar(String key, ILocalizationProvider lp) {
		this.key = Objects.requireNonNull(key, "Key can not be null!");
		prov = Objects.requireNonNull(lp, "Localization provider can not be null!");
		this.listener = this::updateText;
		setToolTipText(lp.getString(key));
		lp.addLocalizationListener(listener);
	}
	
	/**
	 * Method that is used to update text.
	 * @since 1.0.0.
	 */

	private void updateText() {
		setToolTipText(prov.getString(key));
	}

}
