package com.vimukti.accounter.web.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.vimukti.accounter.web.client.core.ClientCompany;
import com.vimukti.accounter.web.client.core.ClientCurrency;
import com.vimukti.accounter.web.client.core.ClientInventoryAssemblyItem;
import com.vimukti.accounter.web.client.core.ClientItem;
import com.vimukti.accounter.web.client.core.ClientMeasurement;
import com.vimukti.accounter.web.client.core.ClientQuantity;
import com.vimukti.accounter.web.client.core.ListFilter;
import com.vimukti.accounter.web.client.core.ValidationResult;
import com.vimukti.accounter.web.client.ui.core.DecimalUtil;
import com.vimukti.accounter.web.client.ui.core.ICurrencyProvider;
import com.vimukti.accounter.web.client.ui.edittable.DeleteColumn;
import com.vimukti.accounter.web.client.ui.edittable.EditTable;
import com.vimukti.accounter.web.client.ui.edittable.InventoryTotalColumn;

public abstract class InventoryAssemblyItemTable extends
		EditTable<ClientInventoryAssemblyItem> implements ICurrencyProvider {

	double lineTotal;

	protected ICurrencyProvider currencyProvider;

	public InventoryAssemblyItemTable(ICurrencyProvider currencyProvider) {
		this.currencyProvider = currencyProvider;
	}

	protected abstract void updateNonEditableItems();

	@Override
	protected void initColumns() {
		final InventoryQuantityColumn quantityColumn = new InventoryQuantityColumn();
		InventoryItemNameColumn transactionItemNameColumn = new InventoryItemNameColumn(
				true, currencyProvider) {

			@Override
			public ListFilter<ClientItem> getItemsFilter() {
				return new ListFilter<ClientItem>() {

					@Override
					public boolean filter(ClientItem e) {
						return (e.getType() == ClientItem.TYPE_INVENTORY_PART);
					}
				};
			}

			@Override
			protected void setValue(ClientInventoryAssemblyItem row,
					ClientItem newValue) {
				super.setValue(row, newValue);
				if (newValue != null) {
					if (row.getQuantity() == null) {
						ClientQuantity quantity = new ClientQuantity();
						quantity.setValue(1.0);
						row.setQuantity(quantity);
					}
					if (row.getUnitPrice() == null) {
						row.setUnitPrice(new Double(0));
					}
					ClientMeasurement measurement = Accounter.getCompany()
							.getMeasurement(newValue.getMeasurement());
					row.getQuantity().setUnit(
							measurement.getDefaultUnit().getId());
					row.setWareHouse(newValue.getWarehouse());
					row.setDescription(newValue.getPurchaseDescription());
					quantityColumn.setQuantity(row, row.getQuantity());
				}
				update(row);
				// if (getAllRows().indexOf(row) == getAllRows().size() - 1) {
				// addEmptyRowAtLast();
				// }
			}

			@Override
			protected String getDiscription(ClientItem item) {
				return item.getPurchaseDescription();
			}

		};
		transactionItemNameColumn.setItemForCustomer(false);

		this.addColumn(transactionItemNameColumn);

		this.addColumn(new InventoryDescriptionEditColumn());

		this.addColumn(quantityColumn);

		this.addColumn(new InventoryTransactionUnitPriceColumn(currencyProvider));

		this.addColumn(new InventoryTotalColumn(currencyProvider));

		this.addColumn(new DeleteColumn<ClientInventoryAssemblyItem>());
	}

	@Override
	public void addEmptyRowAtLast() {
		this.add(new ClientInventoryAssemblyItem());
	}

	protected abstract void addEmptyRecords();

	public void updateTotals() {

		List<ClientInventoryAssemblyItem> allrecords = getAllRows();
		lineTotal = 0.0;

		for (ClientInventoryAssemblyItem record : allrecords) {
			if (!record.isEmpty()) {

				Double lineTotalAmt = record.getLineTotal();
				if (lineTotalAmt != null) {
					lineTotal += lineTotalAmt;
				}
			}
		}

		updateNonEditableItems();

	}

	@Override
	public void delete(ClientInventoryAssemblyItem row) {
		super.delete(row);
		updateTotals();
	}

	@Override
	public void setAllRows(List<ClientInventoryAssemblyItem> rows) {
		createColumns();
		for (ClientInventoryAssemblyItem item : rows) {
			item.setID(0);
		}
		super.setAllRows(rows);

	}

	@Override
	public void addRows(List<ClientInventoryAssemblyItem> rows) {
		for (ClientInventoryAssemblyItem item : getRecords()) {
			if (item.getInventoryItem() == null) {
				delete(item);
			}
		}
		createColumns();
		super.addRows(rows);
		List<ClientInventoryAssemblyItem> itemList = new ArrayList<ClientInventoryAssemblyItem>();
		if (getAllRows().size() < 4) {
			for (int ii = 0; ii < (4 - getAllRows().size()); ii++) {
				ClientInventoryAssemblyItem item = new ClientInventoryAssemblyItem();
				itemList.add(item);
			}
			createColumns();
			super.addRows(itemList);
		}
	}

	protected ClientCompany getCompany() {
		return Accounter.getCompany();
	}

	@Override
	public void update(ClientInventoryAssemblyItem row) {
		super.update(row);
		updateTotals();
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public void removeAllRecords() {
		clear();
	}

	public void setRecords(List<ClientInventoryAssemblyItem> transactionItems) {
		if (!transactionItems.isEmpty()) {
			setAllRows(transactionItems);
			updateTotals();
		} else {
			addEmptyRecords();
		}
	}

	public List<ClientInventoryAssemblyItem> getRecords() {
		return getAllRows();
	}

	public ValidationResult validateGrid() {
		ValidationResult result = new ValidationResult();
		// Validations
		// 1. checking for the name of the transaction item
		// 2. checking for the vat code if the company is of type UK
		// TODO::: check whether this validation is working or not
		for (ClientInventoryAssemblyItem transactionItem : this.getRecords()) {
			if (transactionItem.isEmpty()) {
				continue;
			}
			if (transactionItem.getInventoryItem() == null) {
				result.addError("GridItem-",
						messages.pleaseSelect(messages.inventoryItem()));
			}

		}
		if (DecimalUtil.isLessThan(lineTotal, 0.0)) {
			result.addError(this, messages.invalidTransactionAmount());
		}
		return result;
	}

	public void resetRecords() {
		clear();
		addEmptyRecords();
	}

	public void updateAmountsFromGUI() {
		updateTotals();
		for (ClientInventoryAssemblyItem item : this.getAllRows()) {
			updateFromGUI(item);
			update(item);
		}
		updateColumnHeaders();
	}

	public void updateRecords() {
		for (ClientInventoryAssemblyItem item : this.getAllRows()) {
			update(item);
		}
	}

	@Override
	protected abstract boolean isInViewMode();

	public boolean isEmpty() {
		List<ClientInventoryAssemblyItem> records = getRecords();
		boolean isEmpty = true;
		for (ClientInventoryAssemblyItem item : records) {
			if (!item.isEmpty()) {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}

	public List<ClientInventoryAssemblyItem> getTransactionItems() {
		List<ClientInventoryAssemblyItem> list = new ArrayList<ClientInventoryAssemblyItem>();
		for (ClientInventoryAssemblyItem item : getRecords()) {
			if (!item.isEmpty()) {
				list.add(item);
			}
		}
		return list;
	}

	@Override
	public ClientCurrency getTransactionCurrency() {
		return getCompany().getPrimaryCurrency();
	}

	@Override
	public Double getCurrencyFactor() {
		return 1.0;
	}

	@Override
	public Double getAmountInBaseCurrency(Double amount) {
		return amount;
	}

}