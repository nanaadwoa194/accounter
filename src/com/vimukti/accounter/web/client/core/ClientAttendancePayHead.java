package com.vimukti.accounter.web.client.core;

import com.vimukti.accounter.web.client.externalization.AccounterMessages;
import com.vimukti.accounter.web.client.ui.Accounter;

public class ClientAttendancePayHead extends ClientPayHead {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * As per Calendar Period refers to the perpetual calendar month, i.e. if
	 * the payroll process is carried out for the month of March, then the
	 * attendance records will be entered for 31 days.
	 */
	public static final int PER_DAY_CALCULATION_AS_PER_CALANDAR_PERIOD = 1;

	/**
	 * User Defined refers to the consistent payroll period irrespective of
	 * calendar month, i.e. If an employee�s salary is based on a standard
	 * month of 30 days irrespective of the calendar month, then you can select
	 * User Defined as the Calculation Basis and define the periodicity of the
	 * specified period or month.
	 */
	public static final int PER_DAY_CALCULATION_30_DAYS = 2;

	/**
	 * User Defined Calendar Type option will provide the flexibility to the
	 * user to select user defined calendar days every Calculation Period. Once
	 * this option is selected the user can have different number of working
	 * days for every Calculation Period.
	 */
	public static final int PER_DAY_CALCULATION_USER_DEFINED_CALANDAR = 3;

	public static final int LEAVE_WITH_PAY = 1;
	public static final int LEAVE_WITHOUT_PAY = 2;
	public static final int ATTENDANCE_ON_PAYHEAD = 3;
	public static final int ATTENDANCE_ON_EARNING_TOTAL = 4;
	public static final int ATTENDANCE_ON_SUBTOTAL = 5;
	public static final int ATTENDANCE_ON_RATE = 6;

	private int attendanceType;

	private long payHead;

	private int calculationPeriod;

	private int perDayCalculationBasis;

	private long leaveWithPay;

	/**
	 * Will be used if leaveWithPay does not Exist
	 */
	private long leaveWithoutPay;

	/**
	 * Will be used if perDayCalculationBasis is
	 * PER_DAY_CALCULATION_USER_DEFINED_CALANDAR
	 */
	private ClientAttendanceOrProductionType userDefinedCalendar;

	public int getCalculationPeriod() {
		return calculationPeriod;
	}

	public void setCalculationPeriod(int calculationPeriod) {
		this.calculationPeriod = calculationPeriod;
	}

	public int getPerDayCalculationBasis() {
		return perDayCalculationBasis;
	}

	public void setPerDayCalculationBasis(int perDayCalculationBasis) {
		this.perDayCalculationBasis = perDayCalculationBasis;
	}

	public long getLeaveWithPay() {
		return leaveWithPay;
	}

	public void setLeaveWithPay(long leaveWithPay) {
		this.leaveWithPay = leaveWithPay;
	}

	public long getLeaveWithoutPay() {
		return leaveWithoutPay;
	}

	public void setLeaveWithoutPay(long leaveWithoutPay) {
		this.leaveWithoutPay = leaveWithoutPay;
	}

	@Override
	public AccounterCoreType getObjectType() {
		return AccounterCoreType.ATTENDANCE_PAY_HEAD;
	}

	public ClientAttendanceOrProductionType getUserDefinedCalendar() {
		return userDefinedCalendar;
	}

	public void setUserDefinedCalendar(
			ClientAttendanceOrProductionType userDefinedCalendar) {
		this.userDefinedCalendar = userDefinedCalendar;
	}

	public int getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(int attendanceType) {
		this.attendanceType = attendanceType;
	}

	public long getPayhead() {
		return payHead;
	}

	public void setPayhead(long payHead) {
		this.payHead = payHead;
	}

	public static String getAttendanceType(int attendanceType) {
		AccounterMessages messages = Accounter.getMessages();
		switch (attendanceType) {
		case LEAVE_WITH_PAY:
			return messages.leaveWithPay();

		case LEAVE_WITHOUT_PAY:
			return messages.leaveWithoutPay();

		case ATTENDANCE_ON_PAYHEAD:
			return messages.payhead();

		case ATTENDANCE_ON_EARNING_TOTAL:
			return messages.onEarningTotal();

		case ATTENDANCE_ON_SUBTOTAL:
			return messages.onSubTotal();

		case ATTENDANCE_ON_RATE:
			return messages.rate();

		default:
			break;
		}
		return null;
	}
}