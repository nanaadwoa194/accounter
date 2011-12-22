package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;

public class CongoDemocratic extends AbstractCountryPreferences {

	@Override
	public String getPreferredCurrency() {
		return "CDZ";
		// formerly ZRZ
	}

	@Override
	public String[] getStates() {
		return new String[] { "Bandundu", "Bas-Congo", "�quateur",
				"Haut-Congo", "Kasai-Occidental", "Kasai-Oriental", "Katanga",
				"Kinshasa", "Maniema", "Nord-Kivu", "Sud-Kivu" };
	}

	@Override
	public String getDefaultTimeZone(String state) {
		// West
		return "UTC+1:00 Africa/Kinshasa";
		// East
		// "UTC+2:00 Africa/Lubumbashi"
	}

}
