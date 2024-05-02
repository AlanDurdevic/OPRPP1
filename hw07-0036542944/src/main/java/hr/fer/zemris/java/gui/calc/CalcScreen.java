package hr.fer.zemris.java.gui.calc;

import java.util.Objects;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Class that represents screen for {@link Calculator}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CalcScreen extends JLabel implements CalcValueListener{

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>model</code> is <code>null</code>
	 * @since 1.0.0.
	 */
	
	@Override
	public void valueChanged(CalcModel model) {
		setText(Objects.requireNonNull(model, "Model can not be null!").toString());
	}

}
