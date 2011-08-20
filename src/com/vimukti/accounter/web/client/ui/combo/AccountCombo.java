package com.vimukti.accounter.web.client.ui.combo;

import java.util.List;

import com.vimukti.accounter.web.client.Global;
import com.vimukti.accounter.web.client.core.ClientAccount;
import com.vimukti.accounter.web.client.core.Utility;
import com.vimukti.accounter.web.client.ui.company.NewAccountAction;
import com.vimukti.accounter.web.client.ui.core.ActionCallback;
import com.vimukti.accounter.web.client.ui.core.ActionFactory;

public abstract class AccountCombo extends CustomCombo<ClientAccount> {

	public static final int DEPOSIT_IN_ACCOUNT = 1;
	public static final int PAY_FROM_COMBO = 2;
	public static final int GRID_ACCOUNTS_COMBO = 3;
	public static final int CASH_BACK_ACCOUNTS_COMBO = 4;
	public static final int INCOME_AND_EXPENSE_ACCOUNTS_COMBO = 5;
	public static final int BANK_ACCOUNTS_COMBO = 6;
	public static final int FIXEDASSET_COMBO = 7;
	public static final int DEPRECIATION_COMBO = 8;
	public static final int DEBIT_COMBO = 9;

	private List<ClientAccount> accountList;
	private List<Integer> accounTypes;
	private boolean useAccountNumbers;
	
	public AccountCombo(String title) {
		super(title, true, 3);
	}

	
	public AccountCombo(String title, boolean b) {
		super(title, b, 3);
		this.useAccountNumbers = Global.get().preferences().getUseAccountNumbers();
	}

	protected abstract List<ClientAccount> getAccounts();

	@Override
	public String getDefaultAddNewCaption() {

		return super.comboConstants.newAccount();
	}

	public void init() {
		accountList = getAccounts();
		initCombo(accountList);
	}

	@Override
	protected String getDisplayName(ClientAccount object) {
		if (object != null)
			return object.getDisplayName() != null ? object.getDisplayName()
					: object.getName();
		else
			return "";
	}

	@Override
	public void onAddNew() {
		NewAccountAction action = ActionFactory.getNewAccountAction();
		action.setCallback(new ActionCallback<ClientAccount>() {

			@Override
			public void actionResult(ClientAccount result) {
				addItemThenfireEvent(result);
			}
		});

		action.run(null, true);

	}

	/**
	 * @param accountList
	 * 
	 */
	public void setAccountTypes(List<Integer> accounTypes) {
		this.accounTypes = accounTypes;
	}

	/**
	 * @return the accountTypes
	 */
	public List<Integer> getAccountTypes() {
		return accounTypes;
	}

	@Override
	protected String getColumnData(ClientAccount object, int row, int col) {

		switch (col) {
		case 0:
			if (useAccountNumbers) {
				return object.getNumber();
			} else {
				return null;
			}
		case 1:
			return getDisplayName(object);
		case 2:
			return Utility.getAccountTypeString(object.getType());
		default:
			break;
		}
		return null;
	}
}
