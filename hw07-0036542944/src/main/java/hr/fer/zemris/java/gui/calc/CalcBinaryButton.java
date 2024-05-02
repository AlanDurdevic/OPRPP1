package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class that represents binary operation buttons for {@link Calculator}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CalcBinaryButton extends JButton{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with text, operator and model parameter.
	 * @param text text
	 * @param op binary operator
	 * @param model model
	 * @throws NullPointerException if <code>text</code>, <code>op</code> or <code>model</code>
	 * is <code>null</code>.
	 * @since 1.0.0.
	 */

	public CalcBinaryButton(String text, DoubleBinaryOperator op, CalcModel model) {
		setText(Objects.requireNonNull(text, "Text can not be null!"));
		Objects.requireNonNull(op, "Binary operator can not be null!");
		Objects.requireNonNull(model, "Model can not be null");
		addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.hasFrozenValue()) throw new CalculatorInputException();
				DoubleBinaryOperator oldOp = model.getPendingBinaryOperation();
				if(oldOp == null) {
					model.setActiveOperand(model.getValue());
					model.setPendingBinaryOperation(op);
				}
				else {
					model.setActiveOperand(oldOp.applyAsDouble(model.getActiveOperand(), model.getValue()));
					model.setPendingBinaryOperation(op);
				}
				model.clear();
				model.freezeValue(String.valueOf(model.getActiveOperand()));
			}
		});
	}
	
}
