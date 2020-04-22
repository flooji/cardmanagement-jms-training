package com.acn.jms.model;

import java.io.Serializable;

public class Payment implements Serializable{

	private static final long serialVersionUID = 1L;
	String id;
	long amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", amount=" + amount + "]";
	}
}
