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
public class SaveAndCloseButton extends ImageButton {

	private AbstractBaseView<?> view;

	public SaveAndCloseButton(String save) {
		super(save, Accounter.getFinanceImages().saveAndClose());
		this.addStyleName("saveAndClose-Btn");
	}

	public void setView(AbstractBaseView<?> view) {
		this.view = view;
		addHandler();
	}

	/**
	 * Creates new Instance
	 */
	public SaveAndCloseButton(AbstractBaseView<?> baseView) {

		super(messages.saveAndClose(), Accounter.getFinanceImages()
				.saveAndClose());
		this.view = baseView;
		this.addStyleName("saveAndClose-Btn");
		this.setTitle(messages.clickThisTo(this.getText(), view.getAction()
				.getViewName()));
		addHandler();
	}

	private void addHandler() {
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {

				// Need to clarify after implemented approval process.
				if (view instanceof AbstractTransactionBaseView) {
					AbstractTransactionBaseView<?> transactionView = (AbstractTransactionBaseView<?>) view;
					final ClientTransaction transaction = transactionView
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

					if (!transactionView.isTemplate
							&& transaction.getSaveStatus() != ClientTransaction.STATUS_VOID) {
						transaction
								.setSaveStatus(ClientTransaction.STATUS_APPROVE);
					}
				}
				view.onSave(false);
			}
		});
	}
}
