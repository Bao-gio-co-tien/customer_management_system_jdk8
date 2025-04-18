package impl;

import CMS.CustomerCare;
import CMS.CustomerCareServicePOA;

import java.util.HashMap;
import java.util.Map;

public class CustomerCareServiceImpl extends CustomerCareServicePOA {
    private final Map<String, CustomerCare> ticketDatabase;

    public CustomerCareServiceImpl() {
        this.ticketDatabase = new HashMap<>();

        ticketDatabase.put("T001", new CustomerCare("T001", "C001", "Billing issue", "Open", "High", "Pending resolution"));
        ticketDatabase.put("T002", new CustomerCare("T002", "C002", "Login issue", "In Progress", "Medium", "Investigating"));
        ticketDatabase.put("T003", new CustomerCare("T003", "C003", "Account upgrade request", "Closed", "Low", "Upgrade completed"));
        ticketDatabase.put("T004", new CustomerCare("T004", "C001", "Password reset", "Open", "High", "Pending user confirmation"));
    }

    @Override
    public String createTicket(CustomerCare ticket) {
        if (ticketDatabase.containsKey(ticket.ticketId)) {
            return null;
        }
        ticketDatabase.put(ticket.ticketId, ticket);
        return ticket.ticketId;
    }

    @Override
    public boolean updateTicket(String ticketId, CustomerCare updateTicket) {
        if (ticketDatabase.containsKey(ticketId)) {
            ticketDatabase.put(ticketId, updateTicket);
            return true;
        }
        return false;
    }

    @Override
    public CustomerCare getTicketInfo(String ticketId) {
        return ticketDatabase.get(ticketId);
    }

    @Override
    public CustomerCare[] getCustomerTicket(String customerId) {
        return ticketDatabase.values().stream()
                .filter(ticket -> ticket.customerId.equals(customerId))
                .toArray(CustomerCare[]::new);
    }
}
