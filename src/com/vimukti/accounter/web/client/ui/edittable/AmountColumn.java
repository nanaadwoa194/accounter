package com.vimukti.accounter.web.client.ui.edittable;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.DataUtils;
import com.vimukti.accounter.web.client.ui.core.ICurrencyProvider;

public abstract class AmountColumn<T> extends TextEditColumn<T> {

	ICurrencyProvider currencyProvider;

	public AmountColumn(ICurrencyProvider currencyProvider,
			boolean updateFromGUI) {
		super(updateFromGUI);
		this.currencyProvider = currencyProvider;
	}

	@Override
	protected String getValue(T row) {
		Double amount = getAmount(row);
		if (amount == null) {
			return "";
		}
		return DataUtils.getAmountAsString(amount);
	}

	protected abstract Double getAmount(T row);

	@Override
	public void setValue(T row, String value) {
		try {
			Double amount = null;
			if (value != null && !value.equals("")) {
				amount = DataUtils.getAmountStringAsDouble(value);
			}
			setAmount(row, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void setAmount(T row, Double value);

	@Override
	protected void configure(TextBoxBase textBox) {
		super.configure(textBox);
		textBox.addStyleName("amount");
	}

	@Override
	public void setTable(EditTable<T> table) {
		super.setTable(table);
		FlexTable flexTable = (FlexTable) table.getWidget();
		flexTable.getCellFormatter().addStyleName(0, flexTable.getCellCount(0),
				"amount");
	}

	protected String getColumnNameWithCurrency(String name) {
		String currencyName = Accounter.getCompany().getPrimaryCurrency()
				.getFormalName();
		if (currencyProvider.getTransactionCurrency() != null) {
			currencyName = currencyProvider.getTransactionCurrency()
					.getFormalName();
		}
		return Accounter.messages().nameWithCurrency(name, currencyName);
	}

	protected String getColumnNameWithBaseCurrency(String name) {
		String formalName = Accounter.getCompany().getPrimaryCurrency()
				.getFormalName();

		return Accounter.messages().nameWithCurrency(name, formalName);
	}
}
