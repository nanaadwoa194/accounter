package com.vimukti.accounter.web.client.ui.customers;

import com.google.gwt.resources.client.ImageResource;
import com.vimukti.accounter.web.client.AccounterAsyncCallback;
import com.vimukti.accounter.web.client.Global;
import com.vimukti.accounter.web.client.core.ClientEstimate;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.MainFinanceWindow;
import com.vimukti.accounter.web.client.ui.core.AccounterAsync;
import com.vimukti.accounter.web.client.ui.core.Action;
import com.vimukti.accounter.web.client.ui.core.CreateViewAsyncCallback;

/**
 * 
 * @author kumar kasimala
 */

public class NewQuoteAction extends Action {

	protected QuoteView view;
	private int type;
	private String title;

	public NewQuoteAction(int type) {
		super();
		this.type = type;
		this.catagory = Global.get().Customer();

		if (type == ClientEstimate.QUOTES) {
			title = messages.quote();
		} else if (type == ClientEstimate.CHARGES) {
			title = messages.charge();
		} else if (type == ClientEstimate.CREDITS) {
			title = messages.credit();
		} else if (type == ClientEstimate.SALES_ORDER) {
			title = messages.salesOrder();
		}

	}

	public NewQuoteAction(ClientEstimate quote,
			AccounterAsyncCallback<Object> callback, int type) {
		super();
		this.catagory = Global.get().Customer();
		this.type = type;
	}

	@Override
	public void run() {
		runAsync(data, isDependent);

	}

	public void runAsync(final Object data, final Boolean isDependent) {

		AccounterAsync.createAsync(new CreateViewAsyncCallback() {

			@Override
			public void onCreated() {
				if (type == 0 && data != null) {
					type = ((ClientEstimate) data).getEstimateType();
					switch (type) {
					case ClientEstimate.QUOTES:
						title = messages.quote();
						break;
					case ClientEstimate.CHARGES:
						title = messages.charge();
						break;
					case ClientEstimate.CREDITS:
						title = messages.credit();
						break;
					case ClientEstimate.SALES_ORDER:
						title = messages.salesOrder();
					default:
						break;
					}
				}
				view = QuoteView.getInstance(type, title);

				MainFinanceWindow.getViewManager().showView(view, data,
						isDependent, NewQuoteAction.this);

			}

		});
	}

	// @Override
	// public ParentCanvas getView() {
	// return this.view;
	// }

	public ImageResource getBigImage() {
		// NOTHING TO DO.
		return null;
	}

	public ImageResource getSmallImage() {
		return Accounter.getFinanceMenuImages().newQuote();
	}

	// @Override
	// public String getImageUrl() {
	//
	// return "/images/new_quote.png";
	// }

	@Override
	public String getHistoryToken() {
		if (type == ClientEstimate.QUOTES) {
			return "newQuote";
		} else if (type == ClientEstimate.CHARGES) {
			return "newCharge";
		} else if (type == ClientEstimate.CREDITS) {
			return "newCredit";
		} else if (type == ClientEstimate.SALES_ORDER) {
			return "salesOrder";
		}
		return "";
	}

	@Override
	public String getHelpToken() {
		return "customer-quote";
	}

	@Override
	public String getText() {
		if (type == ClientEstimate.QUOTES) {
			title = messages.newQuote();
		} else if (type == ClientEstimate.CHARGES) {
			title = messages.newCharge();
		} else if (type == ClientEstimate.CREDITS) {
			title = messages.newCredit();
		} else if (type == ClientEstimate.SALES_ORDER) {
			title = messages.newSalesOrder();
		}
		return title;
	}

	@Override
	public String getViewName() {
		String viewName = "";
		if (type == ClientEstimate.QUOTES) {
			viewName = messages.quote();
		} else if (type == ClientEstimate.CHARGES) {
			viewName = messages.charge();
		} else if (type == ClientEstimate.CREDITS) {
			viewName = messages.credit();
		} else if (type == ClientEstimate.SALES_ORDER) {
			viewName = messages.salesOrder();
		}
		return viewName;
	}
}
