package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class Namibia extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = { "Caprivi", "Erongo", "Hardap", "Karas", "Kavango",
				"Khomas", "Kunene", "Ohangwena", "Omaheke", "Omusati",
				"Oshana", "Oshikoto", "Otjozondjupa" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "NAD";
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
		return "UTC+1:00 Africa/Windhoek";
	}

}
