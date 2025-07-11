package org.example;

public class Main {
    public static void main(String[] args) {
        FinanceManager manager = new FinanceManager();
        new FinanceManagerGUI(manager);
    }
}