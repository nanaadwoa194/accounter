package com.vimukti.accounter.web.client.ui.settings;

import com.vimukti.accounter.web.client.core.ClientStockTransfer;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.externalization.AccounterConstants;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.UIUtils;
import com.vimukti.accounter.web.client.ui.core.Action;
import com.vimukti.accounter.web.client.ui.core.ActionFactory;
import com.vimukti.accounter.web.client.ui.core.BaseListView;

public class WarehouseListView extends BaseListView<ClientStockTransfer> {

	private AccounterConstants customerConstants;

	@Override
	public void init() {
		customerConstants = Accounter.constants();
		super.init();
	}

	@Override
	public void deleteFailed(Throwable caught) {

	}

	@Override
	public void deleteSuccess(Boolean result) {

	}

	@Override
	public void processupdateView(IAccounterCore core, int command) {

	}

	@Override
	protected String getViewTitle() {
		return Accounter.constants().warehouseList();
	}

	@Override
	public void fitToSize(int height, int width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEdit() {

	}

	@Override
	public void printPreview() {

	}

	@Override
	public void print() {

	}

	@Override
	public void saveSuccess(IAccounterCore object) {
		if (object != null) {
			super.saveSuccess(object);
			ActionFactory.getInvoiceBrandingAction().run(null, false);
		} else
			saveFailed(new Exception(Accounter.constants().failed()));
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
		return Accounter.constants().warehouseList();
	}

	@Override
	public void updateInGrid(ClientStockTransfer objectTobeModified) {
		// currently not using

	}
}
