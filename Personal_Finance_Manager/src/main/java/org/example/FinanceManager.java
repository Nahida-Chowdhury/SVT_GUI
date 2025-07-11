package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FinanceManager {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction tx) {
        if (tx == null) throw new IllegalArgumentException("Transaction cannot be null");
        transactions.add(tx);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public double getBalance() {
        double income = transactions.stream()
                .filter(tx -> tx.getType() == Transaction.Type.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(tx -> tx.getType() == Transaction.Type.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return income - expense;
    }
}