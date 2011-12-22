package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class SaintHelena extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = { "Ascension", "Saint Helena", "Tristan da Cunha" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "SHP";
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
		return "UTC+0:00 Atlantic/St_Helena";
	}

}
