package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Class that implements basic Calculator with {@link CalcModel} model.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Calculator extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link CalcModel} model.
	 * @since 1.0.0.
	 */
	
	private CalcModel model;

	/**
	 * List of digit buttons.
	 * @since 1.0.0.
	 */
	
	private List<CalcButton> digitButtons = new ArrayList<>();
	
	/**
	 * List of binary operation buttons.
	 * @since 1.0.0.
	 */
	
	private List<CalcBinaryButton> binaryOperationButtons = new ArrayList<>();
	
	/**
	 * List of other buttons.
	 * @since 1.0.0.
	 */
	
	private List<JButton> otherButtons = new ArrayList<>();
	
	/**
	 * List of unary operations buttons.
	 * @since 1.0.0.
	 */
	
	private List<CalcOperationButton> operationButtons = new ArrayList<>();
	
	/**
	 * Stack.
	 * @since 1.0.0.
	 */
	
	private Stack<Double> stack = new Stack<>();
	
	/**
	 * Constructor with model parameter.
	 * @param model model
	 * @throws NullPointerException if <code>model</code> is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public Calculator(CalcModel model) {
		this.model = Objects.requireNonNull(model, "Model can not be null!");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		initGUI();
		digitButtons.get(0).doClick();
		pack();
		setLocationRelativeTo(null);
	}
	
	/**
	 * Method for initializing GUI.
	 * @since 1.0.0.
	 */

	private void initGUI() {
		Container cp = getContentPane();
		setLayout(new CalcLayout(5));
		
		CalcScreen screen = new CalcScreen();
		screen.setOpaque(true);
		screen.setBackground(Color.YELLOW);
		screen.setFont(screen.getFont().deriveFont(30f));
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		model.addCalcValueListener(screen);
		cp.add(screen, "1, 1");
		
		setDigitButtons();
		cp.add(digitButtons.get(0), "5, 3");
		cp.add(digitButtons.get(1), "4, 3");
		cp.add(digitButtons.get(2), "4, 4");
		cp.add(digitButtons.get(3), "4, 5");
		cp.add(digitButtons.get(4), "3, 3");
		cp.add(digitButtons.get(5), "3, 4");
		cp.add(digitButtons.get(6), "3, 5");
		cp.add(digitButtons.get(7), "2, 3");
		cp.add(digitButtons.get(8), "2, 4");
		cp.add(digitButtons.get(9), "2, 5");
		
		setBinaryOperations();
		cp.add(binaryOperationButtons.get(0), "5, 6");
		cp.add(binaryOperationButtons.get(1), "4, 6");
		cp.add(binaryOperationButtons.get(2), "3, 6");
		cp.add(binaryOperationButtons.get(3), "2, 6");
		
		setOtherButtons();
		cp.add(otherButtons.get(0), "1, 6");
		cp.add(otherButtons.get(1), "5, 4");
		cp.add(otherButtons.get(2), "5, 5");
		cp.add(otherButtons.get(3), "1, 7");
		cp.add(otherButtons.get(4), "2, 7");
		cp.add(otherButtons.get(5), "3, 7");
		cp.add(otherButtons.get(6), "4, 7");
		
		setOperationButtons();
		cp.add(operationButtons.get(0), "2, 1");
		cp.add(operationButtons.get(1), "3, 1");
		cp.add(operationButtons.get(2), "4, 1");
		cp.add(operationButtons.get(3), "2, 2");
		cp.add(operationButtons.get(4), "3, 2");
		cp.add(operationButtons.get(5), "4, 2");
		cp.add(operationButtons.get(6), "5, 2");
		
		JButton binaryOperationButton = new JButton("x^n");
		cp.add(binaryOperationButton, "5, 1");
		binaryOperationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.hasFrozenValue()) throw new CalculatorInputException();
				DoubleBinaryOperator oldOp = model.getPendingBinaryOperation();
				if(oldOp == null) {
					model.setActiveOperand(model.getValue());
					model.setPendingBinaryOperation(binaryOperationButton.getText().equals("x^n") ? (x, n) -> Math.pow(x, n) : (x, n) ->Math.pow(x, 1./n));
				}
				else {
					model.setActiveOperand(oldOp.applyAsDouble(model.getActiveOperand(), model.getValue()));
					model.setPendingBinaryOperation(binaryOperationButton.getText().equals("x^n") ? (x, n) -> Math.pow(x, n) : (x, n) ->Math.pow(x, 1./n));
				}
				model.clear();
				model.freezeValue(String.valueOf(model.getActiveOperand()));
			}
		});
		
		JCheckBox checkBox = new JCheckBox("Inv");
		checkBox.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {
					operationButtons.forEach(b -> b.setState(true));
					binaryOperationButton.setText("x^(1/n)");
				}
				else {
					operationButtons.forEach(b -> b.setState(false));
					binaryOperationButton.setText("x^n");
				}
			}
		});
		cp.add(checkBox, "5, 7");
	}
	
	/**
	 * Method for setting unary operation buttons.
	 * @since 1.0.0.
	 */

	private void setOperationButtons() {
		operationButtons.add(new CalcOperationButton(x -> 1/x, x-> 1/x, "1/x", "1/x", model));
		operationButtons.add(new CalcOperationButton(x -> Math.log10(x), x-> Math.pow(10., x), "log", "10^x", model));
		operationButtons.add(new CalcOperationButton(x -> Math.log(x), x -> Math.pow(Math.E,  x), "ln", "e^x", model));
		operationButtons.add(new CalcOperationButton(x -> Math.sin(x), x -> Math.asin(x), "sin", "arcsin", model));
		operationButtons.add(new CalcOperationButton(x -> Math.cos(x), x-> Math.acos(x), "cos", "arccos", model));
		operationButtons.add(new CalcOperationButton(x -> Math.tan(x), x-> Math.atan(x), "tan", "arctan", model));
		operationButtons.add(new CalcOperationButton(x -> 1 / Math.tan(x), x -> 1 / Math.atan(x), "ctg", "arcctg", model));
	}
	
	/**
	 * Method for setting other buttons.
	 * @since 1.0.0.
	 */

	private void setOtherButtons() {
		JButton equals = new JButton("=");
		equals.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				DoubleBinaryOperator oldOp = model.getPendingBinaryOperation();
				if(oldOp != null) {
					double v = oldOp.applyAsDouble(model.getActiveOperand(), model.getValue());
					model.clear();
					model.setValue(v);
					model.clearActiveOperand();
					model.setPendingBinaryOperation(null);
				}
			}
		});
		otherButtons.add(equals);
		JButton swap = new JButton("+/-");
		swap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.swapSign();
			}
		});
		otherButtons.add(swap);
		JButton point = new JButton(".");
		point.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.insertDecimalPoint();
			}
		});
		otherButtons.add(point);
		JButton clr = new JButton("clr");
		clr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clear();
			}
		});
		otherButtons.add(clr);
		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearAll();
				stack.clear();
			}
		});
		otherButtons.add(reset);
		JButton push = new JButton("push");
		push.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stack.push(Double.parseDouble(model.toString()));
			}
		});
		otherButtons.add(push);
		JButton pop = new JButton("pop");
		pop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double v;
				try {
					v = stack.pop();
					model.setValue(v);
				}catch(EmptyStackException ex) {
					JOptionPane.showMessageDialog(Calculator.this, "Stack is empty!", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		otherButtons.add(pop);
		
	}
	
	/**
	 * Method for setting binary operation buttons.
	 * @since 1.0.0.
	 */

	private void setBinaryOperations() {
		CalcBinaryButton plus = new CalcBinaryButton("+", (x, y) -> x + y, model);
		binaryOperationButtons.add(plus);
		CalcBinaryButton minus = new CalcBinaryButton("-", (x, y) -> x - y, model);
		binaryOperationButtons.add(minus);
		CalcBinaryButton multiply = new CalcBinaryButton("*", (x, y) -> x * y, model);
		binaryOperationButtons.add(multiply);
		CalcBinaryButton divide = new CalcBinaryButton("/", (x, y) -> x / y, model);
		binaryOperationButtons.add(divide);
		binaryOperationButtons.forEach((b) -> b.setFont(b.getFont().deriveFont(15f)));
	}
	
	/**
	 * Method for setting digit buttons.
	 * @since 1.0.0.
	 */

	private void setDigitButtons() {
		digitButtons.add(new CalcButton(model, 0));
		digitButtons.add(new CalcButton(model, 1));
		digitButtons.add(new CalcButton(model, 2));
		digitButtons.add(new CalcButton(model, 3));
		digitButtons.add(new CalcButton(model, 4));
		digitButtons.add(new CalcButton(model, 5));
		digitButtons.add(new CalcButton(model, 6));
		digitButtons.add(new CalcButton(model, 7));
		digitButtons.add(new CalcButton(model, 8));
		digitButtons.add(new CalcButton(model, 9));
		digitButtons.forEach((b) -> b.setFont(b.getFont().deriveFont(30f)));
		digitButtons.forEach((b) -> b.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				b.getCalcModel().insertDigit(b.getNumber());
			}
		}));
	}
	
	/**
	 * Main method.
	 * @param args
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator(new CalcModelImpl()).setVisible(true));
	}

}
