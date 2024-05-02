package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class that represents unary operation buttons for {@link Calculator}.
 * Button have 2 states.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CalcOperationButton extends JButton{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Operation for first state.
	 * @since 1.0.0.
	 */
	
	private DoubleUnaryOperator operation1;
	
	/**
	 * Operation for second state.
	 * @since 1.0.0.
	 */
	
	private DoubleUnaryOperator operation2;
	
	/**
	 * Text for first state.
	 * @since 1.0.0.
	 */
	
	private String text1;
	
	/**
	 * Text for second state.
	 * @since 1.0.0.
	 */
	
	private String text2;
	
	/**
	 * State.
	 * @since 1.0.0.
	 */
	
	private boolean state;
	
	/**
	 * Constructor with both operations and both text parameter and model parameter.
	 * @param operation1 first operation
	 * @param operation2 second operation
	 * @param text1 first text
	 * @param text2 second text
	 * @param model model
	 * @throws NullPointerException if <code>operation1</code> or <code>operation2</code>
	 *  or <code>text1</code> or <code>text2</code> or <code>model</code> is <code>null</code> 
	 * @since 1.0.0.
	 */
	
	public CalcOperationButton(DoubleUnaryOperator operation1, DoubleUnaryOperator operation2, String text1, String text2, CalcModel model) {
		this.operation1 = Objects.requireNonNull(operation1, "First operation can not be null!");
		this.operation2 = Objects.requireNonNull(operation2, "Second operation can not be null!");
		this.state = false;
		this.text1 = Objects.requireNonNull(text1, "First text can not be null!");
		this.text2 = Objects.requireNonNull(text2, "Second text can not be null!");
		Objects.requireNonNull(model, "Model can not be null!");
		setText(text1);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.hasFrozenValue()) throw new CalculatorInputException();
				model.setValue(getOperation().applyAsDouble(model.getValue()));
			}
		});
	}
	
	/**
	 * Getter for current operation.
	 * @return current operation
	 * @since 1.0.0.
	 */
	
	public DoubleUnaryOperator getOperation() {
		return state ? operation2 : operation1;
	}
	
	/**
	 * Setter for state
	 * @param state state
	 * @since 1.0.0.
	 */
	
	public void setState(boolean state) {
		this.state = state;
		setText(state ? text2 : text1);
	}
	
}
