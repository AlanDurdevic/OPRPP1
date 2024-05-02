package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import javax.swing.JFrame;

/**
 * Class that represents extended implementation for {@link LocalizationProviderBridge}.
 * It contains frame that is provided with localization.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor with <code>parent</code> and <code>frame</code> parameter.
	 * @param parent parent
	 * @param frame frame
	 * @throws NullPointerException if <code>parent</code> or <code>frame</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(Objects.requireNonNull(parent, "Parent provider can not be null!"));
		Objects.requireNonNull(frame, "Frame can not be null!").addWindowListener(new WindowAdapter() {

			/**
			 * {@inheritDoc}
			 * @since 1.0.0.
			 */
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			/**
			 * {@inheritDoc}
			 * @since 1.0.0.
			 */

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

		});
	}

}
