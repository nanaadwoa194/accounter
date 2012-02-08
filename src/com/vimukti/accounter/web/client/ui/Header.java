package com.vimukti.accounter.web.client.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vimukti.accounter.web.client.AccounterAsyncCallback;
import com.vimukti.accounter.web.client.Global;
import com.vimukti.accounter.web.client.core.ClientCompany;
import com.vimukti.accounter.web.client.core.ClientUser;
import com.vimukti.accounter.web.client.exception.AccounterException;
import com.vimukti.accounter.web.client.externalization.AccounterMessages;
import com.vimukti.accounter.web.client.externalization.IMessageStats;
import com.vimukti.accounter.web.client.ui.core.ActionFactory;

public class Header extends FlowPanel {

	protected static AccounterMessages messages = Global.get().messages();

	private Image userImage;

	public static Label companyNameLabel;

	public static Anchor userName;

	private SimplePanel headerLinks;

	private Anchor logout, help;

	private VerticalPanel panel1;
	private HorizontalPanel panel2, panel3;
	private String gettingStartedStatus = messages.hideGettingStarted();
	private MenuBar helpBar;
	private ClientCompany company = null;

	protected PopupPanel popupPanel;

	private Anchor companiesLink;

	/**
	 * Creates new Instance
	 */
	public Header() {
		addStyleName("page_header");
		createControls();
	}

	public Header(ClientCompany company) {
		this.company = company;
		addStyleName("page_header");
		createControls();
	}

	private void createControls() {
		companyNameLabel = new Label();
		String companyName = "";
		if (company != null) {
			companyNameLabel.addStyleName("companyName");
			companyName = Accounter.getCompany().getDisplayName();
		}
		Header.companyNameLabel.setText(companyName);

		userImage = new Image("/images/User.png");
		userImage.getElement().getStyle().setPaddingBottom(4, Unit.PX);

		if (company != null) {
			if (Accounter.getCompany().isConfigured()) {
				userName = new Anchor(Accounter.getUser().getFullName());
			} else {
				userName = new Anchor(Accounter.getUser().getFullName());
			}
		} else {
			userName = new Anchor();
		}

		// userName.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		if (company != null) {
			if (!Accounter.isLoggedInFromDomain()
					&& Accounter.getCompany().isConfigured()) {
				userName.addStyleName("userName-style");
				userName.getElement().getStyle()
						.setTextDecoration(TextDecoration.UNDERLINE);
				userName.getElement().getStyle().setCursor(Cursor.POINTER);

				userName.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						ActionFactory.getUserDetailsAction().run(null, false);
					}
				});
			}
		}
		// userName.setWidth("100%");
		logout = new Anchor(messages.logout(), "/main/logout");
		logout.addStyleName("logout-html");
		// logout.setWidth(((messages.logout().length() * 4) + 19)+
		// "px");
		helpBar = new MenuBar();
		initializeHelpBar();
		helpBar.setStyleName("helpBar");
		help = new Anchor(messages.help());
		// help.setWidth(((messages.help().length() * 2) + 19) +
		// "px");
		help.addStyleName("help-style");
		help.addStyleName("helpBar");
		help.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// ViewManager viewManager = MainFinanceWindow.getViewManager();
				// if (viewManager != null) {
				// viewManager.addRemoveHelpPanel();
				// } else {
				Window.open("http://help.accounterlive.com", "_blank", "");
				// }
			}
		});

		Image logoImg = new Image();
		logoImg.setUrl("/images/Accounter_logo.png");
		logoImg.setStyleName("logo");
		logoImg.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ActionFactory.getCompanyHomeAction().run(null, false);
			}
		});

		panel1 = new VerticalPanel();
		// panel1.setWidth("25%");
		panel1.setWidth("100%");
		panel1.add(logoImg);
		SimplePanel companiesLinkPanel = new SimplePanel();

		panel2 = new HorizontalPanel();
		Accounter.createHomeService().getClientCompaniesCount(
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						int subscriptionType = 0;// Accounter.getCompany().getSubscriptionType();
						if (!((subscriptionType == ClientCompany.BEFORE_PAID_FETURE || subscriptionType == ClientCompany.FREE_CLIENT) && subscriptionType < 2)) {
							companiesLink = new Anchor(messages.companies(),
									"/main/companies");
							companiesLink.addStyleName("companiesLink");
							companiesLink.getElement().setAttribute(
									"lang",
									((CldrImpl) GWT.create(CldrImpl.class))
											.isRTL() ? "ar" : "en");
							panel2.add(companiesLink);
							companiesLink.getElement().getParentElement()
									.addClassName("companies_link_parent");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

		panel2.add(companiesLinkPanel);
		panel2.add(companyNameLabel);
		companiesLinkPanel.getElement().getParentElement()
				.addClassName("arrow-right");

		// panel2.setWidth("50%");
		panel2.setWidth("100%");
		companyNameLabel.getElement().getParentElement()
				.addClassName("companyName-parent");

		headerLinks = new SimplePanel();
		headerLinks.addStyleName("header_links");

		panel3 = new HorizontalPanel();
		panel3.setSpacing(6);
		panel3.addStyleName("logout-help-welcome");
		panel3.add(userName);
		panel2.addStyleName("companies_title");
		panel3.add(help);
		panel3.add(logout);
		ClientUser user = Accounter.getUser();
		if (user != null && user.getEmail() != null
				&& user.getEmail().trim().equals("support@accounterlive.com")) {
			panel3.add(createStatisticsLink());
		}
		// panel3.setCellHorizontalAlignment(panel3, ALIGN_RIGHT);
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.setWidth("100%");
		hpanel.add(panel1);
		hpanel.add(panel2);

		// this.add(panel1);
		// this.add(panel2);
		headerLinks.add(panel3);
		// this.add(headerLinks);
		hpanel.add(headerLinks);
		hpanel.setCellWidth(panel1, "30%");
		hpanel.setCellWidth(panel2, "40%");
		hpanel.setCellWidth(headerLinks, "30%");
		this.add(hpanel);

		// Element spanEle = DOM.createSpan();
		// spanEle.setInnerText("Vimukti Technologies Pvt Ltd");
		// spanEle.addClassName("vimutki-text");
		// DOM.appendChild(panel1.getElement(), spanEle);
	}

	private Anchor createStatisticsLink() {
		Anchor saveStatistics = new Anchor("Save Statistics");
		saveStatistics.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				IMessageStats messageStats = (IMessageStats) Global.get()
						.messages();
				ArrayList<String> messagesUsageOrder = messageStats
						.getMessagesUsageOrder();
				HashMap<String, Integer> messgaesUsageCount = messageStats
						.getMessgaesUsageCount();
				Accounter.createTranslateService().updateMessgaeStats(
						messagesUsageOrder, messgaesUsageCount,
						new AccounterAsyncCallback<Boolean>() {

							@Override
							public void onException(AccounterException exception) {
								Accounter
										.showInformation("Messgae Statistics Updation Failed!");
							}

							@Override
							public void onResultSuccess(Boolean result) {
								Accounter
										.showInformation("Messgae Statistics Updated!");
							}
						});
			}
		});
		return saveStatistics;
	}

	public void initializeHelpBar() {
		MenuItem menuItem = helpBar.addItem(messages.help(), getHelpMenuBar());
		menuItem.getElement().getStyle().setColor("#072027");
		Image child = new Image();
		child.addStyleName("menu_arrow");
		child.setUrl(Accounter.getThemeImages().drop_down_indicator().getURL());
		DOM.insertChild(menuItem.getElement(), child.getElement(), 0);
	}

	private CustomMenuBar getHelpMenuBar() {

		CustomMenuBar helpMenu = new CustomMenuBar();
		helpMenu.addItem(messages.helpCenter(), true, new Command() {

			@Override
			public void execute() {

			}
		});

		helpMenu.addItem(gettingStartedStatus, new Command() {

			@Override
			public void execute() {
				if (gettingStartedStatus.equals(messages.hideGettingStarted())) {
					// DashBoardView.hideGettingStarted();
					changeHelpBarContent(messages.showGettingStarted());
				} else {
					// DashBoardView.showGettingStarted();
					changeHelpBarContent(messages.hideGettingStarted());
				}
			}
		});
		return helpMenu;
	}

	public void changeHelpBarContent(String gettingStartedStatus) {
		this.gettingStartedStatus = gettingStartedStatus;
		panel3.remove(helpBar);
		helpBar = new MenuBar();
		initializeHelpBar();
		helpBar.setStyleName("helpBar");
		panel3.insert(helpBar, 1);
	}
}
