package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Command line application to evaluate given expression.
 * Expression must be in postfix representation.
 * Accepts a single command-line argument.
 * Example 1: “8 2 /” means apply / on 8 and 2, so 8/2=4.
 * Example 2: “-1 8 2 / +” means apply / on 8 and 2, so 8/2=4,
 * then apply + on -1 and 4, so the result is 3.
 * @author Alan Đurđević
 * @version 1.0.0 
 */

public class StackDemo {
	
	/**
	 * Performs evaluation.
	 * @param args arguments for the expression
	 * @throws IllegalArgumentException if <code>args</code> length != 1 or
	 * expression contains value that is not number or arithmetic operator.
	 * @throws EmptyStackException if there is invalid number of operands.
	 * @throws ArithmeticException if tries to divide by zero
	 * @since 1.0.0
	 */

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Number of expressions should be 1.");
		}

		String[] arguments = args[0].split("[ ]+");

		ObjectStack stack = new ObjectStack();
		try {
			for (String argument : arguments) {
				try {
					int number = Integer.parseInt(argument);
					stack.push(number);
				} catch (NumberFormatException exc) {
					if (argument.equals("+") || argument.equals("-") || argument.equals("/") || argument.equals("*")
							|| argument.equals("%")) {
						int number2 = Integer.parseInt(stack.pop().toString());
						int number1 = Integer.parseInt(stack.pop().toString());
						int result;
						switch (argument) {
						case "+":
							result = number1 + number2;
							stack.push(result);
							break;
						case "-":
							result = number1 - number2;
							stack.push(result);
							break;
						case "/":
							if (number2 == 0)
								throw new ArithmeticException("Can not / with 0.");
							result = number1 / number2;
							stack.push(result);
							break;
						case "*":
							result = number1 * number2;
							stack.push(result);
							break;
						case "%":
							if (number2 == 0)
								throw new ArithmeticException("Can not % with 0.");
							result = number1 % number2;
							stack.push(result);
						}

					} else {
						throw new IllegalArgumentException(
								"Argument \"" + argument + "\" is not number or arithmetic operator.");
					}
				}
			}
			if (stack.size() != 1) {
				System.err.println("Too many operands in expression.");
			} else {
				System.out.println("Expression evaluates to " + stack.pop() + ".");
			}
		}catch(EmptyStackException exc) {
			throw new EmptyStackException("Invalid numbers of operands.");
		}
		catch (Exception exc) {
			System.err.println(exc.getClass() + ": " + exc.getMessage());
		}

	}
}
