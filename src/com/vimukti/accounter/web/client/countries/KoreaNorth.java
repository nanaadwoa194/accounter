package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class KoreaNorth extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = new String[] { "Chagangdo", "Hamgyongbukto",
				"Hamgyongnamdo", "Hwanghaebukto", "Hwanghaenamdo", "Kangwon",
				"Pyonganbukto", "Pyongannamdo", "Pyongyang", "Rason",
				"Yanggang" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "KPW";
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
		return "UTC+9:00 Asia/Pyongyang";
	}

}
