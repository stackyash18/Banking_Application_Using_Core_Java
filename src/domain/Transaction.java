package domain;

import java.time.LocalDateTime;

public class Transaction {
    public static String getAccountNumber;
    private String transactionId;
    private Type type;
    private String accountNumber;
    private Double amount;
    private LocalDateTime timeStamp;
    private String note;

    public Transaction(String transactionId, Type type, String accountNumber, Double amount, LocalDateTime timeStamp, String note) {
        this.transactionId = transactionId;
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.note = note;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Transaction(String accountNumber, double amount, String string, String note) {

    }

    public Transaction(String accountNumber, double amount, String string, String note, LocalDateTime now, Type type) {

    }
}
