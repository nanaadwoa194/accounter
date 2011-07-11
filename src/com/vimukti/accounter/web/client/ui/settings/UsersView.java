package com.vimukti.accounter.web.client.ui.settings;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vimukti.accounter.web.client.core.IAccounterCore;
import com.vimukti.accounter.web.client.data.ClientUser;
import com.vimukti.accounter.web.client.ui.FinanceApplication;
import com.vimukti.accounter.web.client.ui.HistoryTokenUtils;
import com.vimukti.accounter.web.client.ui.core.AccounterButton;
import com.vimukti.accounter.web.client.ui.core.BaseView;
import com.vimukti.accounter.web.client.ui.forms.DynamicForm;

public class UsersView extends BaseView<ClientUser> {

	private HTML generalSettingsHTML;
	private Label titleLabel;
	private VerticalPanel mainLayPanel, usersPanel, recentActivityPanel;
	private DecoratedTabPanel tabPanel;
	private FlexTable flexTable;
	private UsersListGrid usersListGrid;
	private RecentActivityListGrid activityListGrid;
	AccounterButton inviteUserButton;

	@Override
	public List<DynamicForm> getForms() {
		return null;
	}

	@Override
	public void onEdit() {
	}

	@Override
	public void init() {
		// super.init();
		createControls();
	}

	@Override
	public void initData() {
		super.initData();
	}

	@SuppressWarnings("deprecation")
	private void createControls() {

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				e.printStackTrace();
			}
		});

		mainLayPanel = new VerticalPanel();
		flexTable = new FlexTable();
		generalSettingsHTML = new HTML(FinanceApplication.getSettingsMessages()
				.generalSettingsLabel());
		generalSettingsHTML.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				generalSettingsHTML.getElement().getStyle().setCursor(
						Cursor.POINTER);
				generalSettingsHTML.getElement().getStyle().setTextDecoration(
						TextDecoration.UNDERLINE);
			}
		});
		generalSettingsHTML.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				generalSettingsHTML.getElement().getStyle().setTextDecoration(
						TextDecoration.NONE);
			}
		});
		generalSettingsHTML.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SettingsActionFactory.getGeneralSettingsAction().run(null,
						false);
			}
		});
		titleLabel = new Label(FinanceApplication.getSettingsMessages()
				.usersTitle());

		titleLabel.removeStyleName("gwt-Label");
		titleLabel.setStyleName(FinanceApplication.getVendorsMessages()
				.lableTitle());

		inviteUserButton = new AccounterButton("Invite a User");
		inviteUserButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				HistoryTokenUtils.setPresentToken(SettingsActionFactory
						.getInviteUserAction(), null);
				SettingsActionFactory.getInviteUserAction().run(null, true);
			}
		});
		tabPanel = new DecoratedTabPanel();
		tabPanel.add(getUsersPanel(), FinanceApplication.getSettingsMessages()
				.users());
		tabPanel.add(getRecentActivityPanel(), FinanceApplication
				.getSettingsMessages().recentActivity());
		tabPanel.selectTab(0);

		// flexTable.setWidget(0, 0, generalSettingsHTML);
		flexTable.setWidget(1, 0, titleLabel);

		mainLayPanel.add(flexTable);
		if (FinanceApplication.getUser().isCanDoUserManagement()) {
			mainLayPanel.add(inviteUserButton);
			inviteUserButton.enabledButton();
		}

		mainLayPanel.add(getUsersPanel());
		mainLayPanel.setWidth("100%");

		// saveAndCloseButton.setVisible(false);
		// saveAndNewButton.setVisible(false);
		// cancelButton.setVisible(false);

		add(mainLayPanel);
	}

	private VerticalPanel getRecentActivityPanel() {
		recentActivityPanel = new VerticalPanel();

		activityListGrid = new RecentActivityListGrid(false);
		activityListGrid.setRecentActivityGridView(this);
		activityListGrid.init();

		recentActivityPanel.add(activityListGrid);
		recentActivityPanel.setCellHorizontalAlignment(activityListGrid,
				HasHorizontalAlignment.ALIGN_CENTER);
		activityListGrid.setWidth("90%");
		activityListGrid.getElement().getStyle().setMarginTop(20, Unit.PX);

		return recentActivityPanel;
	}

	private VerticalPanel getUsersPanel() {
		usersPanel = new VerticalPanel();

		usersListGrid = new UsersListGrid(false);
		usersListGrid.setUsersView(this);
		usersListGrid.init();
		usersListGrid
				.setRecords(FinanceApplication.getCompany().getUsersList());
		usersPanel.add(usersListGrid);
		usersPanel.setCellHorizontalAlignment(usersListGrid,
				HasHorizontalAlignment.ALIGN_CENTER);
		usersListGrid.setWidth("100%");
		usersListGrid.getElement().getStyle().setMarginTop(10, Unit.PX);
		usersPanel.setWidth("100%");

		return usersPanel;
	}

	@Override
	public void print() {
	}

	@Override
	public void printPreview() {
	}

	@Override
	public void deleteFailed(Throwable caught) {
	}

	@Override
	public void deleteSuccess(Boolean result) {
	}

	@Override
	public void processupdateView(IAccounterCore core, int command) {
	}

	@Override
	public void fitToSize(int height, int width) {
		this.setHeight(height + "");
		usersListGrid.setHeight(height + "");
		activityListGrid.setHeight(height + "");
	}

	@Override
	protected String getViewTitle() {
		return FinanceApplication.getSettingsMessages().users();
	}

}
