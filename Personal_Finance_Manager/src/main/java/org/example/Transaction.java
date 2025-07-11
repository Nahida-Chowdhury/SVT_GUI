package org.example;
import java.time.LocalDate;

public class Transaction {
    public enum Type { INCOME, EXPENSE }

    private String category;
    private double amount;
    private LocalDate date;
    private Type type;

    public Transaction(String category, double amount, LocalDate date, Type type) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public Type getType() { return type; }
}


