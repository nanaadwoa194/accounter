package com.vimukti.accounter.mobile.requirements;

import com.vimukti.accounter.core.CustomerGroup;
import com.vimukti.accounter.mobile.CommandList;
import com.vimukti.accounter.mobile.Record;
import com.vimukti.accounter.web.client.Global;

public abstract class CustomerGroupRequirement extends
		ListRequirement<CustomerGroup> {

	public CustomerGroupRequirement(String requirementName, String enterString,
			String recordName, boolean isOptional, boolean isAllowFromContext,
			ChangeListner<CustomerGroup> listner) {
		super(requirementName, enterString, recordName, isOptional,
				isAllowFromContext, listner);
	}

	@Override
	protected String getEmptyString() {
		return getMessages().youDontHaveAny(
				getMessages().payeeGroup(Global.get().Customer()));
	}

	@Override
	protected Record createRecord(CustomerGroup value) {
		Record record = new Record(value);
		record.add(getMessages().name(), value.getName());
		return record;
	}

	@Override
	protected String getDisplayValue(CustomerGroup value) {
		return value != null ? value.getName() : "";
	}

	@Override
	protected void setCreateCommand(CommandList list) {
		list.add("createcustomergroup");
	}

	@Override
	protected String getSelectString() {
		return getMessages().pleaseSelect(
				getMessages().payeeGroup(Global.get().Customer()));
	}

}
