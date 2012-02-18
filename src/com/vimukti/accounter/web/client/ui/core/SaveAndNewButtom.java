/**
 * 
 */
package com.vimukti.accounter.web.client.ui.core;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vimukti.accounter.web.client.core.ClientRecurringTransaction;
import com.vimukti.accounter.web.client.core.ClientTransaction;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.exception.AccounterException;
import com.vimukti.accounter.web.client.ui.AbstractBaseView;
import com.vimukti.accounter.web.client.ui.Accounter;
import com.vimukti.accounter.web.client.ui.IDeleteCallback;
import com.vimukti.accounter.web.client.ui.ImageButton;

/**
 * @author Prasanna Kumar G
 * 
 */
public class SaveAndNewButtom extends ImageButton {

	private AbstractBaseView<?> view;

	/**
	 * Creates new Instance
	 */
	public SaveAndNewButtom(AbstractBaseView<?> view) {
		super(messages.saveAndNew(), Accounter.getFinanceImages().saveAndNew());
		this.view = view;
		this.setTitle(messages.clickThisToOpenNew(view.getAction()
				.getViewName()));
		// this.addStyleName("saveAndNew-Btn");
		addClichHandler();
	}

	/**
	 * 
	 */
	private void addClichHandler() {
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				// Need to clarify after implemented approval process.
				if (view instanceof AbstractTransactionBaseView) {
					AbstractTransactionBaseView<?> transactionView = (AbstractTransactionBaseView<?>) view;
					ClientTransaction transaction = transactionView
							.getTransactionObject();
					ClientRecurringTransaction recurTransaction = transaction
							.getClientRecurringTransaction();
					if (recurTransaction != null
							&& recurTransaction.getType() != ClientRecurringTransaction.RECURRING_SCHEDULED) {
						boolean isValid = transactionView
								.validateAndUpdateTransaction(false);
						if (isValid) {
							view.saveAndUpdateView();
							view.setSaveCliecked(true);
							view.setCloseOnSave(true);
						}
						return;
					}
					if (transaction.isDraft()) {
						Accounter.deleteObject(new IDeleteCallback() {

							@Override
							public void deleteSuccess(IAccounterCore result) {

							}

							@Override
							public void deleteFailed(AccounterException caught) {
								// TODO Auto-generated method stub

							}
						}, transaction);
						transaction.setID(0);
					}
					if (transaction.getSaveStatus() != ClientTransaction.STATUS_VOID) {
						transaction
								.setSaveStatus(ClientTransaction.STATUS_APPROVE);
					}
				}
				view.onSave(true);
			}
		});

	}

}
