package com.vimukti.accounter.migration;

import org.hibernate.Criteria;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vimukti.accounter.core.Account;
import com.vimukti.accounter.core.Address;
import com.vimukti.accounter.core.Contact;
import com.vimukti.accounter.core.FinanceDate;
import com.vimukti.accounter.core.PaymentTerms;
import com.vimukti.accounter.core.TAXAgency;

public class TaxAgencyMigrator implements IMigrator<TAXAgency> {

	@Override
	public JSONObject migrate(TAXAgency obj, MigratorContext context)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		CommonFieldsMigrator.migrateCommonFields(obj, jsonObject, context);
		jsonObject.put("name", obj.getName());
		jsonObject.put("inActive", !obj.isActive());
		// TAX AGENCY SNICE
		FinanceDate payeeSince = obj.getPayeeSince();
		if (payeeSince != null) {
			jsonObject
					.put("payeeSince", payeeSince.getAsDateObject().getTime());
		} else {
			jsonObject.put("payeeSince", obj.getCreatedDate().getTime());
		}
		// Setting object PaymentTerm
		PaymentTerms paymentTerm = obj.getPaymentTerm();
		if (paymentTerm != null) {
			JSONObject paymentTermJSON = new JSONObject();
			paymentTermJSON.put("name", paymentTerm.getName());
			jsonObject.put("paymentTerm", paymentTermJSON);
		}
		jsonObject.put("taxType", getTaxTypeString(obj.getTaxType()));
		// Setting Purchase Liability Account of company
		Account purchaseLiabilityAccount = obj.getPurchaseLiabilityAccount();
		if (purchaseLiabilityAccount != null) {
			jsonObject.put("purchaseLiabilityAccount",
					context.get("Account", purchaseLiabilityAccount.getID()));
		}
		// Setting Sales Liability Account of Company
		Account salesLiabilityAccount = obj.getSalesLiabilityAccount();
		if (salesLiabilityAccount != null) {
			jsonObject.put("salesLiabilityAccount",
					context.get("Account", salesLiabilityAccount.getID()));
		}
		// Setting Filed Liability Account of Company
		Account filedLiabilityAccount = obj.getFiledLiabilityAccount();
		if (filedLiabilityAccount != null) {
			jsonObject.put("filedLiabilityAccount",
					context.get("Account", filedLiabilityAccount.getID()));
		}
		FinanceDate asDateObject = obj.getLastTAXReturnDate();
		if (asDateObject != null) {
			jsonObject.put("lastFileTaxDate", asDateObject.getAsDateObject()
					.getTime());
		}
		jsonObject.put("email", obj.getEmail());
		jsonObject.put("fax", obj.getFaxNo());
		jsonObject.put("phone", obj.getPhoneNo());
		jsonObject.put("webAddress", obj.getWebPageAddress());
		// Addresses
		Address shipToAddress = null;
		Address billToAddress = null;
		for (Address primaryAddress : obj.getAddress()) {
			if (primaryAddress.getType() == Address.TYPE_BILL_TO) {
				billToAddress = primaryAddress;
			}
			if (primaryAddress.getType() == Address.TYPE_SHIP_TO) {
				shipToAddress = primaryAddress;
			}
		}
		// SHIP TO
		if (shipToAddress != null) {
			JSONObject selectedAddress = new JSONObject();
			selectedAddress.put("street", shipToAddress.getStreet());
			selectedAddress.put("city", shipToAddress.getCity());
			selectedAddress.put("stateOrProvince",
					shipToAddress.getStateOrProvinence());
			selectedAddress.put("zipOrPostalCode",
					shipToAddress.getZipOrPostalCode());
			selectedAddress.put("country", shipToAddress.getCountryOrRegion());
			jsonObject.put("shipTo", selectedAddress);
		}
		// BILL TO
		if (billToAddress != null) {
			JSONObject billTOaddr = new JSONObject();
			billTOaddr.put("street", billToAddress.getStreet());
			billTOaddr.put("city", billToAddress.getCity());
			billTOaddr.put("stateOrProvince",
					billToAddress.getStateOrProvinence());
			billTOaddr.put("zipOrPostalCode",
					billToAddress.getZipOrPostalCode());
			billTOaddr.put("country", billToAddress.getCountryOrRegion());
			jsonObject.put("billTo", billTOaddr);
		}
		// Contacts
		JSONArray jsonContacts = new JSONArray();
		for (Contact contact : obj.getContacts()) {
			String contactName = contact.getName();
			if (contactName == null || contactName.equals("")) {
				continue;
			}
			JSONObject jsonContact = new JSONObject();
			jsonContact.put("contactName", contactName);
			jsonContact.put("title", contact.getTitle());
			jsonContact.put("businessPhone", contact.getBusinessPhone());
			jsonContact.put("email", contact.getEmail());
			jsonContacts.put(jsonContact);
		}
		jsonObject.put("contacts", jsonContacts);
		// Currency
		JSONObject currencyJson = new JSONObject();
		currencyJson.put("identity", obj.getCurrency().getFormalName());
		jsonObject.put("currency", currencyJson);
		jsonObject.put("currencyFactor", obj.getCurrencyFactor());

		return jsonObject;
	}

	private static String getTaxTypeString(int taxType) {
		if (taxType == TAXAgency.TAX_TYPE_SALESTAX) {
			return "SalesTax";
		}
		if (taxType == TAXAgency.TAX_TYPE_VAT) {
			return "Vat";
		}
		if (taxType == TAXAgency.TAX_TYPE_SERVICETAX) {
			return "ServiceTax";
		}
		if (taxType == TAXAgency.TAX_TYPE_OTHER) {
			return "Other";
		}
		if (taxType == TAXAgency.TAX_TYPE_TDS) {
			return "Tds";
		}
		return null;
	}

	@Override
	public void addRestrictions(Criteria criteria) {
		// TODO Auto-generated method stub
		
	}
}
