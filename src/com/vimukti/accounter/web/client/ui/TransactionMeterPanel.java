package com.vimukti.accounter.web.client.ui;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vimukti.accounter.web.client.Global;
import com.vimukti.accounter.web.client.core.TransactionMeterEventType;
import com.vimukti.accounter.web.client.externalization.AccounterMessages;
import com.vimukti.accounter.web.client.util.CoreEvent;
import com.vimukti.accounter.web.client.util.CoreEventHandler;

public class TransactionMeterPanel extends SimplePanel {
	protected static final AccounterMessages messages = Global.get().messages();
	private int transactionsCount = 0;
	private Label countLabel;
	private StyledPanel meterBar, meterbar1;
	private int maxTransactionCount;

	public TransactionMeterPanel() {
		transactionsCount = Accounter.getCompany().getTransactionsCount();
		maxTransactionCount = Accounter.getCompany().getTransactionsLimit();
		initGui();
		Type<CoreEventHandler<TransactionMeterEventType>> type = CoreEvent
				.getType(TransactionMeterEventType.class);
		Accounter.getEventBus().addHandler(type,
				new CoreEventHandler<TransactionMeterEventType>() {

					@Override
					public void onAdd(TransactionMeterEventType obj) {
						increase();
					}

					@Override
					public void onDelete(TransactionMeterEventType obj) {
						decrease();
					}

					@Override
					public void onChange(TransactionMeterEventType obj) {
						// TODO Auto-generated method stub

					}
				});
	}

	public TransactionMeterPanel(int transactionNo) {
		transactionsCount = transactionNo;
		initGui();
	}

	private void initGui() {
		VerticalPanel mainPanel = new VerticalPanel();
		countLabel = new Label(messages.transactionsUsed(transactionsCount,
				maxTransactionCount));
		meterBar = new StyledPanel("transactionMeterBar");
		meterbar1 = new StyledPanel("inner_transactionMeterBar");
		mainPanel.add(countLabel);
		meterBar.add(meterbar1);
		addStyleToMeterbar();
		mainPanel.add(meterBar);
		mainPanel.addStyleName("transaction_meter");
		add(mainPanel);
	}

	public void increase() {
		transactionsCount++;
		updateLabel();
	}

	private void updateLabel() {
		addStyleToMeterbar();
		countLabel.setText(messages.transactionsUsed(transactionsCount,
				maxTransactionCount));

	}

	public void decrease() {
		transactionsCount--;
		updateLabel();
	}

	public void addStyleToMeterbar() {
		int width = transactionsCount * 10;
		meterbar1.setWidth(width + "px");
		int g = (maxTransactionCount * 60 / 100);
		int y = (maxTransactionCount * 80 / 100);
		if (transactionsCount <= g) {
			meterbar1.getElement().getStyle().setBackgroundColor("green");
		} else if (transactionsCount > g && transactionsCount < y) {
			meterbar1.getElement().getStyle().setBackgroundColor("yellow");
		} else {
			meterbar1.getElement().getStyle().setBackgroundColor("red");
		}
	}
}