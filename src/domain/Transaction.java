package domain;

import java.time.LocalDateTime;

public class Transaction {
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
}
