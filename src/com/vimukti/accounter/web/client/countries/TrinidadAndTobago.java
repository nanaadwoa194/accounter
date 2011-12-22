package com.vimukti.accounter.web.client.countries;

import com.vimukti.accounter.web.client.util.AbstractCountryPreferences;
import com.vimukti.accounter.web.client.util.DayAndMonthUtil;

public class TrinidadAndTobago extends AbstractCountryPreferences {

	@Override
	public String[] getStates() {
		String[] states = new String[] { "Arima", "Chaguanas",
				"Couva-Tabaquite-Talparo", "Diego Mart�n", "Mayaro-R�o Claro",
				"Pe�al D�b�", "Point Fort�n", "Port of Spain", "Princes Town",
				"San Fernando", "Sangre Grande", "San Juan-Laventville",
				"Siparia", "Tobago", "Tunapuna-Piarco" };
		return states;
	}

	@Override
	public String getPreferredCurrency() {
		return "TTD";
	}

	@Override
	public boolean allowFlexibleFiscalYear() {
		return true;
	}

	@Override
	public String getDefaultFiscalYearStartingMonth() {
		return DayAndMonthUtil.october();
	}

	@Override
	public String getDefaultTimeZone(String state) {
		return "UTC-4:00 America/Port_of_Spain";
	}

}
