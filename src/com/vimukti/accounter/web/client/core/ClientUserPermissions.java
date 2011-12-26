package com.vimukti.accounter.web.client.core;

public class ClientUserPermissions implements IAccounterCore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public static String BANK_RECONCILATION = "Bank Reconcilation";
	// public static String INVOICES_AND_EXPENSES = "Invoices And Expenses";
	// public static String EDIT_SYSTEM_SETTINGS = "Edit System Settings";
	// public static String VIEW_REPORTS = "View Reports";
	// public static String PUBLISH_REPORTS = "Publish Reports";
	// public static String LOCK_DATES = "Lock Dates";
	//
	// public static int TYPE_YES = 1;
	// public static int TYPE_NO = 3;
	// public static int TYPE_READ_ONLY = 2;

	int typeOfBankReconcilation;

	int typeOfInvoicesBills;

	int typeOfPayBillsPayments;

	int typeOfCompanySettingsLockDates;

	int typeOfViewReports;

	int typeOfMangeAccounts;

	int typeOfInventoryWarehouse;

	ClientUser user;

	private int version;

	public int getTypeOfBankReconcilation() {
		return typeOfBankReconcilation;
	}

	public void setTypeOfBankReconcilation(int typeOfBankReconcilation) {
		this.typeOfBankReconcilation = typeOfBankReconcilation;
	}

	public int getTypeOfInvoicesBills() {
		return typeOfInvoicesBills;
	}

	public void setTypeOfInvoicesBills(int typeOfInvoicesBills) {
		this.typeOfInvoicesBills = typeOfInvoicesBills;
	}

	public int getTypeOfPayBillsPayments() {
		return typeOfPayBillsPayments;
	}

	public void setTypeOfPayBillsPayments(int typeOfPayBillsPayments) {
		this.typeOfPayBillsPayments = typeOfPayBillsPayments;
	}

	public int getTypeOfCompanySettingsLockDates() {
		return typeOfCompanySettingsLockDates;
	}

	public void setTypeOfCompanySettingsLockDates(
			int typeOfCompanySettingsLockDates) {
		this.typeOfCompanySettingsLockDates = typeOfCompanySettingsLockDates;
	}

	public int getTypeOfViewReports() {
		return typeOfViewReports;
	}

	public void setTypeOfViewReports(int typeOfViewReports) {
		this.typeOfViewReports = typeOfViewReports;
	}

	public int getTypeOfManageAccounts() {
		return typeOfMangeAccounts;
	}

	public void setTypeOfManageAccounts(int typeOfMangeAccounts) {
		this.typeOfMangeAccounts = typeOfMangeAccounts;
	}

	public int getTypeOfInventoryWarehouse() {
		return typeOfInventoryWarehouse;
	}

	public void setTypeOfInventoryWarehouse(int typeOfInventoryWarehouse) {
		this.typeOfInventoryWarehouse = typeOfInventoryWarehouse;
	}

	public ClientUser getUser() {
		return user;
	}

	public void setUser(ClientUser user) {
		this.user = user;
	}

	@Override
	public String getClientClassSimpleName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccounterCoreType getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(long id) {
		// TODO Auto-generated method stub

	}

	public ClientUserPermissions clone() {
		ClientUserPermissions clientUserPreferencesClone = (ClientUserPermissions) this
				.clone();
		clientUserPreferencesClone.user = this.user.clone();

		return clientUserPreferencesClone;
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public void setVersion(int version) {
		this.version = version;
	}

}
