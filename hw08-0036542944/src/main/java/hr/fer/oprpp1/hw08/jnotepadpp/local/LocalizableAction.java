package hr.fer.oprpp1.hw08.jnotepadpp.local;


import java.util.Objects;

import javax.swing.AbstractAction;

/**
 * Class that represents abstract implementation for localization of {@link AbstractAction}.
 * Need to implement actionPerformed::void method.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public abstract class LocalizableAction extends AbstractAction{
	
	private final static long serialVersionUID = 1L;
	
	/**
	 * Localization key.
	 * @since 1.0.0.
	 */
	
	private String key;
	
	/**
	 * Localization listener.
	 * @since 1.0.0.
	 */
	
	private ILocalizationListener listener;
	
	/**
	 * Localization provider.
	 * @since 1.0.0.
	 */
	
	private ILocalizationProvider prov;
	
	/**
	 * Constructor with <code>key</code> and <code>lp</code> parameter.
	 * @param key localization key
	 * @param lp localization provider
	 * @throws NullPointerException if <code>key</code> or <code>lp</code> is <code>null</code>.
	 * @since 1.0.0.
	 */
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = Objects.requireNonNull(key, "Key can not be null!");
		prov = Objects.requireNonNull(lp, "LocalizationProvider can not be null");
		String translation = lp.getString(key);
		putValue(NAME, translation);
		addListener();
	}
	
	/**
	 * Method for adding listener to provider.
	 * @since 1.0.0.
	 */

	private void addListener() {
		this.listener = () -> putValue(NAME, prov.getString(key));
		prov.addLocalizationListener(listener);
	}

}
