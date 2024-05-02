package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface that provides function for Localization providers.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface ILocalizationProvider {
	
	/**
	 * Method for adding {@link ILocalizationListener}.
	 * @param listener listener
	 * @since 1.0.0.
	 */
	
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Method for removing {@link ILocalizationListener}.
	 * @param listener listener
	 * @since 1.0.0.
	 */
	
	void removeLocalizationListener(ILocalizationListener listener);
	
	 /**
	  * Method that return translated key value based on localization language.
	  * @param key key
	  * @return translated word
	  * @since 1.0.0.
	  */
	
	String getString(String key);
	
	/**
	 * Method that return current language of localization.
	 * @return language of localization
	 * @since 1.0.0.
	 */
	
	String getLanguage();

}
