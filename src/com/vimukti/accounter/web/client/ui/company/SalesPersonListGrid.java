package com.vimukti.accounter.web.client.ui.company;

import java.util.Set;

import com.google.gwt.user.client.ui.CheckBox;
import com.vimukti.accounter.web.client.core.AccounterCoreType;
import com.vimukti.accounter.web.client.core.ClientAddress;
import com.vimukti.accounter.web.client.core.ClientFax;
import com.vimukti.accounter.web.client.core.ClientPhone;
import com.vimukti.accounter.web.client.core.ClientSalesPerson;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.UIUtils;
import com.vimukti.accounter.web.client.ui.core.ActionFactory;
import com.vimukti.accounter.web.client.ui.core.ViewManager;
import com.vimukti.accounter.web.client.ui.grids.BaseListGrid;
import com.vimukti.accounter.web.client.ui.grids.ListGrid;

public class SalesPersonListGrid extends BaseListGrid<ClientSalesPerson> {

	public SalesPersonListGrid() {
		super(false, false);
	}

	@Override
	protected Object getColumnValue(ClientSalesPerson salesPerson, int col) {
		switch (col) {
		case 0:
			return salesPerson.isActive();
		case 1:
			return salesPerson.getName();
		case 2:
			return getBillToAddress(salesPerson) != null ? getBillToAddress(
					salesPerson).getAddress1() : "";
		case 3:
			return getBillToAddress(salesPerson) != null ? getBillToAddress(
					salesPerson).getCity() : "";
		case 4:
			return getBillToAddress(salesPerson) != null ? getBillToAddress(
					salesPerson).getStateOrProvinence() : "";

		case 5:
			return getBillToAddress(salesPerson) != null ? getBillToAddress(
					salesPerson).getZipOrPostalCode() : "";

		case 6:
			return salesPerson.getPhoneNo();
			// Set<ClientPhone> phones = SalesPerson.getPhoneNumbers();
			// for (ClientPhone p : phones) {
			// if (p.getType() == ClientPhone.BUSINESS_PHONE_NUMBER) {
			// return p.getNumber();
			// }
			// }
			// break;
		case 7:
			return salesPerson.getFaxNo();
			// Set<ClientFax> faxes = SalesPerson.getFaxNumbers();
			// for (ClientFax f : faxes) {
			// if (f.getType() == ClientFax.TYPE_BUSINESS) {
			// return f.getNumber();
			// }
			// }
			// break;
			// case 8:
			// return DataUtils.getAmountAsString(SalesPerson.getBalance());
		case 8:
			// updateTotal(SalesPerson, true);
			return Accounter.getFinanceMenuImages().delete();
		default:
			break;
		}

		return null;
	}

	@Override
	protected String[] getColumns() {
		return new String[] { Accounter.constants().active(),
				Accounter.constants().salesPerson(),
				Accounter.constants().address(), Accounter.constants().city(),
				Accounter.constants().state(), Accounter.constants().zipCode(),
				Accounter.constants().phone(), Accounter.constants().fax(), " " };

	}

	@Override
	protected void onClick(ClientSalesPerson obj, int row, int col) {
		// List<ClientCustomer> customers = getRecords();
		// ClientCustomer customer = customers.get(row);

		switch (col) {
		case 8:
			showWarnDialog(obj);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onValueChange(ClientSalesPerson obj, int col, Object value) {

	}

	private ClientAddress getBillToAddress(ClientSalesPerson salesPerson) {
		Set<ClientAddress> address = salesPerson.getAddress();
		for (ClientAddress a : address) {
			if (a.getType() == ClientAddress.TYPE_BILL_TO) {
				return a;
			}

		}
		return null;
	}

	@Override
	public void onDoubleClick(ClientSalesPerson obj) {
		ActionFactory.getNewSalesperSonAction().run(obj, true);
	}

	protected void executeDelete(ClientSalesPerson object) {
		ViewManager.getInstance().deleteObject(object,
				AccounterCoreType.SALES_PERSON, this);

	}

	@Override
	protected int[] setColTypes() {
		return new int[] { ListGrid.COLUMN_TYPE_CHECK,
				ListGrid.COLUMN_TYPE_TEXT, ListGrid.COLUMN_TYPE_TEXT,
				ListGrid.COLUMN_TYPE_TEXT, ListGrid.COLUMN_TYPE_TEXT,
				ListGrid.COLUMN_TYPE_TEXT, ListGrid.COLUMN_TYPE_TEXT,
				ListGrid.COLUMN_TYPE_TEXT, ListGrid.COLUMN_TYPE_IMAGE };
	}

	@Override
	public boolean validateGrid() {
		// its not using any where return null;
		return true;
	}

	// protected void updateTotal(ClientPayee customer, boolean add) {
	//
	// if (add)
	// total += customer.getBalance();
	// else
	// total -= customer.getBalance();
	//
	// }

	public Double getTotal() {
		return total;
	}

	@Override
	protected int getCellWidth(int index) {
		if (index == 8) {
			if (UIUtils.isMSIEBrowser())
				return 25;
			else
				return 15;
		}
		if (index == 0) {
			return 40;
		}
		if (index == 2 || index == 3 || index == 4 || index == 5 || index == 6
				|| index == 7) {
			return 100;
		}
		return -1;
	}

	@Override
	protected int sort(ClientSalesPerson obj1, ClientSalesPerson obj2, int index) {
		switch (index) {
		case 1:
			return obj1.getName().compareTo(obj2.getName());
		case 2:

			String address1 = getBillToAddress(obj1) != null ? getBillToAddress(
					obj1).getAddress1()
					: "";
			String address2 = getBillToAddress(obj2) != null ? getBillToAddress(
					obj2).getAddress1()
					: "";
			return address1.compareTo(address2);

		case 3:

			String city1 = getBillToAddress(obj1) != null ? getBillToAddress(
					obj1).getCity() : "";
			String city2 = getBillToAddress(obj2) != null ? getBillToAddress(
					obj2).getCity() : "";
			return city1.compareTo(city2);

		case 4:
			String state1 = getBillToAddress(obj1) != null ? getBillToAddress(
					obj1).getStateOrProvinence() : "";
			String state2 = getBillToAddress(obj2) != null ? getBillToAddress(
					obj2).getStateOrProvinence() : "";
			return state1.compareTo(state2);

		case 5:
			String zip1 = getBillToAddress(obj1) != null ? getBillToAddress(
					obj1).getZipOrPostalCode() : "";
			String zip2 = getBillToAddress(obj2) != null ? getBillToAddress(
					obj2).getZipOrPostalCode() : "";
			return zip1.compareTo(zip2);
		case 6:
			String phone1 = getPhoneNumber(obj1);
			String phone2 = getPhoneNumber(obj2);
			return phone1.compareTo(phone2);
		case 7:
			String fax1 = getFaxNumber(obj1);
			String fax2 = getFaxNumber(obj2);
			return fax1.compareTo(fax2);
			// case 8:
			// Double bal1 = obj1.getBalance();
			// Double bal2 = obj2.getBalance();
			// return bal1.compareTo(bal2);

		default:
			break;
		}

		return 0;
	}

	private String getPhoneNumber(ClientSalesPerson SalesPerson) {
		String phoneNo = null;
		if (SalesPerson != null) {
			Set<ClientPhone> phones = SalesPerson.getPhoneNumbers();
			for (ClientPhone p : phones) {
				if (p.getType() == ClientPhone.BUSINESS_PHONE_NUMBER) {
					phoneNo = String.valueOf(p.getNumber());
				}
			}

		}
		return phoneNo != null ? phoneNo : "";
	}

	private String getFaxNumber(ClientSalesPerson SalesPerson) {
		String faxNo = null;
		if (SalesPerson != null) {
			Set<ClientFax> faxes = SalesPerson.getFaxNumbers();
			for (ClientFax f : faxes) {
				if (f.getType() == ClientFax.TYPE_BUSINESS) {
					faxNo = String.valueOf(f.getNumber());
				}
			}

		}
		return faxNo != null ? faxNo : "";
	}

	@Override
	public void processupdateView(IAccounterCore core, int command) {
		// its not using any where return null;
	}

	public AccounterCoreType getType() {
		return AccounterCoreType.SALES_PERSON;
	}

	@Override
	public void addData(ClientSalesPerson obj) {
		super.addData(obj);
		((CheckBox) this.getWidget(currentRow, 0)).setEnabled(false);
	}

	@Override
	public void headerCellClicked(int colIndex) {
		super.headerCellClicked(colIndex);
		for (int i = 0; i < this.getRowCount(); i++) {
			((CheckBox) this.getWidget(i, 0)).setEnabled(false);
		}
	}

}
