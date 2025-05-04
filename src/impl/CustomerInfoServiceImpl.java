package impl;


import CMS.CustomerInfo;
import CMS.CustomerInfoServicePOA;
import db.DatabaseConnection;
import org.omg.CORBA.ORB;

import java.sql.*;
import java.util.ArrayList;

public class CustomerInfoServiceImpl extends CustomerInfoServicePOA {
    private ORB orb;

    public CustomerInfoServiceImpl(ORB orb) {
        this.orb = orb;
    }

    @Override
    public CustomerInfo getCustomer(String customerId) {
        CustomerInfo customer = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customers WHERE customerId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customer = new CustomerInfo(
                        rs.getString("customerId"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        rs.getString("customerPhone"),
                        rs.getString("customerAddress"),
                        rs.getString("customerStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public CustomerInfo[] searchCustomer(String criteria) {
        ArrayList<CustomerInfo> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customers WHERE customerName LIKE ? OR customerEmail LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + criteria + "%");
            pstmt.setString(2, "%" + criteria + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CustomerInfo c = new CustomerInfo(
                        rs.getString("customerId"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        rs.getString("customerPhone"),
                        rs.getString("customerAddress"),
                        rs.getString("customerStatus")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new CustomerInfo[0]);
    }

    @Override
    public boolean addCustomer(CustomerInfo customer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Customers (customerId, customerName, customerEmail, customerPhone, customerAddress, customerStatus) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.customerId);
            pstmt.setString(2, customer.customerName);
            pstmt.setString(3, customer.customerEmail);
            pstmt.setString(4, customer.customerPhone);
            pstmt.setString(5, customer.customerAddress);
            pstmt.setString(6, customer.customerStatus);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CustomerInfo[] getAllCustomer() {
        ArrayList<CustomerInfo> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                CustomerInfo c = new CustomerInfo(
                        rs.getString("customerId"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        rs.getString("customerPhone"),
                        rs.getString("customerAddress"),
                        rs.getString("customerStatus")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new CustomerInfo[0]);
    }
}
