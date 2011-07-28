package com.vimukti.accounter.web.client.ui;

import java.util.List;

import com.vimukti.accounter.web.client.core.ClientItem;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.ui.company.NewItemAction;
import com.vimukti.accounter.web.client.ui.core.AccounterWarningType;
import com.vimukti.accounter.web.client.ui.core.Action;
import com.vimukti.accounter.web.client.ui.core.BaseListView;
import com.vimukti.accounter.web.client.ui.core.CustomersActionFactory;
import com.vimukti.accounter.web.client.ui.core.DecimalUtil;
import com.vimukti.accounter.web.client.ui.core.VendorsActionFactory;
import com.vimukti.accounter.web.client.ui.grids.ItemsListGrid;

/**
 * 
 * @author Mandeep Singh modified by Rajesh.A
 * 
 */
public class ItemListView extends BaseListView<ClientItem> {
	List<ClientItem> allItems;
	private Double total = 0.00;
	private ClientItem toBeDeletedItem;
	private List<ClientItem> listOfItems;
	@SuppressWarnings("unused")
	private int actionType;
	private String catageory;

	public boolean isSalesType = true;
	/*
	 * To Identify from which view the request is generated.i.e SalesItemview or
	 * PurchaseItemview
	 */
	public static boolean isPurchaseType = false;

	public ItemListView() {
		super();
	}

	public static ItemListView getInstance() {
		return new ItemListView();
	}

	@Override
	public void deleteFailed(Throwable caught) {
		super.deleteFailed(caught);
		Accounter.showInformation(Accounter.constants().youCantDeleteItem());
	}

	@Override
	public void deleteSuccess(Boolean result) {

		allItems.remove(toBeDeletedItem);
		refreshTotal();
		// Accounter.showInformation(FinanceApplication.constants()
		// .itemDeletedSuccessfully());

	}

	public void refreshTotal() {

		total = 0.00;
		for (ClientItem item : allItems) {
			if (!DecimalUtil.isEquals(item.getSalesPrice(), 0))
				total += item.getSalesPrice();
		}
		totalLabel.setText(Accounter.constants().totalSalesPrice() + " = "
				+ DataUtils.getAmountAsString(total));
	}

	@Override
	protected Action getAddNewAction() {
		if (!Accounter.getUser().canDoInvoiceTransactions())
			return null;
		else {
			NewItemAction action;
			if (this.catageory.equals(Accounter.constants().customer())) {
				action = CustomersActionFactory.getNewItemAction();
				action.setType(3);
				return action;
			} else if (this.catageory.equals(Accounter.constants().supplier())
					|| this.catageory.equals(Accounter.constants().vendor())) {
				action = VendorsActionFactory.getNewItemAction();
				action.setType(3);
				return action;
			}

			action = CustomersActionFactory.getNewItemAction();
			action.setType(3);
			return action;
		}
	}

	@Override
	protected String getAddNewLabelString() {
		if (Accounter.getUser().canDoInvoiceTransactions())
			return Accounter.constants().addNewItem();
		else
			return "";
	}

	@Override
	protected String getListViewHeading() {
		return Accounter.constants().productList();
	}

	@Override
	public void initListCallback() {
	}

	@Override
	public void updateInGrid(ClientItem objectTobeModified) {

	}

	@Override
	protected void initGrid() {
		grid = new ItemsListGrid(false);
		grid.init();

		isPurchaseType = !isSalesType;
		if (!isSalesType)
			listOfItems = getCompany().getPurchaseItems();
		else
			listOfItems = getCompany().getSalesItems();

		filterList(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void filterList(boolean isActive) {
		grid.removeAllRecords();
		for (ClientItem item : listOfItems) {
			if (isActive) {
				if (item.isActive() == true)
					grid.addData(item);
			} else if (item.isActive() == false) {
				grid.addData(item);
			}

		}
		if (grid.getRecords().isEmpty())
			grid.addEmptyMessage(AccounterWarningType.RECORDSEMPTY);
	}

	public void setCatageoryType(String catagory) {
		this.catageory = catagory;
	}

	@Override
	public void fitToSize(int height, int width) {
		super.fitToSize(height, width);

	}

	@Override
	public void onEdit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printPreview() {
		// NOTHING TO DO.
	}

	@Override
	public void processupdateView(IAccounterCore core, int command) {
		super.processupdateView(core, command);
		filterList(true);
	}

	@Override
	protected String getViewTitle() {
		return Accounter.constants().items();
	}

}
