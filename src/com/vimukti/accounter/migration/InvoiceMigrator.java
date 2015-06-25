package com.vimukti.accounter.migration;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vimukti.accounter.core.Address;
import com.vimukti.accounter.core.Contact;
import com.vimukti.accounter.core.Estimate;
import com.vimukti.accounter.core.Invoice;
import com.vimukti.accounter.core.PaymentTerms;
import com.vimukti.accounter.core.ShippingMethod;
import com.vimukti.accounter.core.ShippingTerms;

public class InvoiceMigrator extends TransactionMigrator<Invoice> {

	@Override
	public JSONObject migrate(Invoice obj, MigratorContext context)
			throws JSONException {
		JSONObject jsonObject = super.migrate(obj, context);

		jsonObject.put("dueDate", obj.getDueDate().getAsDateObject().getTime());
		jsonObject.put("phone", obj.getPhone());

		JSONObject billingAdressJson = new JSONObject();
		Address billingAddress = obj.getBillingAddress();
		if (billingAddress != null) {
			billingAdressJson.put("street", billingAddress.getStreet());
			billingAdressJson.put("city", billingAddress.getCity());
			billingAdressJson.put("stateOrProvince",
					billingAddress.getStateOrProvinence());
			billingAdressJson.put("zipOrPostalCode",
					billingAddress.getZipOrPostalCode());
			billingAdressJson.put("country",
					billingAddress.getCountryOrRegion());
			jsonObject.put("billTo", billingAdressJson);
		}

		JSONObject shippingAdressJson = new JSONObject();
		Address shippingAddress = obj.getBillingAddress();
		if (shippingAddress != null) {
			shippingAdressJson.put("street", shippingAddress.getStreet());
			shippingAdressJson.put("city", shippingAddress.getCity());
			shippingAdressJson.put("stateOrProvince",
					shippingAddress.getStateOrProvinence());
			shippingAdressJson.put("zipOrPostalCode",
					shippingAddress.getZipOrPostalCode());
			shippingAdressJson.put("country",
					shippingAddress.getCountryOrRegion());
			jsonObject.put("shipTo", shippingAdressJson);
		}

		ShippingTerms shippingTerm = obj.getShippingTerm();
		if (shippingTerm != null) {
			jsonObject.put("shippingTerm",
					context.get("ShippingTerm", shippingTerm.getID()));
		}
		ShippingMethod shippingMethod = obj.getShippingMethod();
		if (shippingMethod != null) {
			jsonObject.put("shippingMethod",
					context.get("shippingMethod", shippingMethod.getID()));
		}
		jsonObject.put("deliveryDate", obj.getDeliverydate().getAsDateObject()
				.getTime());

		{
			List<Estimate> estimates = obj.getEstimates();
			JSONArray array = new JSONArray();
			for (Estimate estimate : estimates) {
				JSONObject quoteJson = new JSONObject();
				quoteJson.put("salesQuotation",
						context.get("SalesQuotation", estimate.getID()));
				array.put(quoteJson);
			}
			jsonObject.put("salesOrders", array);
		}

		PaymentTerms paymentTerm = obj.getPaymentTerm();
		if (paymentTerm != null) {
			jsonObject.put("paymentTerm",
					context.get("PaymentTerms", paymentTerm.getID()));
		}
		Contact contact = obj.getContact();
		if (contact != null) {
			jsonObject.put("contact", context.get("Contact", contact.getID()));

		}
		return jsonObject;
	}
}