package com.vimukti.accounter.developer.api.process;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vimukti.accounter.developer.api.process.reports.ReportProcessor;
import com.vimukti.accounter.web.client.core.ClientVendor;

public class TransactionHistoryVendorsProcessor extends ReportProcessor {

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		init(req, resp);
		List<ClientVendor> result = service.getTransactionHistoryVendors(
				clientFinanceStartDate, clientFinanceEndDate);

		sendResult(result);
	}
}