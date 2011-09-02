package com.vimukti.accounter.web.client.ui.serverreports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vimukti.accounter.web.client.Global;
import com.vimukti.accounter.web.client.core.ClientAccount;
import com.vimukti.accounter.web.client.core.ClientFinanceDate;
import com.vimukti.accounter.web.client.core.ClientLocation;
import com.vimukti.accounter.web.client.core.reports.ProfitAndLossByLocation;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.reports.IFinanceReport;
import com.vimukti.accounter.web.client.ui.reports.ISectionHandler;
import com.vimukti.accounter.web.client.ui.reports.Section;

public class ProfitAndLossByLocationServerReport extends
		AbstractFinaneReport<ProfitAndLossByLocation> {
	protected List<String> sectiontypes = new ArrayList<String>();
	protected List<String> types = new ArrayList<String>();
	private String curentParent;

	protected Double totalincome = 0.0D;
	protected Double totalCGOS = 0.0D;
	protected Double grosProft = 0.0D;
	protected Double totalexpese = 0.0D;
	protected Double netIncome = 0.0D;
	protected Double otherIncome = 0.0D;
	protected Double otherExpense = 0.0D;
	protected Double otherNetIncome = 0.0D;

	protected Double totalincome2 = 0.0D;
	protected Double totalCGOS2 = 0.0D;
	protected Double grosProft2 = 0.0D;
	protected Double totalexpese2 = 0.0D;
	protected Double netIncome2 = 0.0D;
	protected Double otherIncome2 = 0.0D;
	protected Double otherExpense2 = 0.0D;
	protected Double otherNetIncome2 = 0.0D;
	private double rowTotal = 0;
	public static ArrayList<ClientLocation> locations = null;
	public static int noColumns = 0;

	public ProfitAndLossByLocationServerReport(
			IFinanceReport<ProfitAndLossByLocation> profitAndLossByLocationReport) {
		this.reportView = profitAndLossByLocationReport;

	}

	@Override
	public String[] getDynamicHeaders() {
		String[] headers = new String[noColumns];
		headers[0] = "Category Number  ";
		for (int i = 0; i < locations.size(); i++) {
			headers[i + 1] = locations.get(i).getLocationName();
		}
		headers[noColumns - 1] = "Total";
		return headers;
	}

	@Override
	public String getTitle() {
		return getConstants().profitAndLoss() + "  By  "
				+ Accounter.messages().location(Global.get().Location());
	}

	@Override
	public String[] getColunms() {
		String[] headers = new String[noColumns];
		headers[0] = "Category Number ";
		for (int i = 0; i < locations.size(); i++) {
			headers[i + 1] = locations.get(i).getLocationName();
		}
		headers[noColumns - 1] = "Total";
		return headers;
	}

	@Override
	public int[] getColumnTypes() {
		int[] columnTypes = new int[noColumns];
		columnTypes[0] = COLUMN_TYPE_TEXT;
		for (int i = 1; i < noColumns - 1; i++) {
			columnTypes[i] = COLUMN_TYPE_AMOUNT;
		}
		columnTypes[noColumns - 1] = COLUMN_TYPE_AMOUNT;
		return columnTypes;
	}

	@Override
	public void processRecord(ProfitAndLossByLocation record) {

		if (this.handler == null)
			initHandler();
		if (sectionDepth == 0) {
			addTypeSection("", getConstants().netOrdinaryIncome());
		}
		addOrdinaryIncomeOrExpenseTypes(record);

		if (closePrevSection(record.getParentAccount() == 0 ? record
				.getAccountName() : getAccountNameById(record
				.getParentAccount()))) {
			processRecord(record);
		} else {
			addSection(record);
			return;
		}
	}

	@Override
	public int getColumnWidth(int index) {
		return -1;
		// switch (index) {
		// case 0:
		// return 150;
		// case 2:
		// return 135;
		// case 3:
		// return 95;
		// case 4:
		// return 135;
		// case 5:
		// return 90;
		// }

	}

	public String getAccountNameById(long id) {
		for (ProfitAndLossByLocation balance : this.records)
			if (balance.getAccountId() == id)
				return balance.getAccountName();
		return null;
	}

	public void addTypeSection(String title, String bottomTitle) {
		if (!sectiontypes.contains(title)) {
			int[] nocolumn = new int[noColumns - 1];
			for (int i = 1; i < noColumns; i++) {
				nocolumn[i - 1] = i;
			}
			addSection(new String[] { title }, new String[] { bottomTitle },
					nocolumn);
			types.add(title);
			sectiontypes.add(title);
		}
	}

	public void addTypeSection(String sectionType, String title,
			String bottomTitle) {
		if (!sectiontypes.contains(sectionType)) {
			int[] nocolumn = new int[noColumns - 1];
			for (int i = 1; i < noColumns; i++) {
				nocolumn[i - 1] = i;
			}
			addSection(new String[] { title }, new String[] { bottomTitle },
					nocolumn);
			types.add(title);
			sectiontypes.add(sectionType);
		}
	}

	private void initHandler() {
		intivalues();
		this.handler = new ISectionHandler<ProfitAndLossByLocation>() {

			@Override
			public void OnSectionAdd(Section<ProfitAndLossByLocation> section) {
				if (section.title.equals(getConstants().grossProfit())) {
					section.data[0] = "";
				}
			}

			@Override
			public void OnSectionEnd(Section<ProfitAndLossByLocation> section) {
				if (section.title.equals(getConstants().income())) {
					totalincome = Double.valueOf(section.data[noColumns - 1]
							.toString());
				}
				if (section.title.equals(getConstants().costOfGoodSold())) {
					totalCGOS = Double.valueOf(section.data[noColumns - 1]
							.toString());
				}
				if (section.title.equals(getConstants().otherExpense())) {
					otherExpense = Double.valueOf(section.data[noColumns - 1]
							.toString());
				}
				if (section.footer.equals(getConstants().grossProfit())) {
					grosProft = totalincome - totalCGOS - otherExpense;
					section.data[noColumns - 1] = grosProft;
				}
				if (section.title.equals(getConstants().expense())) {
					totalexpese = (Double) section.data[noColumns - 1];
				}
				if (section.footer.equals(getConstants().netOrdinaryIncome())) {
					netIncome = grosProft - totalexpese;
					section.data[noColumns - 1] = netIncome;
				}
				if (section.title.equals(getConstants().otherIncome())) {
					otherIncome = Double.valueOf(section.data[noColumns - 1]
							.toString());
				}
				if (section.title.equals(getConstants().otherIncomeOrExpense())) {
					otherNetIncome = otherIncome - otherExpense;
					section.data[noColumns - 1] = otherNetIncome;
				}
			}
		};
	}

	private void intivalues() {
		totalincome = 0.0D;
		totalCGOS = 0.0D;
		grosProft = 0.0D;
		totalexpese = 0.0D;
		netIncome = 0.0D;
		otherIncome = 0.0D;
		otherExpense = 0.0D;
		otherNetIncome = 0.0D;
	}

	@Override
	public Object getColumnData(ProfitAndLossByLocation record, int columnIndex) {

		if (columnIndex == 0) {
			rowTotal = 0;
			return record.getAccountNumber() + "-" + record.getAccountName();
		} else if (columnIndex == noColumns - 1) {
			return rowTotal;
		} else {
			long location_id = locations.get(columnIndex - 1).getID();
			Map<Long, Double> map = record.getMap();
			Double value = map.get(location_id);
			if (value != null) {
				rowTotal += value;
			}
			return value;
		}
	}

	@Override
	public void resetVariables() {
		this.types.clear();
		this.sectiontypes.clear();
		curentParent = "";
	}

	@Override
	public boolean isWiderReport() {
		return true;
	}

	@Override
	public ClientFinanceDate getStartDate(ProfitAndLossByLocation obj) {
		return obj.getStartDate();
	}

	@Override
	public ClientFinanceDate getEndDate(ProfitAndLossByLocation obj) {
		return obj.getStartDate();
	}

	@Override
	public void makeReportRequest(long start, long end) {
		// TODO Auto-generated method stub
	}

	public void addOrdinaryIncomeOrExpenseTypes(ProfitAndLossByLocation record) {

		if (record.getAccountType() == ClientAccount.TYPE_INCOME
				|| record.getAccountType() == ClientAccount.TYPE_COST_OF_GOODS_SOLD) {
			if (!sectiontypes.contains(getConstants().grossProfit())) {
				addTypeSection(getConstants().grossProfit(), "", getConstants()
						.grossProfit());

			}
			if (record.getAccountType() == ClientAccount.TYPE_INCOME)
				if (!sectiontypes.contains(getConstants().income())) {

					addTypeSection(getConstants().income(), getConstants()
							.incomeTotals());
				}
			if (record.getAccountType() == ClientAccount.TYPE_COST_OF_GOODS_SOLD)
				if (!sectiontypes.contains(getConstants().costOfGoodSold())) {
					closeOtherSections();
					closeSection(types.indexOf(getConstants().income()));
					addTypeSection(getConstants().costOfGoodSold(),
							getConstants().cogsTotal());
				}
		}

		if (record.getAccountType() == ClientAccount.TYPE_OTHER_EXPENSE) {

			if (!sectiontypes.contains(getConstants().otherExpense())) {
				for (int i = types.size() - 2; i > 0; i--) {
					closeSection(i);
				}
				addTypeSection(getConstants().otherExpense(), getConstants()
						.otherExpenseTotals());
			}
		}

		if (record.getAccountType() == ClientAccount.TYPE_EXPENSE) {

			if (!sectiontypes.contains(getConstants().expense())) {
				closeAllSection();
				addTypeSection(getConstants().expense(), getConstants()
						.expenseTotals());
			}
		}
	}

	public void closeAllSection() {
		for (int i = types.size() - 1; i > 0; i--) {
			closeSection(i);
		}
	}

	public void closeOtherSections() {
		for (int i = types.size() - 1; i > 0; i--) {
			closePrevSection(types.get(i));
		}
	}

	public boolean closePrevSection(String title) {
		if (curentParent != null && curentParent != "")
			if (!title.equals(curentParent)) {
				if (!sectiontypes.contains(curentParent)) {
					types.remove(types.size() - 1);
					if (types.size() > 0) {
						curentParent = types.get(types.size() - 1);
					}
					if (sectionDepth > 0)
						endSection();
					return true;
				}
			}
		return false;
	}

	public boolean addSection(ProfitAndLossByLocation record) {
		if (isParent(record)) {
			types.add(record.getAccountName());
			curentParent = record.getAccountName();
			int[] nocolumn = new int[noColumns - 1];
			for (int i = 1; i < noColumns; i++) {
				nocolumn[i - 1] = i;
			}
			addSection(
					record.getAccountNumber() + "-" + record.getAccountName(),
					record.getAccountName() + "  " + getConstants().total(),
					nocolumn);
			return true;
		}
		return false;
	}

	public boolean isParent(ProfitAndLossByLocation record) {
		for (ProfitAndLossByLocation balance : this.records) {
			if (balance.getParentAccount() != 0) {
				if (balance.getParentAccount() == record.getAccountId())
					return true;
			}
		}
		return false;
	}

	public void closeSection(int index) {
		if (index >= 0) {
			types.remove(index);
			curentParent = "";
			endSection();
		}
	}

	protected String getPreviousReportDateRange(Object object) {
		return ((ProfitAndLossByLocation) object).getDateRange();
	}

	@Override
	protected ClientFinanceDate getPreviousReportStartDate(Object object) {
		return ((ProfitAndLossByLocation) object).getStartDate();
	}

	@Override
	protected ClientFinanceDate getPreviousReportEndDate(Object object) {
		return ((ProfitAndLossByLocation) object).getEndDate();
	}

	public ClientFinanceDate getLastMonth(ClientFinanceDate date) {
		int month = date.getMonth() - 1;
		int year = date.getYear();

		int lastDay;
		switch (month) {
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			lastDay = 31;
			break;
		case 1:
			if (year % 4 == 0 && year % 100 == 0)
				lastDay = 29;
			else
				lastDay = 28;
			break;

		default:
			lastDay = 30;
			break;
		}
		return new ClientFinanceDate(date.getYear(), date.getMonth() - 1,
				lastDay);
		// return lastDay;
	}
}
