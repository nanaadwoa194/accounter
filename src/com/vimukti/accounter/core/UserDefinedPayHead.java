package com.vimukti.accounter.core;

/**
 * As User defined Value allows you to manually enter the value at the time of
 * processing the salary. This Calculation Type is used the value of pay is not
 * fixed and does not depend upon any pay components.
 * 
 * Example: Incentives or Variable Salary.
 * 
 * @author Prasanna Kumar G
 * 
 */
public class UserDefinedPayHead extends PayHead {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDefinedPayHead() {
		super(CALCULATION_TYPE_AS_USER_DEFINED);
	}

}
