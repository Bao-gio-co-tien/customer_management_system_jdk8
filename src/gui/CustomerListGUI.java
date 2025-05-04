package gui;

import CMS.CustomerInfo;
import CMS.CustomerInfoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerListGUI extends JFrame {
    private CustomerInfoService customerService;
    private JTable table;

    public CustomerListGUI(CustomerInfoService customerService) {
        this.customerService = customerService;
        initComponents();
    }

    private void initComponents() {
        setTitle("Danh sách khách hàng");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] columnNames = {"ID", "Tên", "Email", "SĐT", "Địa chỉ", "Trạng thái"};
        table = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnLoad = new JButton("Tải khách hàng");
        btnLoad.addActionListener(e -> loadCustomers());
        add(btnLoad, BorderLayout.SOUTH);
    }

    private void loadCustomers() {
        try {
            CustomerInfo[] customers = customerService.getAllCustomer();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear table

            for (CustomerInfo c : customers) {
                model.addRow(new Object[]{
                        c.customerId,
                        c.customerName,
                        c.customerEmail,
                        c.customerPhone,
                        c.customerAddress,
                        c.customerStatus
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải khách hàng: " + ex.getMessage());
        }
    }


}
