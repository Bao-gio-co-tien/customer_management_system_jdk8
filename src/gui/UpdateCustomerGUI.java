package gui;

import CMS.CustomerInfo;
import CMS.CustomerUpdateService;

import javax.swing.*;
import java.awt.*;

public class UpdateCustomerGUI extends JFrame {
    private CustomerUpdateService updateService;

    private JTextField idField, nameField, emailField, phoneField, addressField, statusField;
    private JTextField newStatusField, logTypeField;

    public UpdateCustomerGUI(CustomerUpdateService updateService) {
        this.updateService = updateService;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cập nhật thông tin khách hàng");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        statusField = new JTextField();

        inputPanel.add(new JLabel("Mã khách hàng:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Tên:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("SĐT:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Địa chỉ:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Trạng thái:"));
        inputPanel.add(statusField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));

        JButton btnUpdateInfo = new JButton("Cập nhật toàn bộ thông tin");
        JButton btnUpdateStatus = new JButton("Cập nhật trạng thái");
        JButton btnLogChange = new JButton("Ghi log thay đổi");

        newStatusField = new JTextField();
        logTypeField = new JTextField();

        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.add(new JLabel("Trạng thái mới:"));
        statusPanel.add(newStatusField);
        statusPanel.add(btnUpdateStatus);

        JPanel logPanel = new JPanel(new FlowLayout());
        logPanel.add(new JLabel("Loại thay đổi:"));
        logPanel.add(logTypeField);
        logPanel.add(btnLogChange);

        buttonPanel.add(btnUpdateInfo);
        buttonPanel.add(statusPanel);
        buttonPanel.add(logPanel);

        add(buttonPanel, BorderLayout.CENTER);

        btnUpdateInfo.addActionListener(e -> updateCustomerInfo());
        btnUpdateStatus.addActionListener(e -> updateStatus());
        btnLogChange.addActionListener(e -> logChange());
    }

    private void updateCustomerInfo() {
        try {
            CustomerInfo info = new CustomerInfo();
            info.customerId = idField.getText();
            info.customerName = nameField.getText();
            info.customerEmail = emailField.getText();
            info.customerPhone = phoneField.getText();
            info.customerAddress = addressField.getText();
            info.customerStatus = statusField.getText();

            boolean result = updateService.updateCustomerInfo(info.customerId, info);
            if (result) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void updateStatus() {
        try {
            String id = idField.getText();
            String newStatus = newStatusField.getText();
            boolean result = updateService.updateStatus(id, newStatus);
            if (result) {
                JOptionPane.showMessageDialog(this, "Trạng thái đã được cập nhật.");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật trạng thái.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void logChange() {
        try {
            String id = idField.getText();
            String changeType = logTypeField.getText();
            updateService.logCustomerChange(id, changeType);
            JOptionPane.showMessageDialog(this, "Đã ghi log thay đổi.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + msg);
    }
}
