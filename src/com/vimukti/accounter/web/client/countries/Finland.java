package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class Finland extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = new String[] { "Ahvenanmaa", "Etela-Karjala",
				"Etela-Pohjanmaa", "Etela-Savo", "Ita-Uusimaa", "Kainuu",
				"Kanta-Hame", "Keski-Pohjanmaa", "Keski-Suomi", "Kymenlaakso",
				"Lappland", "Paijat-Hame", "Pirkanmaa", "Pohjanmaa",
				"Pohjois-Karjala", "Pohjois-Pohjanmaa", "Pohjois-Savo",
				"Satakunta", "Uusimaa", "Varsinais-Suomi" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "EUR";
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
		return "UTC+2:00 Europe/Helsinki";
	}

}
