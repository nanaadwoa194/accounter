package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class NewCaledonia extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = { "�les", "Nord", "Sud" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "XPF";
	}

	@Override
	public boolean allowFlexibleFiscalYear() {
		return true;
	}

	@Override
	public String getDefaultFiscalYearStartingMonth() {
		return DayAndMonthUtil.january();
	}

	@Override
	public String getDefaultTimeZone(String state) {
		return "UTC+11:00 Pacific/Noumea";
	}
}
