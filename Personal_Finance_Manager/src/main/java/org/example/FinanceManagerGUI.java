package org.example;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FinanceManagerGUI extends JFrame {
    private final FinanceManager manager;
    private JTextField categoryField, amountField, dateField;
    private JComboBox<String> typeComboBox;
    private JLabel balanceLabel;
    private DefaultTableModel tableModel;

    public FinanceManagerGUI(FinanceManager manager) {
        this.manager = manager;
        setTitle("💰 Personal Finance Manager");
        setSize(750, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel topPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        topPanel.setBackground(new Color(250, 250, 250));

        categoryField = new JTextField();
        amountField = new JTextField();
        dateField = new JTextField(LocalDate.now().toString());
        typeComboBox = new JComboBox<>(new String[]{"INCOME", "EXPENSE"});

        JButton addButton = new JButton("➕ Add");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);

        topPanel.add(new JLabel("Category:"));
        topPanel.add(new JLabel("Amount:"));
        topPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        topPanel.add(new JLabel("Type:"));
        topPanel.add(new JLabel("")); // spacer

        topPanel.add(categoryField);
        topPanel.add(amountField);
        topPanel.add(dateField);
        topPanel.add(typeComboBox);
        topPanel.add(addButton);

        String[] columns = {"Category", "Amount", "Date", "Type"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 10, 0, 10));

        balanceLabel = new JLabel("Balance: 0.00");
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        balanceLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(balanceLabel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTransaction());
    }

    private void addTransaction() {
        try {
            String category = categoryField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            Transaction.Type type = Transaction.Type.valueOf((String) typeComboBox.getSelectedItem());

            if (category.isEmpty() || amount <= 0) {
                throw new IllegalArgumentException("Invalid input");
            }

            manager.addTransaction(new Transaction(category, amount, date, type));
            updateTable();
            clearInputs();

        } catch (NumberFormatException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Transaction tx : manager.getTransactions()) {
            tableModel.addRow(new Object[]{
                    tx.getCategory(),
                    String.format("%.2f", tx.getAmount()),
                    tx.getDate(),
                    tx.getType()
            });
        }
        balanceLabel.setText(String.format("Balance: %.2f", manager.getBalance()));
    }

    private void clearInputs() {
        categoryField.setText("");
        amountField.setText("");
        dateField.setText(LocalDate.now().toString());
        typeComboBox.setSelectedIndex(0);
    }
}