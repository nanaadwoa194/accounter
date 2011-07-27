package com.vimukti.accounter.web.client.core;

@SuppressWarnings("serial")
public class ClientTransactionCreditsAndPayments implements IAccounterCore {

	long id;

	long date;

	String memo;

	double amountToUse;

	ClientTransactionReceivePayment transactionReceivePayment;

	ClientTransactionPayBill transactionPayBill;

	ClientCreditsAndPayments creditsAndPayments;

	boolean isVoid = false;

	int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public double getAmountToUse() {
		return amountToUse;
	}

	public void setAmountToUse(double amountToUse) {
		this.amountToUse = amountToUse;
	}

	public ClientTransactionReceivePayment getTransactionReceivePayment() {
		return transactionReceivePayment;
	}

	public void setTransactionReceivePayment(
			ClientTransactionReceivePayment transactionReceivePayment) {
		this.transactionReceivePayment = transactionReceivePayment;
	}

	public ClientTransactionPayBill getTransactionPayBill() {
		return transactionPayBill;
	}

	public void setTransactionPayBill(
			ClientTransactionPayBill transactionPayBill) {
		this.transactionPayBill = transactionPayBill;
	}

	/**
	 * @return the creditsAndPayments
	 */
	public ClientCreditsAndPayments getCreditsAndPayments() {
		return creditsAndPayments;
	}

	/**
	 * @param creditsAndPayments
	 *            the creditsAndPayments to set
	 */

	/**
	 * @return the isVoid
	 */
	public boolean getIsVoid() {
		return isVoid;
	}

	/**
	 * @param isVoid
	 *            the isVoid to set
	 */
	public void setIsVoid(boolean isVoid) {
		this.isVoid = isVoid;
	}

	public void setCreditsAndPayments(
			ClientCreditsAndPayments creditsAnsPayments) {
		this.creditsAndPayments = creditsAnsPayments;

	}

	@Override
	public String getDisplayName() {
		// its not using any where
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccounterCoreType getObjectType() {

		return null;
	}

	@Override
	public long getID() {
		return this.id;
	}

	@Override
	public void setID(long id) {
		this.id = id;

	}

	@Override
	public String getClientClassSimpleName() {

		return "ClientTransactionCreditsAndPayments";
	}

	// private ClientTransaction getTransaction() {
	//
	// if (this.transactionReceivePaymentId != null) {
	// return this.transactionReceivePaymentId.getReceivePayment();
	// } else {
	//
	// return this.transactionPayBillId.getPayBill();
	// }
	//
	// }
	//
	// public void makeVoid() {
	// this.creditsAndPaymentsId.updateBalance(getTransaction(), -amountToUse);
	// this.setAmountToUse(0.0);
	// }

}
