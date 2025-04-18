package impl;

import CMS.CustomerInfo;
import CMS.CustomerInfoServicePOA;
import org.omg.CORBA.ORB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerInfoServiceImpl extends CustomerInfoServicePOA {
    private ORB orb;
    private Map<String, CustomerInfo> customerDatabase;

    public CustomerInfoServiceImpl(ORB orb) {
        this.orb = orb;
        this.customerDatabase = new HashMap<>();

        customerDatabase.put("C001", new CustomerInfo("C001", "John Doe", "john@example.com", "123456789", "123 Main St", "Active"));
    }

    @Override
    public CustomerInfo getCustomer(String customerId) {
        return customerDatabase.get(customerId);
    }

    @Override
    public CustomerInfo[] searchCustomer(String criteria) {
        ArrayList<CustomerInfo> result = new ArrayList<>();
        for (CustomerInfo customer : customerDatabase.values()) {
            if (customer.customerName.contains(criteria) || customer.customerEmail.contains(criteria)) {
                result.add(customer);
            }
        }
        return result.toArray(new CustomerInfo[0]);
    }

    @Override
    public boolean addCustomer(CustomerInfo customerInfo) {
        // Implement the logic to add a new customer
        if (customerDatabase.containsKey(customerInfo.customerId)) {
            return false;
        }
        customerDatabase.put(customerInfo.customerId, customerInfo);
        return true;
    }

    @Override
    public CustomerInfo[] getAllCustomer() {
        // Implement the logic to retrieve all customers
        return customerDatabase.values().toArray(new CustomerInfo[0]);
    }
}
