package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents abstract implementation of {@link ILocalizationProvider}.
 * Methods to be override: getString::String and getLanguage::String
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	/**
	 * List of listeners.
	 * @since 1.0.0.
	 */
	
	private List<ILocalizationListener> listeners;
	
	/**
	 * Default constructor.
	 * @since 1.0.0.
	 */
	
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>listener</code> is <code>null</code>.
	 * @since 1.0.0.
	 */
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(Objects.requireNonNull(listener, "Listener can not be null!"));
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>listener</code> is <code>null</code>.
	 * @since 1.0.0.
	 */
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(Objects.requireNonNull(listener, "Listener can not be null!"));
	}
	
	/**
	 * Method that notifies all listeners when language changes.
	 * @since 1.0.0.
	 */
	
	public void fire() {
		listeners.forEach(ILocalizationListener::localizationChanged);
	}

}
