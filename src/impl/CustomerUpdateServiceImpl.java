package impl;

import CMS.CustomerInfo;
import CMS.CustomerUpdateServicePOA;

import java.util.HashMap;
import java.util.Map;

public class CustomerUpdateServiceImpl extends CustomerUpdateServicePOA {
    private Map<String, CustomerInfo> customerDatabase;

    public CustomerUpdateServiceImpl() {
        this.customerDatabase = new HashMap<>();

        customerDatabase.put("C001", new CustomerInfo("C001", "John Doe", "john@example.com", "123456789", "123 Main St", "Active"));
    }

    @Override
    public boolean updateCustomerInfo(String customerId, CustomerInfo newInfo) {
        if (customerDatabase.containsKey(customerId)) {
            customerDatabase.put(customerId, newInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStatus(String customerId, String newStatus) {
        CustomerInfo customer = customerDatabase.get(customerId);
        if (customer != null) {
            customer.customerStatus = newStatus;
            customerDatabase.put(customerId, customer);
            return true;
        }
        return false;
    }

    @Override
    public void logCustomerChange(String customerId, String changeType) {
        System.out.println("Customer ID: " + customerId + ", Change Type: " + changeType);
    }
}
