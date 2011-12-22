package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class SouthAfrica extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = new String[] { "Eastern Cape", "Free State",
				"Gauteng", "KwaZulu Natal", "Limpopo", "Mpumalanga",
				"Northern Cape", "North West", "Western Cape" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "ZAR";
	}

	@Override
	public boolean allowFlexibleFiscalYear() {
		return true;
	}

	@Override
	public String getDefaultFiscalYearStartingMonth() {
		return DayAndMonthUtil.april();
	}

	@Override
	public String getDefaultTimeZone(String state) {
		return "UTC+2:00 Africa/Johannesburg";
	}

}
