package org.nfunk.jep.function;

import org.nfunk.jep.JEP;
import org.nfunk.jep.type.NumberFactory;

public class HJEP extends JEP {
	/**
	 * Creates a new JEP instance with the default settings.
	 * <p>
	 * Traverse = false<br>
	 * Allow undeclared variables = false<br>
	 * Implicit multiplication = false<br>
	 * Number Factory = DoubleNumberFactory
	 */
	public HJEP() {
		super();
		super.addStandardConstants();
		super.addStandardFunctions();
		super.addComplex();
		super.addFunction("max", new Max());
		super.addFunction("min", new Min());
		super.addFunction("count", new Count());
		super.addFunction("avg", new Avg());
	}

	/**
	 * Creates a new JEP instance with custom settings. If the
	 * numberFactory_in is null, the default number factory is used.
	 * 
	 * @param traverse_in        The traverse option.
	 * @param allowUndeclared_in The "allow undeclared variables" option.
	 * @param implicitMul_in     The implicit multiplication option.
	 * @param numberFactory_in   The number factory to be used.
	 */
	public HJEP(boolean traverse_in, boolean allowUndeclared_in, boolean implicitMul_in, NumberFactory numberFactory_in) {
		super(traverse_in, allowUndeclared_in, implicitMul_in, numberFactory_in);
		super.addStandardConstants();
		super.addStandardFunctions();
		super.addComplex();
		super.addFunction("max", new Max());
		super.addFunction("min", new Min());
		super.addFunction("count", new Count());
		super.addFunction("avg", new Avg());
	}
}
