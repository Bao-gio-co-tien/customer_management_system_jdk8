package gui;

import CMS.CustomerCare;
import CMS.CustomerCareService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerCareGUI extends JFrame {
    private CustomerCareService careService;

    private JTextField ticketIdField, customerIdField, issueField, statusField, priorityField, resolutionField;
    private JTextField searchCustomerIdField;
    private JTable ticketTable;

    public CustomerCareGUI(CustomerCareService careService) {
        this.careService = careService;
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản lý Chăm sóc Khách hàng");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        ticketIdField = new JTextField();
        customerIdField = new JTextField();
        issueField = new JTextField();
        statusField = new JTextField();
        priorityField = new JTextField();
        resolutionField = new JTextField();

        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Ticket"));
        inputPanel.add(new JLabel("Ticket ID:"));
        inputPanel.add(ticketIdField);
        inputPanel.add(new JLabel("Customer ID:"));
        inputPanel.add(customerIdField);
        inputPanel.add(new JLabel("Vấn đề:"));
        inputPanel.add(issueField);
        inputPanel.add(new JLabel("Trạng thái:"));
        inputPanel.add(statusField);
        inputPanel.add(new JLabel("Độ ưu tiên:"));
        inputPanel.add(priorityField);
        inputPanel.add(new JLabel("Giải pháp:"));
        inputPanel.add(resolutionField);

        JButton btnCreate = new JButton("Tạo Ticket");
        JButton btnUpdate = new JButton("Cập nhật Ticket");
        inputPanel.add(btnCreate);
        inputPanel.add(btnUpdate);

        // Bảng hiển thị ticket
        String[] columnNames = {"Ticket ID", "Customer ID", "Vấn đề", "Trạng thái", "Ưu tiên", "Giải pháp"};
        ticketTable = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane tableScroll = new JScrollPane(ticketTable);

        // Tìm ticket theo customerId
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchCustomerIdField = new JTextField(15);
        JButton btnSearch = new JButton("Tìm Ticket");
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm ticket theo Customer ID"));
        searchPanel.add(new JLabel("Customer ID:"));
        searchPanel.add(searchCustomerIdField);
        searchPanel.add(btnSearch);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnCreate.addActionListener(e -> createTicket());
        btnUpdate.addActionListener(e -> updateTicket());
        btnSearch.addActionListener(e -> loadTickets());
    }

    private void createTicket() {
        try {
            CustomerCare ticket = new CustomerCare();
            ticket.ticketId = ticketIdField.getText();
            ticket.customerId = customerIdField.getText();
            ticket.issue = issueField.getText();
            ticket.status = statusField.getText();
            ticket.priority = priorityField.getText();
            ticket.resolution = resolutionField.getText();

            String newId = careService.createTicket(ticket);
            JOptionPane.showMessageDialog(this, "Tạo ticket thành công với ID: " + newId);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void updateTicket() {
        try {
            CustomerCare update = new CustomerCare();
            update.ticketId = ticketIdField.getText();
            update.customerId = customerIdField.getText();
            update.issue = issueField.getText();
            update.status = statusField.getText();
            update.priority = priorityField.getText();
            update.resolution = resolutionField.getText();

            boolean result = careService.updateTicket(update.ticketId, update);
            if (result) {
                JOptionPane.showMessageDialog(this, "Cập nhật ticket thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hoặc cập nhật thất bại.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void loadTickets() {
        try {
            String customerId = searchCustomerIdField.getText();
            CustomerCare[] tickets = careService.getCustomerTicket(customerId);

            DefaultTableModel model = (DefaultTableModel) ticketTable.getModel();
            model.setRowCount(0);

            for (CustomerCare c : tickets) {
                model.addRow(new Object[]{
                        c.ticketId,
                        c.customerId,
                        c.issue,
                        c.status,
                        c.priority,
                        c.resolution
                });
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + msg);
    }
}