package org.nfunk.jep.function;

import java.util.Stack;

import org.nfunk.jep.ParseException;

/**
 * This class serves mainly as an example of a function that accepts any number
 * of parameters. Note that the numberOfParameters is initialized to -1.
 */
public class Max extends PostfixMathCommand {
	/**
	 * Constructor.
	 */
	public Max() {
		// Use a variable number of arguments
		numberOfParameters = -1;
	}

	/**
	 * Calculates the result of summing up all parameters, which are assumed to
	 * be of the Double type.
	 */
	public void run(Stack stack) throws ParseException {
		checkStack(stack);// check the stack

		if (curNumberOfParameters < 1)
			throw new ParseException("No arguments for Max");

		// initialize the result to the first argument
		Object max = stack.pop();
		Object param;
		int i = 1;

		// repeat summation for each one of the current parameters
		while (i < curNumberOfParameters) {
			// get the parameter from the stack
			param = stack.pop();
			// it to the max (order is important for String arguments)
			max = max(max, param);
			i++;
		}

		// push the result on the inStack
		stack.push(max);
	}

	public Object max(Object param1, Object param2) throws ParseException {
		if (param2 instanceof Number && param2 instanceof Number) {
			return ((Number) param1).doubleValue() > ((Number) param2).doubleValue() ? param1 : param2;
		}
		throw new ParseException("Invalid parameter type");
	}
}
