package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class Seychelles extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = { "Mah�" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "SCR";
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
		return "UTC+4:00 Indian/Mahe";
	}
}
