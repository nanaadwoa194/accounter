package com.vimukti.accounter.migration;

import org.json.JSONException;
import org.json.JSONObject;

import com.vimukti.accounter.core.Utility;
import com.vimukti.accounter.core.WriteCheck;

public class WriteCheckMigrator extends TransactionMigrator<WriteCheck> {
	@Override
	public JSONObject migrate(WriteCheck obj, MigratorContext context)
			throws JSONException {
		JSONObject jsonObj = super.migrate(obj, context);
		jsonObj.put("inFavourOf", obj.getInFavourOf());
		jsonObj.put("ammount", obj.getAmount());

		// checkInDetail
		jsonObj.put("date", obj.getDate().getAsDateObject());
		jsonObj.put("chequeNumber", obj.getCheckNumber());
		jsonObj.put("memo", obj.getMemo());

		// PaymentStatus TODO
		return jsonObj;
	}
}
