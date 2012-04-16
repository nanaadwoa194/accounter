package com.vimukti.accounter.web.client.ui.combo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vimukti.accounter.web.client.core.ClientEmployeeGroup;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.core.ActionCallback;
import com.vimukti.accounter.web.client.ui.core.ActionFactory;
import com.vimukti.accounter.web.client.ui.payroll.NewEmployeeGroupAction;

public class EmployeeGroupCombo extends CustomCombo<ClientEmployeeGroup> {

	public EmployeeGroupCombo(String title, boolean isEmp) {
		super(title, true, 1, "employeegroupcombo");
		getEmployeeGroups();
	}

	private void getEmployeeGroups() {
		Accounter.createPayrollService().getEmployeeGroups(new AsyncCallback<ArrayList<ClientEmployeeGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<ClientEmployeeGroup> result) {
				initCombo(result);
			}
		});
	}

	@Override
	public String getDefaultAddNewCaption() {
		return messages.employeeGroup();
	}

	@Override
	public void onAddNew() {
		NewEmployeeGroupAction action = ActionFactory
				.getNewEmployeeGroupAction();
		action.setCallback(new ActionCallback<ClientEmployeeGroup>() {

			@Override
			public void actionResult(ClientEmployeeGroup result) {
				addItemThenfireEvent(result);
			}
		});
		action.run(null, true);
	}

	@Override
	protected String getDisplayName(ClientEmployeeGroup object) {
		if (object != null)
			return object.getName() != null ? object.getName() : "";
		else
			return "";
	}

	@Override
	protected String getColumnData(ClientEmployeeGroup object, int col) {
		switch (col) {
		case 0:
			return object.getName();
		}
		return null;
	}
}
