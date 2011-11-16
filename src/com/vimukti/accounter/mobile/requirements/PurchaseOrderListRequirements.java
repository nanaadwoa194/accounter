package com.vimukti.accounter.mobile.requirements;

import com.vimukti.accounter.mobile.CommandList;
import com.vimukti.accounter.mobile.Record;
import com.vimukti.accounter.web.client.core.ClientTransaction;
import com.vimukti.accounter.web.client.core.Lists.PurchaseOrdersList;

public abstract class PurchaseOrderListRequirements extends
		ListRequirement<PurchaseOrdersList> {

	public PurchaseOrderListRequirements(String requirementName,
			String enterString, String recordName, boolean isOptional,
			boolean isAllowFromContext,
			ChangeListner<PurchaseOrdersList> listner) {
		super(requirementName, enterString, recordName, isOptional,
				isAllowFromContext, listner);
	}

	@Override
	protected String getEmptyString() {
		return getMessages().thereAreNo(getConstants().purchaseOrders());
	}

	@Override
	protected String getSetMessage() {
		return getMessages().hasSelected(getConstants().purchaseOrder());
	}

	@Override
	protected Record createRecord(PurchaseOrdersList value) {
		Record rec = new Record(value);
		if (value.getType() == ClientTransaction.TYPE_PURCHASE_ORDER) {
			rec.add("", getConstants().purchaseOrder());
			rec.add("", value.getPurchasePrice());
		}
		return rec;
	}

	@Override
	protected String getDisplayValue(PurchaseOrdersList value) {

		return value != null ? value.getVendorName() : "";
	}

	@Override
	protected void setCreateCommand(CommandList list) {
		list.add(getMessages().create(getConstants().purchaseOrder()));

	}

	@Override
	protected String getSelectString() {
		return getMessages().selectTypeOfThis(getConstants().purchaseOrder());
	}
}
