package com.vimukti.accounter.web.client.ui.payroll;

import com.google.gwt.resources.client.ImageResource;
import com.vimukti.accounter.web.client.core.ClientPayHead;
import com.vimukti.accounter.web.client.ui.HistoryTokens;
import com.vimukti.accounter.web.client.ui.MainFinanceWindow;
import com.vimukti.accounter.web.client.ui.core.AccounterAsync;
import com.vimukti.accounter.web.client.ui.core.Action;
import com.vimukti.accounter.web.client.ui.core.CreateViewAsyncCallback;

public class PayheadListAction extends Action<ClientPayHead> {

	@Override
	public String getText() {
		return messages.payheadList();
	}

	@Override
	public void run() {
		runAsync(data, isDependent);
	}

	private void runAsync(final ClientPayHead data, final boolean isDependent) {
		AccounterAsync.createAsync(new CreateViewAsyncCallback() {

			@Override
			public void onCreated() {
				PayheadListView view = new PayheadListView();
				MainFinanceWindow.getViewManager().showView(view, data,
						isDependent, PayheadListAction.this);
			}
		});
	}

	@Override
	public ImageResource getBigImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageResource getSmallImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHistoryToken() {
		return HistoryTokens.PAYHEADLIST;
	}

	@Override
	public String getHelpToken() {
		return "payhead-list";
	}

}