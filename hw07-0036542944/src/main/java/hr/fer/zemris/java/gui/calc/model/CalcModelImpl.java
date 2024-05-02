package hr.fer.zemris.java.gui.calc.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of {@link CalcModel}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CalcModelImpl implements CalcModel {
	
	/**
	 * Variable that tells if current value is editable.
	 * @since 1.0.0.
	 */

	private boolean isEditable;
	
	/**
	 * Variable that tells if current value is negative.
	 * @since 1.0.0.
	 */

	private boolean isNegative;
	
	/**
	 * Variable that represents current entered number.
	 * @since 1.0.0.
	 */

	private String enteredDigits;
	
	/**
	 * Variable that represent current value.
	 * @since 1.0.0.
	 */

	private double value;
	
	/**
	 * Variable that represents freezed value.
	 * @since 1.0.0.
	 */

	private String freezeValue;
	
	/**
	 * Variable that represents current active operand.
	 * @since 1.0.0.
	 */

	private OptionalDouble activeOperand;
	
	/**
	 * Variable that represents current pending operation.
	 * @since 1.0.0.
	 */

	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * List of all {@link CalcValueListener} for this model.
	 * @since 1.0.0.
	 */
	
	private List<CalcValueListener> listenerList;
	
	/**
	 * Default constructor.
	 * @since 1.0.0.
	 */

	public CalcModelImpl() {
		isEditable = true;
		isNegative = false;
		enteredDigits = "";
		value = 0;
		freezeValue = null;
		activeOperand = OptionalDouble.empty();
		pendingOperation = null;
		listenerList = new LinkedList<>();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listenerList.add(Objects.requireNonNull(l, "Listener can not be null!"));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listenerList.remove(l);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public double getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void setValue(double value) {
		if (value < 0)
			isNegative = true;
		this.value = value;
		enteredDigits = String.valueOf(Math.abs(this.value));
		isEditable = false;
		freezeValue = null;
		notifyListeners();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public boolean isEditable() {
		return isEditable;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void clear() {
		isEditable = true;
		isNegative = false;
		enteredDigits = "";
		value = 0;
		freezeValue = null;
		notifyListeners();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void clearAll() {
		activeOperand = OptionalDouble.empty();
		pendingOperation = null;
		clear();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable)
			throw new CalculatorInputException();
		isNegative = !isNegative;
		value = -value;
		notifyListeners();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable || enteredDigits.isBlank() || enteredDigits.contains("."))
			throw new CalculatorInputException();
		enteredDigits += ".";
		freezeValue = null;
		notifyListeners();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(digit < 0 || digit > 9) throw new IllegalArgumentException();
		if (!isEditable)
			throw new CalculatorInputException();
		double newValue;
		if (!(digit == 0 && !enteredDigits.isBlank() && Double.parseDouble(enteredDigits) < 1
				&& !enteredDigits.contains("."))) {
			try {
				newValue = Double.parseDouble(enteredDigits + digit);
				if(newValue == Double.POSITIVE_INFINITY) throw new CalculatorInputException();
				value = isNegative ? -newValue : newValue;
				enteredDigits = enteredDigits + digit;
				freezeValue = null;
				if(!enteredDigits.contains(".") && enteredDigits.length() > 1 && enteredDigits.charAt(0) == '0'){
					enteredDigits = enteredDigits.substring(1, enteredDigits.length());
				}
			} catch (NumberFormatException ex) {
				throw new CalculatorInputException();
			}
		}
		notifyListeners();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand.isPresent();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand.isEmpty())
			throw new IllegalStateException();
		return activeOperand.getAsDouble();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = OptionalDouble.of(activeOperand);
		isEditable = false;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void clearActiveOperand() {
		activeOperand = OptionalDouble.empty();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String toString() {
		if (freezeValue != null)
			return freezeValue;
		if (enteredDigits.isBlank())
			return isNegative ? "-0" : "0";
		return isNegative ? "-" + enteredDigits : enteredDigits;
	}
	
	/**
	 * Method that notifies all listeners that state changed.
	 * @since 1.0.0.
	 */

	public void notifyListeners() {
		listenerList.forEach((l) -> l.valueChanged(this));
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void freezeValue(String value) {
		freezeValue = value;
		notifyListeners();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public boolean hasFrozenValue() {
		return freezeValue != null;
	}

}
