package com.vimukti.accounter.web.client.ui.vat;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vimukti.accounter.web.client.AccounterAsyncCallback;
import com.vimukti.accounter.web.client.core.AccounterCoreType;
import com.vimukti.accounter.web.client.core.ClientCompany;
import com.vimukti.accounter.web.client.core.ClientTDSDeductorMasters;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.core.Utility;
import com.vimukti.accounter.web.client.core.ValidationResult;
import com.vimukti.accounter.web.client.exception.AccounterException;
import com.vimukti.accounter.web.client.exception.AccounterExceptions;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.UIUtils;
import com.vimukti.accounter.web.client.ui.combo.IAccounterComboSelectionChangeHandler;
import com.vimukti.accounter.web.client.ui.combo.SelectCombo;
import com.vimukti.accounter.web.client.ui.core.BaseView;
import com.vimukti.accounter.web.client.ui.core.ButtonBar;
import com.vimukti.accounter.web.client.ui.core.EmailField;
import com.vimukti.accounter.web.client.ui.core.IntegerField;
import com.vimukti.accounter.web.client.ui.forms.CheckboxItem;
import com.vimukti.accounter.web.client.ui.forms.DynamicForm;
import com.vimukti.accounter.web.client.ui.forms.TextItem;

public class TDSDeductorDetailsView extends BaseView<ClientTDSDeductorMasters> {

	private TextItem deductorName;
	private TextItem branchName;
	private TextItem flatNo;
	private TextItem buildingName;
	private TextItem streetName;
	private TextItem areaName;
	private TextItem cityName;
	private IntegerField pinNumber;
	private IntegerField telephoneNumber;
	private IntegerField faxNumber;
	private SelectCombo addressChangeCombo;
	private DynamicForm taxDynamicForm;
	private EmailField email;
	private DynamicForm otherDynamicForm;
	private SelectCombo statusCombo;
	private SelectCombo deductorTypeOther;
	private SelectCombo govtState;
	private TextItem paoCode;
	private IntegerField paoRegistration;
	private TextItem ddoCode;
	private TextItem ddoRegistration;
	private SelectCombo ministryCombo;
	private TextItem ministryNameOtehr;
	private SelectCombo deductorTypeGovernment;
	protected String stateSelected;
	private boolean true_falseValue;
	protected String statusSelected;
	private String deductorTypeSelected;
	private TextItem stdNumber;
	private SelectCombo stateCombo;
	private TextItem panNumber;
	private TextItem tanNumber;
	private CheckboxItem addressSameBox;
	private boolean isViewInitialised;

	@Override
	public void init() {
		super.init();
		createControls();
		setSize("100%", "100%");

		// initRPCService();
		if (data != null) {
			onEdit();
		}

	}

	private void createControls() {

		deductorName = new TextItem("Name");
		deductorName.setHelpInformation(true);
		deductorName.setRequired(true);
		deductorName.setDisabled(isInViewMode());

		branchName = new TextItem("Branch/Division");
		branchName.setHelpInformation(true);
		branchName.setDisabled(isInViewMode());

		flatNo = new TextItem("Flat No.");
		flatNo.setHelpInformation(true);
		flatNo.setRequired(true);
		flatNo.setDisabled(isInViewMode());

		buildingName = new TextItem("Name of Premisis/Building");
		buildingName.setHelpInformation(true);
		buildingName.setDisabled(isInViewMode());

		streetName = new TextItem("Street/Road Name");
		streetName.setHelpInformation(true);
		streetName.setDisabled(isInViewMode());

		areaName = new TextItem("Area");
		areaName.setHelpInformation(true);
		areaName.setDisabled(isInViewMode());

		cityName = new TextItem("City/Town/District");
		cityName.setHelpInformation(true);
		cityName.setDisabled(isInViewMode());

		stateCombo = new SelectCombo("State");
		stateCombo.setHelpInformation(true);
		stateCombo.initCombo(getStatesList());
		stateCombo.setDisabled(isInViewMode());
		stateCombo.setRequired(true);
		stateCombo
				.addSelectionChangeHandler(new IAccounterComboSelectionChangeHandler<String>() {

					@Override
					public void selectedComboBoxItem(String selectItem) {
						stateSelected = selectItem;
					}
				});

		pinNumber = new IntegerField(this, "Pin Code");
		pinNumber.setHelpInformation(true);
		pinNumber.setDisabled(isInViewMode());
		pinNumber.setRequired(true);

		addressChangeCombo = new SelectCombo(
				"Has Address changed since last return");
		addressChangeCombo.setHelpInformation(true);
		addressChangeCombo.initCombo(getYESNOList());
		addressChangeCombo.setDisabled(isInViewMode());
		addressChangeCombo.setRequired(true);
		addressChangeCombo
				.addSelectionChangeHandler(new IAccounterComboSelectionChangeHandler<String>() {

					@Override
					public void selectedComboBoxItem(String selectItem) {
						if (selectItem.equals(getYESNOList().get(1))) {
							true_falseValue = true;
						} else {
							true_falseValue = false;
						}
					}
				});

		stdNumber = new TextItem("Std Code");
		stdNumber.setHelpInformation(true);
		stdNumber.setDisabled(isInViewMode());

		telephoneNumber = new IntegerField(this, "Telephone No.");
		telephoneNumber.setHelpInformation(true);
		telephoneNumber.setDisabled(isInViewMode());

		faxNumber = new IntegerField(this, "Fax No.");
		faxNumber.setHelpInformation(true);
		faxNumber.setDisabled(isInViewMode());

		panNumber = new TextItem("Pan Number");
		panNumber.setHelpInformation(true);
		panNumber.setDisabled(isInViewMode());
		panNumber.setRequired(true);

		tanNumber = new TextItem("Tan Number");
		tanNumber.setHelpInformation(true);
		tanNumber.setDisabled(isInViewMode());
		tanNumber.setRequired(true);

		addressSameBox = new CheckboxItem(
				"Address same for resposible person also");
		addressSameBox.setDisabled(isInViewMode());

		email = new EmailField("Email");
		email.setHelpInformation(true);
		email.setDisabled(isInViewMode());

		taxDynamicForm = new DynamicForm();
		taxDynamicForm.setFields(deductorName, branchName, flatNo,
				buildingName, streetName, areaName, cityName, stateCombo,
				pinNumber, addressChangeCombo, stdNumber, telephoneNumber,
				faxNumber, email);

		statusCombo = new SelectCombo("Status");
		statusCombo.setHelpInformation(true);
		statusCombo.initCombo(getStatusTypes());
		statusCombo.setDisabled(isInViewMode());
		statusCombo.setRequired(true);
		statusCombo
				.addSelectionChangeHandler(new IAccounterComboSelectionChangeHandler<String>() {

					@Override
					public void selectedComboBoxItem(String selectItem) {
						statusSelected = selectItem;

						if (statusSelected.equals(getStatusTypes().get(0))) {
							otherSelected();

						} else {
							governmentSelected();
						}
					}
				});

		deductorTypeOther = new SelectCombo("Deductor Type");
		deductorTypeOther.setHelpInformation(true);
		deductorTypeOther.initCombo(getOthersList());
		deductorTypeOther.setSelectedItem(0);
		deductorTypeOther.setDisabled(isInViewMode());
		deductorTypeOther.setRequired(true);
		deductorTypeOther
				.addSelectionChangeHandler(new IAccounterComboSelectionChangeHandler<String>() {

					@Override
					public void selectedComboBoxItem(String selectItem) {
						deductorTypeSelected = selectItem;
					}
				});

		deductorTypeGovernment = new SelectCombo("Deductor Type");
		deductorTypeGovernment.setHelpInformation(true);
		deductorTypeGovernment.initCombo(getGovtList());
		deductorTypeGovernment.setSelectedItem(0);
		deductorTypeGovernment.setDisabled(isInViewMode());
		deductorTypeGovernment.setRequired(true);
		deductorTypeGovernment
				.addSelectionChangeHandler(new IAccounterComboSelectionChangeHandler<String>() {

					@Override
					public void selectedComboBoxItem(String selectItem) {
						deductorTypeSelected = selectItem;
					}
				});

		govtState = new SelectCombo("State");
		govtState.setHelpInformation(true);
		govtState.initCombo(getStatesList());
		govtState
				.addSelectionChangeHandler(new IAccounterComboSelectionChangeHandler<String>() {

					@Override
					public void selectedComboBoxItem(String selectItem) {
						stateSelected = selectItem;
					}
				});

		paoCode = new TextItem("PAO Code");
		paoCode.setHelpInformation(true);
		paoCode.setDisabled(isInViewMode());

		paoRegistration = new IntegerField(this, "PAO Registration No.");
		paoRegistration.setHelpInformation(true);
		paoRegistration.setDisabled(isInViewMode());

		ddoCode = new TextItem("DDO Code");
		ddoCode.setHelpInformation(true);
		ddoCode.setDisabled(isInViewMode());

		ddoRegistration = new TextItem("DDO Registration No.");
		ddoRegistration.setHelpInformation(true);
		ddoRegistration.setDisabled(isInViewMode());

		ministryCombo = new SelectCombo("Ministry/Dept. Name");
		ministryCombo.setHelpInformation(true);
		ministryCombo.initCombo(getMinistryType());
		ministryCombo.setSelectedItem(0);
		ministryCombo.setDisabled(isInViewMode());
		ministryCombo.setRequired(true);
		ministryCombo
				.addSelectionChangeHandler(new IAccounterComboSelectionChangeHandler<String>() {

					@Override
					public void selectedComboBoxItem(String selectItem) {
						if (selectItem.equals(getMinistryType().get(57))) {
							ministryNameOtehr.setDisabled(false);
							ministryNameOtehr.setRequired(true);
						} else {
							ministryNameOtehr.setDisabled(true);
							ministryNameOtehr.setRequired(false);
						}
					}
				});

		ministryNameOtehr = new TextItem("Ministry/Dept. Name(Other)");
		ministryNameOtehr.setHelpInformation(true);
		ministryNameOtehr.setDisabled(true);
		ministryNameOtehr.setRequired(false);

		otherDynamicForm = new DynamicForm();
		otherDynamicForm.setFields(statusCombo, deductorTypeOther,
				deductorTypeGovernment, govtState, paoCode, paoRegistration,
				ddoCode, ddoRegistration, ministryCombo, ministryNameOtehr,
				panNumber, tanNumber, addressSameBox);

		paoCode.setDisabled(true);
		paoRegistration.setDisabled(true);
		ddoCode.setDisabled(true);
		ddoRegistration.setDisabled(true);
		ministryCombo.setDisabled(true);
		ministryNameOtehr.setDisabled(true);
		govtState.setDisabled(true);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("100%");
		horizontalPanel.add(taxDynamicForm);
		horizontalPanel.add(otherDynamicForm);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(horizontalPanel);

		this.add(verticalPanel);

		deductorTypeOther.hide();
		deductorTypeGovernment.hide();

		if (data != null) {
			updateControls();
		} else {
			updateControlsForNew();
		}

		isViewInitialised = true;

	}

	protected void governmentSelected() {
		deductorTypeOther.hide();
		deductorTypeGovernment.show();
		paoCode.setDisabled(false);
		paoRegistration.setDisabled(false);
		ddoCode.setDisabled(false);
		ddoRegistration.setDisabled(false);
		ministryCombo.setDisabled(false);
		ministryNameOtehr.setDisabled(false);
		govtState.setDisabled(false);

		paoCode.setRequired(true);
		paoRegistration.setRequired(true);
		ddoCode.setRequired(true);
		ddoRegistration.setRequired(true);
		ministryCombo.setRequired(true);
		ministryNameOtehr.setRequired(true);
		govtState.setRequired(true);

	}

	protected void otherSelected() {
		deductorTypeOther.show();
		deductorTypeGovernment.hide();
		paoCode.setDisabled(true);
		paoRegistration.setDisabled(true);
		ddoCode.setDisabled(true);
		ddoRegistration.setDisabled(true);
		ministryCombo.setDisabled(true);
		ministryNameOtehr.setDisabled(true);
		govtState.setDisabled(true);

		paoCode.setRequired(false);
		paoRegistration.setRequired(false);
		ddoCode.setRequired(false);
		ddoRegistration.setRequired(false);
		ministryCombo.setRequired(false);
		ministryNameOtehr.setRequired(false);
		govtState.setRequired(false);

	}

	private void updateControls() {
		deductorName.setValue(data.getDeductorName());
		branchName.setValue(data.getBranch());
		flatNo.setValue(data.getFlatNo());
		buildingName.setValue(data.getBuildingName());
		streetName.setValue(data.getRoadName());
		areaName.setValue(data.getArea());
		cityName.setValue(data.getCity());
		pinNumber.setValue(Long.toString(data.getPinCode()));
		telephoneNumber.setValue(Long.toString(data.getTelephoneNumber()));
		faxNumber.setValue(Long.toString(data.getFaxNo()));

		stateCombo.setSelected(data.getState());

		if (data.isAddressdChanged()) {
			addressChangeCombo.setSelected(getYESNOList().get(0));
		} else {
			addressChangeCombo.setSelected(getYESNOList().get(1));
		}

		email.setValue(data.getEmailID());

		stateSelected = data.getState();
		statusSelected = data.getStatus();
		deductorTypeSelected = data.getDeductorType();

		if (data.getStatus().equals(getStatusTypes().get(0))) {
			otherSelected();
			statusCombo.setSelected(data.getStatus());
			deductorTypeOther.setSelected(data.getDeductorType());
			deductorTypeOther.setVisible(true);

		} else {
			governmentSelected();
			statusCombo.setSelected(data.getStatus());
			deductorTypeGovernment.setValue(data.getDeductorType());
			paoCode.setValue(data.getPaoCode());
			paoRegistration.setValue(Long.toString(data.getPaoRegistration()));
			ddoCode.setValue(data.getDdoCode());
			ddoRegistration.setValue(data.getDdoRegistration());
			ministryCombo.setValue(data.getMinistryDeptName());
			ministryNameOtehr.setValue(data.getMinistryDeptOtherName());

		}

		panNumber.setValue(data.getPanNumber());
		tanNumber.setValue(data.getTanNumber());
		addressSameBox.setValue(data.isAddressSameForResopsiblePerson());
		stdNumber.setValue(data.getStdCode());

	}

	private List<String> getMinistryType() {
		List<String> names = new ArrayList<String>();
		names = Utility.getMinistryType();
		return names;
	}

	private List<String> getOthersList() {

		List<String> names = new ArrayList<String>();
		for (int i = 8; i < 16; i++) {
			names.add(Utility.getDeductorTypes().get(i));
		}
		return names;
	}

	private List<String> getGovtList() {

		List<String> names = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			names.add(Utility.getDeductorTypes().get(i));
		}
		return names;
	}

	private List<String> getStatusTypes() {
		List<String> names = new ArrayList<String>();
		names.add("Others");
		names.add("Government");
		return names;
	}

	@Override
	public void deleteFailed(AccounterException caught) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSuccess(IAccounterCore result) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getViewTitle() {
		return "Particular of Deductor";
	}

	@Override
	public List<DynamicForm> getForms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public ValidationResult validate() {
		ValidationResult result = new ValidationResult();

		result.add(taxDynamicForm.validate());

		result.add(otherDynamicForm.validate());

		if (stateSelected == null) {
			result.addError(govtState, "Select a state");
		}
		if (deductorTypeSelected == null) {
			result.addError(statusCombo, "Select a Deductor Type");
		}
		if (statusSelected == null) {
			result.addError(statusCombo, "Select the status of Deductor");
		}

		if (!UIUtils.isValidEmail(email.getValue())) {
			result.addError(email, "Invalid email id.");
		}
		return result;

	}

	@Override
	public void saveAndUpdateView() {
		updateObject();

		saveOrUpdate(getData());

	}

	private void updateObject() {

		data.setDeductorName(deductorName.getValue());

		data.setBranch(branchName.getValue());

		data.setFlatNo(flatNo.getValue());

		data.setBuildingName(buildingName.getValue());

		data.setRoadName(streetName.getValue());

		data.setArea(areaName.getValue());

		data.setCity(cityName.getValue());

		data.setState(stateSelected);

		data.setPinCode(pinNumber.getNumber());

		if (telephoneNumber.getValue().length() > 0) {
			data.setTelephoneNumber(telephoneNumber.getNumber());
		} else {
			data.setTelephoneNumber(0);
		}

		if (faxNumber.getValue().length() > 0) {
			data.setFaxNo(faxNumber.getNumber());
		} else {
			data.setFaxNo(0);
		}

		data.setAddressdChanged(true_falseValue);

		data.setEmailID(email.getValue());

		data.setStatus(statusSelected);

		data.setDeductorType(deductorTypeSelected);

		if (statusSelected.equals(getStatusTypes().get(1))) {
			// if (paoCode.getValue().length() > 0) {
			data.setPaoCode(paoCode.getValue());
			// } else {
			// data.setPaoCode("");
			// }
			if (paoRegistration.getValue().length() > 0) {
				data.setPaoRegistration(paoRegistration.getNumber());
			} else {
				data.setPaoRegistration(0);
			}
			// if (ddoCode.getValue().length() > 0) {
			data.setDdoCode(ddoCode.getValue());
			// } else {
			// data.setDdoCode(0);
			// }
			// if (ddoRegistration.getValue().length() > 0) {
			data.setDdoRegistration(ddoRegistration.getValue());
			// } else {
			// data.setDdoRegistration(0);
			// }
			data.setMinistryDeptName(ministryCombo.getSelectedValue());
			data.setMinistryDeptOtherName(ministryNameOtehr.getValue());
			data.setGovtState(govtState.getSelectedValue());
		} else {
			data.setPaoCode("");
			data.setPaoRegistration(0);
			data.setDdoCode("");
			data.setDdoRegistration("");
			data.setMinistryDeptName("");
			data.setMinistryDeptOtherName("");
			data.setGovtState("");
		}

		data.setPanNumber(panNumber.getValue());
		data.setTanNumber(tanNumber.getValue());
		data.setAddressSameForResopsiblePerson(addressSameBox.getValue());
		// if (stdNumber.getValue().length() > 0) {
		data.setStdCode(stdNumber.getValue());
		// } else {
		// data.setStdCode(0);
		// }

	}

	@Override
	public void saveFailed(AccounterException exception) {
		super.saveFailed(exception);

		AccounterException accounterException = exception;
		int errorCode = accounterException.getErrorCode();
		String errorString = AccounterExceptions.getErrorString(errorCode);
		Accounter.showError(errorString);

		updateObject();

	}

	@Override
	protected void initRPCService() {
		// super.initRPCService();

		Accounter.createHomeService().getDeductorMasterDetails(
				new AccounterAsyncCallback<ClientTDSDeductorMasters>() {

					@Override
					public void onException(AccounterException exception) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResultSuccess(ClientTDSDeductorMasters result) {
						if (result != null) {
							data = result;
							if (isViewInitialised) {
								updateControls();
							}
						} else {
							data = new ClientTDSDeductorMasters();
							if (isViewInitialised) {
								updateControlsForNew();
							}
						}
					}
				});

	}

	private void updateControlsForNew() {
		ClientCompany company = getCompany();
		deductorName.setValue(company.getName());
		cityName.setValue(company.getRegisteredAddress().getCity());
		streetName.setValue(company.getRegisteredAddress().getStreet());
		areaName.setValue(company.getRegisteredAddress().getCountryOrRegion());
		// govtState.setValue(company.getRegisteredAddress()
		// .getStateOrProvinence());
		pinNumber.setValue(company.getRegisteredAddress().getZipOrPostalCode());
		telephoneNumber.setValue(company.getPhone());
		faxNumber.setValue(company.getFax());
	}

	@Override
	public void onEdit() {

		AccounterAsyncCallback<Boolean> editCallBack = new AccounterAsyncCallback<Boolean>() {

			@Override
			public void onException(AccounterException caught) {
				Accounter.showError(caught.getMessage());
			}

			@Override
			public void onResultSuccess(Boolean result) {
				if (result)
					enableFormItems();
			}

		};

		this.rpcDoSerivce.canEdit(AccounterCoreType.TDSDEDUCTORMASTER,
				data.getID(), editCallBack);
	}

	protected void enableFormItems() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createButtons(ButtonBar buttonBar) {
		// TODO Auto-generated method stub
		super.createButtons(buttonBar);
		saveAndNewButton.setVisible(false);
	}

}
