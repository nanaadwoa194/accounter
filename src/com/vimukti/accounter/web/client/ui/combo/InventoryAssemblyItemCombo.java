package com.vimukti.accounter.web.client.ui.combo;

import java.util.ArrayList;
import java.util.List;

import com.vimukti.accounter.web.client.core.ClientInventoryAssembly;
import com.vimukti.accounter.web.client.core.ClientItem;
import com.vimukti.accounter.web.client.ui.InventoryAssemblyAction;
import com.vimukti.accounter.web.client.ui.core.ActionCallback;

public class InventoryAssemblyItemCombo extends
		CustomCombo<ClientInventoryAssembly> {

	public InventoryAssemblyItemCombo(String title) {
		super(title);
		List<ClientInventoryAssembly> assemblies = new ArrayList<ClientInventoryAssembly>();
		List<ClientItem> items = getCompany().getItems();
		for (ClientItem clientItem : items) {
			if (clientItem.getType() == ClientItem.TYPE_INVENTORY_ASSEMBLY) {
				assemblies.add((ClientInventoryAssembly) clientItem);
			}
		}
		initCombo(assemblies);
	}

	@Override
	protected String getDisplayName(ClientInventoryAssembly object) {
		return object.getName();
	}

	@Override
	protected String getColumnData(ClientInventoryAssembly object, int col) {
		return object.getName();
	}

	@Override
	public String getDefaultAddNewCaption() {
		return messages.assemblyItem();
	}

	@Override
	public void onAddNew() {
		InventoryAssemblyAction action = new InventoryAssemblyAction();
		action.setDependent(true);
		action.setCallback(new ActionCallback<ClientInventoryAssembly>() {

			@Override
			public void actionResult(ClientInventoryAssembly result) {
				if (result.getDisplayName() != null && result != null) {
					addItemThenfireEvent((ClientInventoryAssembly) result);
				}
			}
		});
		action.run();
	}
}
