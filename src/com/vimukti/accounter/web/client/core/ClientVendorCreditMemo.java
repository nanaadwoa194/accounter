package com.vimukti.accounter.web.client.core;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.zefer.html.doc.v;

@SuppressWarnings("serial")
public class ClientVendorCreditMemo extends ClientTransaction {

	long vendor;

	ClientContact contact;

	String phone;

	String accountsPayable;

	double balanceDue = 0D;

	/**
	 * @return the version
	 */
	@Override
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the vendor
	 */
	public long getVendor() {
		return this.vendor;
	}

	/**
	 * @param vendor
	 *            the vendor to set
	 */
	public void setVendor(long vendorId) {
		this.vendor = vendorId;
	}

	/**
	 * @return the contact
	 */
	public ClientContact getContact() {
		return null;
	}

	public double getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(ClientContact contactId) {
		this.contact = contactId;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccountsPayable() {
		return accountsPayable;
	}

	public void setAccountsPayable(String accountsPayableId) {
		this.accountsPayable = accountsPayableId;
	}

	@Override
	public String getDisplayName() {
		return getName();
	}

	@Override
	public String getName() {

		return Utility.getTransactionName(getType());
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

		return "ClientVendorCreditMemo";
	}

	@Override
	public AccounterCoreType getObjectType() {
		return AccounterCoreType.VENDORCREDITMEMO;
	}

	public ClientVendorCreditMemo clone() {
		ClientVendorCreditMemo vendorCreditMemo = (ClientVendorCreditMemo) this
				.clone();
		vendorCreditMemo.contact = this.contact;
		vendorCreditMemo.creditsAndPayments = this.creditsAndPayments;

		List<ClientEntry> entries = new ArrayList<ClientEntry>();
		for (ClientEntry clientEntry : this.entry) {
			entries.add(clientEntry.clone());
		}
		vendorCreditMemo.entry = entries;

		// transactionItems list
		List<ClientTransactionItem> transactionItems = new ArrayList<ClientTransactionItem>();
		for (ClientTransactionItem clientTransactionItem : this.transactionItems) {
			transactionItems.add(clientTransactionItem.clone());
		}
		vendorCreditMemo.transactionItems = transactionItems;

		// transactionMakeDeposit list
		List<ClientTransactionMakeDeposit> transactionMakeDeposit = new ArrayList<ClientTransactionMakeDeposit>();
		for (ClientTransactionMakeDeposit clientTransactionMakeDeposit : this.transactionMakeDeposit) {
			transactionMakeDeposit.add(clientTransactionMakeDeposit.clone());
		}
		vendorCreditMemo.transactionMakeDeposit = transactionMakeDeposit;

		// transactionPayBill list
		List<ClientTransactionPayBill> transactionPayBillList = new ArrayList<ClientTransactionPayBill>();
		for (ClientTransactionPayBill clientTransactionPayBill : this.transactionPayBill) {
			transactionPayBillList.add(clientTransactionPayBill.clone());
		}
		vendorCreditMemo.transactionPayBill = transactionPayBillList;

		// transactionReceivePayment list
		List<ClientTransactionReceivePayment> transactionReceivePaymentList = new ArrayList<ClientTransactionReceivePayment>();
		for (ClientTransactionReceivePayment clientTransactionReceivePayment : this.transactionReceivePayment) {
			transactionReceivePaymentList.add(clientTransactionReceivePayment
					.clone());
		}
		vendorCreditMemo.transactionReceivePayment = transactionReceivePaymentList;

		// transactionIssuePayment list
		List<ClientTransactionIssuePayment> transactionIssuePayment = new ArrayList<ClientTransactionIssuePayment>();
		for (ClientTransactionIssuePayment clientTransactionIssuePayment : this.transactionIssuePayment) {
			transactionIssuePayment.add(clientTransactionIssuePayment.clone());
		}
		vendorCreditMemo.transactionIssuePayment = transactionIssuePayment;

		// transactionPaySalestax list
		List<ClientTransactionPaySalesTax> transactionPaySalesTax = new ArrayList<ClientTransactionPaySalesTax>();
		for (ClientTransactionPaySalesTax clientTransactionPaySalesTax : this.transactionPaySalesTax) {
			transactionPaySalesTax.add(clientTransactionPaySalesTax.clone());
		}
		vendorCreditMemo.transactionPaySalesTax = transactionPaySalesTax;

		return vendorCreditMemo;
	}
}
