package gui;

import CMS.CustomerInfo;
import CMS.CustomerInfoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerInfoGUI extends JFrame {
    private CustomerInfoService service;
    private JTable table;
    private JTextField searchField, idField, nameField, emailField, phoneField, addressField, statusField;

    public CustomerInfoGUI(CustomerInfoService service) {
        this.service = service;
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản lý thông tin khách hàng");
        setSize(900, 500);
        setLayout(new BorderLayout());

        // Table
        table = new JTable(new DefaultTableModel(new String[]{"ID", "Tên", "Email", "SĐT", "Địa chỉ", "Trạng thái"}, 0));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel điều khiển
        JPanel controlPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        statusField = new JTextField();

        controlPanel.add(new JLabel("ID:")); controlPanel.add(idField);
        controlPanel.add(new JLabel("Tên:")); controlPanel.add(nameField);
        controlPanel.add(new JLabel("Email:")); controlPanel.add(emailField);
        controlPanel.add(new JLabel("SĐT:")); controlPanel.add(phoneField);
        controlPanel.add(new JLabel("Địa chỉ:")); controlPanel.add(addressField);
        controlPanel.add(new JLabel("Trạng thái:")); controlPanel.add(statusField);
        add(controlPanel, BorderLayout.NORTH);

        // Nút
        JPanel buttonPanel = new JPanel();
        JButton btnLoad = new JButton("Tải tất cả");
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Thêm mới");

        searchField = new JTextField(15);
        buttonPanel.add(new JLabel("Từ khóa:"));
        buttonPanel.add(searchField);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnAdd);

        add(buttonPanel, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> loadAll());
        btnSearch.addActionListener(e -> search());
        btnAdd.addActionListener(e -> addCustomer());
    }

    private void loadAll() {
        try {
            CustomerInfo[] list = service.getAllCustomer();
            loadTable(list);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void search() {
        try {
            String criteria = searchField.getText();
            CustomerInfo[] result = service.searchCustomer(criteria);
            loadTable(result);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void addCustomer() {
        try {
            CustomerInfo info = new CustomerInfo();
            info.customerId = idField.getText();
            info.customerName = nameField.getText();
            info.customerEmail = emailField.getText();
            info.customerPhone = phoneField.getText();
            info.customerAddress = addressField.getText();
            info.customerStatus = statusField.getText();

            boolean added = service.addCustomer(info);
            if (added) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadAll();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void loadTable(CustomerInfo[] data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (CustomerInfo c : data) {
            model.addRow(new Object[]{
                    c.customerId,
                    c.customerName,
                    c.customerEmail,
                    c.customerPhone,
                    c.customerAddress,
                    c.customerStatus
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + message);
    }
}
