package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Objects;

/**
 * Class that represents decorator for {@link LocalizationProvider}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	/**
	 * Boolean variable that shows if provider is connected to parent provider.
	 * @since 1.0.0.
	 */
	
	private boolean connected;
	
	/**
	 * Parent provider.
	 * @since 1.0.0.
	 */
	
	private ILocalizationProvider parent;
	
	/**
	 * Listener that listens to parent provider.
	 * @since 1.0.0.
	 */
	
	private ILocalizationListener listener;
	
	/**
	 * Current language of provider.
	 * @since 1.0.0.
	 */
	
	private String currentLanguage;
	
	/**
	 * Constructor with parent provider parameter
	 * @param parent parent provider
	 * @throws NullPointerException if <code>parent</code> is <code>null</code>.
	 */
	
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = Objects.requireNonNull(parent, "Parent provider can not be null!");
		listener = new ILocalizationListener() {
			
			/**
			 * {@inheritDoc}
			 * @since 1.0.0.
			 */
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>key</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	@Override
	public String getString(String key) {
		return parent.getString(Objects.requireNonNull(key, "Key can not be null!"));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getLanguage() {
		return parent.getLanguage();
	}
	
	/**
	 * Method for disconnecting to parent provider.
	 * @since 1.0.0.
	 */
	
	public void disconnect() {
		if(!connected) return;
		connected = false;
		parent.removeLocalizationListener(listener);
	}
	
	/**
	 * Method for connecting to parent provider.
	 * @since 1.0.0.
	 */
	
	public void connect() {
		if(connected) return;
		connected = true;
		parent.addLocalizationListener(listener);
		if(currentLanguage != null && !currentLanguage.equals(parent.getLanguage())) {
			currentLanguage = parent.getLanguage();
			fire();
		}
	}

}
