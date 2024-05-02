package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class that represents full implementation of {@link ILocalizationProvider}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	/**
	 * Current language of localization provider.
	 * @since 1.0.0.
	 */
	
	private String language;
	
	/**
	 * Current {@link ResourceBundle} of localization provider.
	 * @since 1.0.0.
	 */
	
	private ResourceBundle bundle;
	
	/**
	 * Instance of {@link LocalizationProvider}.
	 * @since 1.0.0.
	 */
	
	private final static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Default constructor.
	 * Sets default language to English.
	 * @since 1.0.0.
	 */
	
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	/**
	 * Getter for {@link LocalizationProvider} instance.
	 * @return instance of {@link LocalizationProvider}
	 * @since 1.0.0.
	 */
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Method for setting language for {@link LocalizationProvider}.
	 * @param language language
	 * @throws NullPointerException if <code>language</code> is <code>null</code>.
	 * @since 1.0.0.
	 */
	
	public void setLanguage(String language) {
		this.language = Objects.requireNonNull(language, "Language can not be null!");
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
		fire();
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>key</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	@Override
	public String getString(String key) {
		return bundle.getString(Objects.requireNonNull(key, "Key can not be null!"));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getLanguage() {
		return language;
	}
	
}
