package com.vimukti.accounter.mobile.requirements;

import com.vimukti.accounter.mobile.Context;
import com.vimukti.accounter.mobile.InputType;
import com.vimukti.accounter.mobile.MobileException;
import com.vimukti.accounter.web.client.Global;
import com.vimukti.accounter.web.client.ui.DataUtils;

public class AmountRequirement extends SingleRequirement<Double> {

	public AmountRequirement(String requirementName, String displayString,
			String recordName, boolean isOptional2, boolean isAllowFromContext2) {
		super(requirementName, displayString, recordName, isOptional2,
				isAllowFromContext2);
		setDefaultValue(0.0d);
	}

	@Override
	public <T> T getValue() {
		Object value2 = super.getValue();
		return (T) (value2 == null ? 0d : value2);
	}

	@Override
	public boolean isDone() {
		Double value = getValue();
		if (isOptional()) {
			return true;
		}
		return value != 0;
	}

	@Override
	protected String getDisplayValue(Double value) {
		return value != null ? Global.get().toCurrencyFormat(value, null) : "";
	}

	@Override
	protected Double getInputFromContext(Context context)
			throws MobileException {
		String string = context.getString();
		try {
			return DataUtils.getAmountStringAsDouble(string);
		} catch (Exception e) {
			throw new MobileException(getMessages().wrongFormat(
					getMessages().amount()));
		}
	}

	@Override
	public InputType getInputType() {
		return new InputType(INPUT_TYPE_AMOUNT);
	}
}
