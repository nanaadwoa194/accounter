package com.vimukti.accounter.web.client.ui.combo;

import java.util.ArrayList;
import java.util.List;

import com.vimukti.accounter.web.client.core.ClientCompany;
import com.vimukti.accounter.web.client.core.ClientTAXCode;
import com.vimukti.accounter.web.client.core.ClientTAXGroup;
import com.vimukti.accounter.web.client.core.ClientTAXItem;
import com.vimukti.accounter.web.client.core.ClientTAXItemGroup;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.DataUtils;
import com.vimukti.accounter.web.client.ui.core.Action;
import com.vimukti.accounter.web.client.ui.core.CustomersActionFactory;
import com.vimukti.accounter.web.client.ui.customers.TaxDialogAction;
import com.vimukti.accounter.web.client.ui.vat.VatActionFactory;

public class TAXCodeCombo extends CustomCombo<ClientTAXCode> {
	private boolean isSales;

	public TAXCodeCombo(String title, boolean isSales) {
		super(title);
		this.isSales = isSales;
		initCombo(TAXCodesForSalesOrPurchase(getCompany().getActiveTaxCodes()));
	}

	public TAXCodeCombo(String title, boolean isAddNewRequired, boolean isSales) {
		super(title, isAddNewRequired, 1);
		this.isSales = isSales;
		initCombo(TAXCodesForSalesOrPurchase(getCompany().getActiveTaxCodes()));
	}

	@Override
	public String getDefaultAddNewCaption() {
		if (getCompany().getAccountingType() == ClientCompany.ACCOUNTING_TYPE_US)
			return comboConstants.addNewItem();
		else
			return comboConstants.newVatCode();
	}

	@Override
	public String getDisplayName(ClientTAXCode object) {
		String displayName;
		ClientTAXItemGroup vatGroup;
		if (object != null) {
			displayName = object.getName() != null ? object.getName() : "";
			if (isSales) {
				vatGroup = ((ClientTAXItemGroup) Accounter.getCompany()
						.getTAXItemGroup(object.getTAXItemGrpForSales()));

			} else {
				vatGroup = ((ClientTAXItemGroup) Accounter.getCompany()
						.getTAXItemGroup(object.getTAXItemGrpForPurchases()));
			}

			if (vatGroup instanceof ClientTAXItem) {
				// The selected one is VATItem,so get 'VATRate' from
				// 'VATItem'
				if (vatGroup != null)
					displayName += " - "
							+ ((ClientTAXItem) vatGroup).getTaxRate();
			} else {
				// The selected one is VATGroup,so get 'GroupRate' from
				// 'VATGroup'
				if (vatGroup != null)
					displayName += " - "
							+ ((ClientTAXGroup) vatGroup).getGroupRate();
			}
			if (vatGroup != null && vatGroup.isPercentage())
				displayName += "%";
			return displayName;
		} else
			return "";
	}

	@Override
	public void onAddNew() {
		if (getCompany().getAccountingType() == ClientCompany.ACCOUNTING_TYPE_UK) {
			Action action = VatActionFactory.getNewTAXCodeAction();
			action.setActionSource(this);

			action.run(null, true);
		} else {
			TaxDialogAction action1 = CustomersActionFactory.getTaxAction();
			action1.setActionSource(this);
			action1.run(createAddNewCallBack(), this, null, true);
		}
	}

	@Override
	public SelectItemType getSelectItemType() {
		return SelectItemType.TAX_CODE;
	}

	@Override
	protected String getColumnData(ClientTAXCode object, int row, int col) {
		switch (col) {
		case 0:
			return getDisplayName(object);
		case 1:
			if (isSales) {
				ClientTAXItem item = getCompany().getTaxItem(
						object.getTAXItemGrpForSales());
				return DataUtils.getAmountAsString(item.getTaxRate()) + "%";
			} else {
				ClientTAXItem item = getCompany().getTaxItem(
						object.getTAXItemGrpForPurchases());
				return DataUtils.getAmountAsString(item.getTaxRate()) + "%";
			}
		}
		return null;
	}

	protected List<ClientTAXCode> TAXCodesForSalesOrPurchase(
			List<ClientTAXCode> activeTaxCodes) {

		List<ClientTAXCode> taxCodeList = new ArrayList<ClientTAXCode>();
		for (ClientTAXCode taxCode : activeTaxCodes) {
			if (isSales) {
				if (taxCode.getTAXItemGrpForPurchases() == 0
						|| ((taxCode.getTAXItemGrpForSales() != 0) && (taxCode
								.getTAXItemGrpForPurchases() != 0))) {
					taxCodeList.add(taxCode);
				}
			} else {
				if (taxCode.getTAXItemGrpForSales() == 0
						|| ((taxCode.getTAXItemGrpForPurchases() != 0) && (taxCode
								.getTAXItemGrpForSales() != 0))) {
					taxCodeList.add(taxCode);
				}
			}
		}
		return taxCodeList;
	}
}
