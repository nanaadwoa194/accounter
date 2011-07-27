package com.vimukti.accounter.web.client.ui.vendors;

import com.google.gwt.core.client.GWT;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.core.Lists.PayeeList;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.core.Action;
import com.vimukti.accounter.web.client.ui.core.BaseListView;
import com.vimukti.accounter.web.client.ui.customers.CustomersMessages;
import com.vimukti.accounter.web.client.ui.grids.CustomerListGrid;
import com.vimukti.accounter.web.client.ui.settings.SettingsActionFactory;

public class WarehouseListView extends BaseListView<PayeeList> {

	private CustomersMessages customerConstants;

	@Override
	public void init() {
		customerConstants = GWT.create(CustomersMessages.class);
		super.init();
	}

	@Override
	public void deleteFailed(Throwable caught) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSuccess(Boolean result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processupdateView(IAccounterCore core, int command) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getViewTitle() {
		return Accounter.getSettingsMessages().invoiceBranding();
	}

	@Override
	public void fitToSize(int height, int width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEdit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printPreview() {
		// TODO Auto-generated method stub

	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveSuccess(IAccounterCore object) {
		if (object != null) {
			super.saveSuccess(object);
			SettingsActionFactory.getInvoiceBrandingAction().run(null, false);
		} else
			saveFailed(new Exception(Accounter.getCompanyMessages().failed()));
	}

	@Override
	protected void initGrid() {
		grid = new WarehouseListGrid(false);
		grid.init();
	}

	@Override
	protected String getListViewHeading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Action getAddNewAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getAddNewLabelString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateInGrid(PayeeList objectTobeModified) {
		// TODO Auto-generated method stub

	}
}
