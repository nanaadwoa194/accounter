package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class Hungary extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = new String[] { "Bacs-Kiskun", "Baranya", "Bekes",
				"Borsod-Abauj-Zemplen", "Budapest", "Csongrad", "Fejer",
				"Gyor-Moson-Sopron", "Hajdu-Bihar", "Heves",
				"J�sz-Nagykun-Szolnok", "Komarom-Esztergom", "Nograd", "Pest",
				"Somogy", "Szabolcs-Szatmar-Bereg", "Tolna", "Vas", "Veszprem",
				"Zala" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "HUF";
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
		return "UTC+1:00 Europe/Budapest";
	}

}
