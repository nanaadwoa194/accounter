package com.vimukti.accounter.web.client.core;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.vimukti.accounter.web.client.core.ClientFinanceDate;

public class SearchResultlist implements IsSerializable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int transactionType;
	private ClientFinanceDate date;
	// private String name;
	private double amount;
	private long id;

	public ClientFinanceDate getDate() {
		return date;
	}

	public void setDate(ClientFinanceDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

}
