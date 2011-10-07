package com.vimukti.accounter.web.client.ui.customers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.resources.client.ImageResource;
import com.vimukti.accounter.web.client.AccounterAsyncCallback;
import com.vimukti.accounter.web.client.Global;
import com.vimukti.accounter.web.client.core.ClientInvoice;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.MainFinanceWindow;
import com.vimukti.accounter.web.client.ui.core.Action;

/**
 * 
 * @author Raj Vimal
 */

public class NewInvoiceAction extends Action {

	protected InvoiceView view;

	public NewInvoiceAction(String text) {
		super(text);
		this.catagory = Global.get().Customer();
	}

	public NewInvoiceAction(String text, ClientInvoice invoice,
			AccounterAsyncCallback<Object> callback) {
		super(text);
		this.catagory = Global.get().Customer();
	}

	@Override
	public void run() {
		runAsync(data, isDependent);
	}

	public void runAsync(final Object data, final Boolean isDependent) {
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				try {
					view = InvoiceView.getInstance();
					MainFinanceWindow.getViewManager().showView(view, data,
							isDependent, NewInvoiceAction.this);
				} catch (Exception e) {
					System.err.println(e);
				}

			}

			@Override
			public void onFailure(Throwable arg0) {
				Accounter
						.showError(Accounter.constants().unableToshowtheview());

			}
		});
	}

	// @Override
	// public ParentCanvas<?> getView() {
	// // NOTHING TO DO.
	// return null;
	// }

	public ImageResource getBigImage() {
		// NOTHING TO DO.
		return null;
	}

	public ImageResource getSmallImage() {
		return Accounter.getFinanceMenuImages().newInvoice();
	}

	// @Override
	// public String getImageUrl() {
	// return "/images/new_invoice.png";
	// }

	@Override
	public String getHistoryToken() {
		return "newInvoice";
	}

	@Override
	public String getHelpToken() {
		return "customer-invoice";
	}
}
