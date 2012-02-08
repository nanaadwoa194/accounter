/**
 * 
 */
package com.vimukti.accounter.web.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vimukti.accounter.core.Subscription;
import com.vimukti.accounter.web.client.core.AccountsTemplate;
import com.vimukti.accounter.web.client.core.ClientCompany;
import com.vimukti.accounter.web.client.core.ClientCompanyPreferences;
import com.vimukti.accounter.web.client.core.TemplateAccount;

/**
 * @author Prasanna Kumar G
 * 
 */
public interface IAccounterCompanyInitializationServiceAsync {

	void initalizeCompany(ClientCompanyPreferences preferences,
			String password, List<TemplateAccount> accountsTemplates,
			AsyncCallback<Boolean> callback);

	void getAccountsTemplate(AsyncCallback<List<AccountsTemplate>> callback);

	void getCountry(AsyncCallback<String> callback);

	void getCompany(AsyncCallback<ClientCompany> callback);

	void isCompanyNameExists(String companyName, AsyncCallback<Boolean> callback);

	public void getSubscription(AsyncCallback<Subscription> callback);

}
