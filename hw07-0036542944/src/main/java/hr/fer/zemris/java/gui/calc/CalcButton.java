package hr.fer.zemris.java.gui.calc;

import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class that represents digit buttons for {@link Calculator}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CalcButton extends JButton{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Digit number.
	 * @since 1.0.0.
	 */
	
	private final int number;
	
	/**
	 * Calculator model.
	 * @since 1.0.0.
	 */
	
	private CalcModel calcModel;
	
	/**
	 * Constructor with {@link CalcModel} and number parameter.
	 * @param calcModel calcModel
	 * @param number number
	 * @throws IllegalArgumentException if <code>number</code> is less than 0 or greater than 9
	 * @throws NullPointerException if <code>calcModel</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public CalcButton(CalcModel calcModel, int number) {
		if(number < 0 || number > 9) throw new IllegalArgumentException();
		this.calcModel = Objects.requireNonNull(calcModel, "Model can not be null!");
		this.number = number;
		setText(String.valueOf(number));
	}
	
	/**
	 * Getter for number.
	 * @return number
	 * @since 1.0.0.
	 */
	
	public int getNumber() {
		return number;
	}
	
	/**
	 * Getter for calcModel
	 * @return calcModel
	 * @since 1.0.0.
	 */
	
	public CalcModel getCalcModel() {
		return calcModel;
	}
	
}
