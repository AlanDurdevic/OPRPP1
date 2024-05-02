package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface that represents listener for {@link ILocalizationProvider}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface ILocalizationListener {
	
	/**
	 * Method that executes when listener is notified.
	 * @since 1.0.0.
	 */

	void localizationChanged();
	
}
