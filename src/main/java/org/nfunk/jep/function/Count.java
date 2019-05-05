package org.nfunk.jep.function;

import java.util.Stack;

import org.nfunk.jep.ParseException;

/**
 * This class serves mainly as an example of a function that accepts any number
 * of parameters. Note that the numberOfParameters is initialized to -1.
 */
public class Count extends PostfixMathCommand {
	private List fun = new List();

	/**
	 * Constructor.
	 */
	public Count() {
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
		int i = 0;
		// repeat summation for each one of the current parameters
		while (i < curNumberOfParameters) {
			// get the parameter from the stack
			Object param = stack.pop();
			// it to the max (order is important for String arguments)
			if (param == null) {
				continue;
			}
			i++;
		}
		// push the result on the inStack
		stack.push(i);
	}
}
