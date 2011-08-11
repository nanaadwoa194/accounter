package com.vimukti.accounter.web.client.ui.core;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vimukti.accounter.web.client.AccounterAsyncCallback;
import com.vimukti.accounter.web.client.core.AccounterCommand;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.core.Utility;
import com.vimukti.accounter.web.client.exception.AccounterException;
import com.vimukti.accounter.web.client.exception.AccounterExceptions;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.grids.DialogGrid;

/**
 * GroupDialog is Dialog which contains common controls to display and manage
 * all groups and group Lists .like tax groups,Customer Group List.
 * 
 * @author kumar kasimala
 * 
 * 
 */

public abstract class GroupDialog<T extends IAccounterCore> extends
		BaseDialog<T> {

	private VerticalPanel buttonsLayout;
	private HorizontalPanel bodyLayout;
	protected Button button1;
	private Button button2;
	private Button button3;
	protected DialogGrid listGridView;
	private GroupDialogButtonsHandler dialogButtonsHandler;
	private InputDialogHandler dialogHandler;
	public final static int FIRST_BUTTON = 1;
	public final static int SECOND_BUTTON = 2;
	public final static int THIRD_BUTTON = 3;

	private List<IsSerializable> recordsList = new ArrayList<IsSerializable>();

	private RecordAddhandler recordAddhandler;

	private boolean isEdit;
	private AccounterAsyncCallback<T> callBack;

	public GroupDialog(String title, String descript) {
		super(title, descript);
		initialise();
	}

	/*
	 */
	private void initialise() {

		bodyLayout = new HorizontalPanel();
		mainPanel.setCellVerticalAlignment(bodyLayout,
				HasVerticalAlignment.ALIGN_TOP);
		bodyLayout.setWidth("100%");
		bodyLayout.setSpacing(5);

		listGridView = new DialogGrid(false);
		listGridView.setView(this);
		listGridView.addColumns(setColumns());
		listGridView.setColumnTypes(getColunmTypes());
		listGridView.isEnable = false;
		listGridView.init();
		listGridView.setWidth("100%");
		initGrid(getRecords());

		/**
		 * buttons Layout
		 */
		buttonsLayout = new VerticalPanel();
		buttonsLayout.setWidth("100px");
		buttonsLayout.setSpacing(5);

		button1 = new Button(constants.add());
		button1.setWidth("80px");

		button1.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (dialogButtonsHandler != null)
					firstButtonClick();

			}
		});

		button2 = new Button(constants.edit());
		button2.setEnabled(false);
		button2.setWidth("80px");
		button2.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (dialogButtonsHandler != null)
					secondButtonClick();
			}
		});

		button3 = new Button(this.constants.remove());
		button3.setEnabled(false);
		button3.setWidth("80px");
		button3.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (dialogButtonsHandler != null)
					thirdButtonClick();
			}
		});

		enableEditRemoveButtons(false);

		buttonsLayout.add(button1);
		buttonsLayout.add(button2);
		buttonsLayout.add(button3);
		// button2.enabledButton();
		// button3.enabledButton();
		button1.setFocus(true);
		bodyLayout.add(listGridView);
		if (Accounter.getUser().canDoInvoiceTransactions())
			bodyLayout.add(buttonsLayout);
		buttonsLayout.getElement().getParentElement().setAttribute("width",
				"25%");
		setBodyLayout(bodyLayout);
		cancelBtn.setTitle(this.constants.close());
		dialogHandler = new InputDialogHandler() {

			public void onCancel() {
				closeWindow();
			}

			public boolean onOK() {

				return true;
			}
		};
		addInputDialogHandler(dialogHandler);

	}

	public Integer[] getColunmTypes() {
		return null;
	}

	public void firstButtonClick() {
		dialogButtonsHandler.onFirstButtonClick();
	}

	public void secondButtonClick() {
		dialogButtonsHandler.onSecondButtonClick();
	}

	public void thirdButtonClick() {
		dialogButtonsHandler.onThirdButtonClick();
	}

	protected void closeWindow() {
		if (dialogButtonsHandler != null)
			dialogButtonsHandler.onCloseButtonClick();

	}

	/**
	 * delete currently Selected Record
	 */
	public void deleteSelectedRecord() {
		deleteRecord();
		enableEditRemoveButtons(false);
	}

	/**
	 * Get total Records As Array
	 * 
	 * @return
	 */
	public IAccounterCore[] getRecordsAsArray() {
		return (IAccounterCore[]) this.listGridView.getRecords().toArray();
	}

	/**
	 * Get Total Records As List
	 * 
	 * @return
	 */

	public List<IsSerializable> getRecordsAsList() {
		return this.recordsList;
	}

	/**
	 * add Handler On Record add.default Implementation does nothing
	 * 
	 * @param addhandler
	 */
	public void addRecordAddHandler(RecordAddhandler addhandler) {
		this.recordAddhandler = addhandler;
	}

	/**
	 * Group Dialog Buttons handler
	 * 
	 * @param dialogButtonsHandler
	 */
	public void addGroupButtonsHandler(
			GroupDialogButtonsHandler dialogButtonsHandler) {
		this.dialogButtonsHandler = dialogButtonsHandler;
	}

	/**
	 * Change Title of Buttons
	 * 
	 * @param buttonType
	 * @param title
	 */
	public void setButtonTitle(int buttonType, String title) {
		switch (buttonType) {
		case FIRST_BUTTON:
			button1.setTitle(title);
			break;
		case SECOND_BUTTON:
			button2.setTitle(title);
			break;
		case THIRD_BUTTON:
			button3.setTitle(title);
			break;

		default:
			break;
		}
	}

	public IAccounterCore getSelectedRecord() {
		return (IAccounterCore) listGridView.getSelection();
	}

	public void enableEditRemoveButtons(boolean enable) {
		button2.setEnabled(enable);
		button3.setEnabled(enable);
	}

	public void refreshGrid() {

	}

	public void addButton(Button button) {
		buttonsLayout.add(button);
	}

	public DialogGrid getGrid() {
		return this.listGridView;
	}

	public void initGrid(List<T> resultrecords) {
		if (resultrecords != null) {

			List<T> records = resultrecords;

			if (records != null) {
				for (T t : records) {
					addToGrid(t);
				}

			}
		}
	}

	public void addToGrid(T objectToBeAdded) {
		listGridView.addData((IsSerializable) objectToBeAdded);
	}

	public abstract String[] setColumns();

	protected abstract List<T> getRecords();

	public void deleteCallBack() {
	}

	public void addCallBack() {
	}

	public void editCallBack() {
	}

	private void updateOrAddRecord(T obj) {
		IAccounterCore core = (IAccounterCore) obj;
		if (Utility.getObject((List<IAccounterCore>) (ArrayList) listGridView
				.getRecords(), core.getID()) != null)
			deleteRecord();

		listGridView.addData((IsSerializable) obj);
		if (callBack != null) {
			callBack.onResultSuccess(obj);
		}
	}

	private void deleteRecord() {
		listGridView.deleteRecord(listGridView.getSelection());
	}

	public void deleteObject(IAccounterCore core) {
		Accounter.deleteObject(this, core);
	}

	@Override
	protected void onAttach() {
		super.onAttach();
	}

	@Override
	protected void onLoad() {
		okbtn.setFocus(true);
		cancelBtn.setFocus(true);
		super.onLoad();
	}

	@Override
	public void deleteFailed(AccounterException caught) {
		AccounterException accounterException = (AccounterException) caught;
		int errorCode = accounterException.getErrorCode();
		String errorString = AccounterExceptions.getErrorString(errorCode);
		Accounter.showError(errorString);
	}

	@Override
	public void deleteSuccess(Boolean result) {
		if (result)
			deleteRecord();
	}

	@Override
	public void saveSuccess(IAccounterCore object) {
		updateOrAddRecord((T) object);
		// if (callBack != null) {
		// callBack.onSuccess(object);
		// }
	}

	@Override
	public void saveFailed(AccounterException exception) {
		AccounterException accounterException = (AccounterException) exception;
		int errorCode = accounterException.getErrorCode();
		String errorString = AccounterExceptions.getErrorString(errorCode);
		Accounter.showError(errorString);
	}

	@Override
	public String toString() {
		return Accounter.constants().classNameis() + this.getText();
	}

	@Override
	public void processupdateView(IAccounterCore core, int command) {
		if (core.getObjectType() == listGridView.getType()) {

			IAccounterCore obj = (IAccounterCore) Utility.getObjectFromList(
					listGridView.getRecords(), core.getID());
			switch (command) {
			case AccounterCommand.CREATION_SUCCESS:
			case AccounterCommand.UPDATION_SUCCESS:
				if (obj == null) {
					listGridView.addData(core);
				} else {
					listGridView.deleteRecord(obj);
					listGridView.addData(core);
				}
				for (int i = 0; i < listGridView.getRecords().size(); i++) {
					List<IsSerializable> list = listGridView.getRecords();
					if (list.get(i) == core) {
						listGridView.rowFormatter.addStyleName(i, "selected");
					} else {
						listGridView.rowFormatter
								.removeStyleName(i, "selected");
					}
				}
				break;
			case AccounterCommand.DELETION_SUCCESS:
				if (obj != null) {
					listGridView.deleteRecord(listGridView.indexOf(obj));
				}
				break;

			}
		}

	}

	public void addCallBack(AccounterAsyncCallback<T> callback) {
		this.callBack = callback;
	}

	protected void saveOrUpdate(final T core) {

		final AccounterAsyncCallback<Long> transactionCallBack = new AccounterAsyncCallback<Long>() {

			public void onException(AccounterException caught) {
				saveFailed(caught);
				caught.printStackTrace();
				// TODO handle other kind of errors
			}

			public void onResultSuccess(Long result) {
				core.setID(result);
				Accounter.getCompany().processUpdateOrCreateObject(core);
				saveSuccess(core);
			}

		};
		if (core.getID() == 0) {
			Accounter.createCRUDService().create((IAccounterCore) core,
					transactionCallBack);
		} else {
			Accounter.createCRUDService().update((IAccounterCore) core,
					transactionCallBack);
		}

	}
}
