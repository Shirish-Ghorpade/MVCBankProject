package com.aurionpro.entity;

public class Transactions {
	private long senderAccountNumber;
	private long receiverAccountNumber;
	private String typeOfTransaction;
	private double amount;
	private String date;

	public Transactions(long senderAccountNumber, long receiverAccountNumber, String typeOfTransaction, double amount,
			String date) {
		super();
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
		this.typeOfTransaction = typeOfTransaction;
		this.amount = amount;
		this.date = date;
	}
	public long getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(long senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public long getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public void setReceiverAccountNumber(long receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public String getTypeOfTransaction() {
		return typeOfTransaction;
	}

	public void setTypeOfTransaction(String typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
