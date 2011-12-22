package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class Kazakhstan extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = new String[] { "Akmechet", "Almaty", "Aqmola",
				"Aqtobe", "Atyrau", "Batis Kazakstan", "Mankistau",
				"Ontustik Kazakstan", "Pavlodar", "Qaragandy", "Qostanay",
				"Sigis Kazakstan", "Soltustik Kazakstan", "Taraz" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "KZT";
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
		if (state.equals("Qaragandy") || state.equals("Qaragandy")) {
			return "UTC+6:00 Asia/Qyzylorda";
		} else if (state.equals("Aqtobe")) {
			return "UTC+5:00 Asia/Aqtobe";
		} else if (state.equals("Atyrau") || state.equals("Mankistau")) {
			return "UTC+5:00 Asia/Aqtau";
		} else if (state.equals("Soltustik Kazakstan")) {
			return "UTC+5:00 Asia/Oral";
		} else {
			return "UTC+6:00 Asia/Almaty";
		}

	}

}
