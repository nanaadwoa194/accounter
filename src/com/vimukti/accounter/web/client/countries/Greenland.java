package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class Greenland extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = new String[] { "Aasiaat", "Ammassalik",
				"Illoqqortoormiut", "Ilulissat", "Ivittuut", "Kangaatsiaq",
				"Maniitsoq", "Nanortalik", "Narsaq", "Nuuk", "Paamiut",
				"Qaanaaq", "Qaqortoq", "Qasigiannguit", "Qeqertarsuaq",
				"Sisimiut", "Udenfor kommunal inddeling", "Upernavik",
				"Uummannaq"

		};
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "DKK";
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
		// TODO
		// America/Godthab most locations
		// America/Danmarkshavn east coast, north of Scoresbysund
		// America/Scoresbysund Scoresbysund / Ittoqqortoormiit
		// America/Thule Thule / Pituffik
		return "UTC-1:00 America/Scoresbysund";
	}

}
