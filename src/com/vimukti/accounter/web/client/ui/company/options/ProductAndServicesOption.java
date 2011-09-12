/**
 * 
 */
package com.vimukti.accounter.web.client.ui.company.options;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author vimukti36
 * 
 */
public class ProductAndServicesOption extends AbstractPreferenceOption {

	private static ProductAndServicesOptionUiBinder uiBinder = GWT
			.create(ProductAndServicesOptionUiBinder.class);
	@UiField
	CheckBox productsAndservicesforPurchasesCheckBox;
	@UiField
	CheckBox productsAndservicesforSalesCheckBox;

	interface ProductAndServicesOptionUiBinder extends
			UiBinder<Widget, ProductAndServicesOption> {
	}

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public ProductAndServicesOption() {
		initWidget(uiBinder.createAndBindUi(this));
		createControls();
		initdata();
	}

	private void initdata() {
		// TODO
	}

	private void createControls() {
		productsAndservicesforPurchasesCheckBox.setText(constants
				.productsandservicesforpurchases());
		productsAndservicesforSalesCheckBox.setText(constants
				.productsandservicesforsales());
	}

	public ProductAndServicesOption(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Products and Services";
	}

	@Override
	public void onSave() {
		productsAndservicesforPurchasesCheckBox.getValue();
		productsAndservicesforSalesCheckBox.getValue();

	}

	@Override
	public String getAnchor() {
		// TODO Auto-generated method stub
		return " Products and Services";
	}
}
