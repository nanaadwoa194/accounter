package com.vimukti.accounter.developer.api.process.lists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vimukti.accounter.core.Transaction;
import com.vimukti.accounter.web.client.core.ClientEstimate;

public class ChargesProcessor extends ListProcessor {
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		initTransactionList(req, resp);

		int viewType = getViewType();
		List<?> resultList = service.getEstimates(ClientEstimate.CHARGES,
				viewType, from.getDate(), to.getDate(), start, length);

		sendResult(resultList);
	}

	private int getViewType() {
		int type = 0;
		if (viewName.equalsIgnoreCase("open")) {
			type = Transaction.VIEW_OPEN;
		} else if (viewName.equalsIgnoreCase("Expired")) {
			type = Transaction.VIEW_VOIDED;
		} else if (viewName.equalsIgnoreCase("All")) {
			type = Transaction.VIEW_OVERDUE;
		} else if (viewName.equalsIgnoreCase("Drafts")) {
			type = Transaction.VIEW_ALL;
		}

		return type;
	}

}
