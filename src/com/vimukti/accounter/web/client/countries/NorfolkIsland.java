package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class NorfolkIsland extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPreferredCurrency() {
		return "AUD";
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
		return "UTC+11:30 Pacific/Norfolk";
	}
}
