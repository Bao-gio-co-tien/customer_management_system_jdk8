package client;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import CMS.*;

public class Client {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object namingObj = orb.resolve_initial_references("NameService");
            NamingContextExt namingContext = NamingContextExtHelper.narrow(namingObj);

            CustomerInfoService infoService = CustomerInfoServiceHelper.narrow(
                    namingContext.resolve_str("CustomerInfoService"));
            CustomerUpdateService updateService = CustomerUpdateServiceHelper.narrow(
                    namingContext.resolve_str("CustomerUpdateService"));
            EmailMarketingService emailService = EmailMarketingServiceHelper.narrow(
                    namingContext.resolve_str("EmailMarketingService"));
            CustomerCareService careService = CustomerCareServiceHelper.narrow(
                    namingContext.resolve_str("CustomerCareService"));

            // 1. Get customer
            CustomerInfo customer = infoService.getCustomer("C001");
            System.out.println("Customer: " + customer.customerName + ", Email: " + customer.customerEmail);

            // 2. Add customer
            CustomerInfo newCustomer = new CustomerInfo("C002", "Jane Smith", "jane@example.com", "987654321", "456 Oak St", "Active");
            boolean added = infoService.addCustomer(newCustomer);
            System.out.println("Customer added: " + added);

            // 3. Update status
            boolean updated = updateService.updateStatus("C001", "Inactive");
            System.out.println("Status updated: " + updated);

            // 4. Create email campaign
            EmailCampaign campaign = new EmailCampaign("CMP002", "Promo Campaign", "Discount offer!", "Active Users", "Draft");
            boolean campaignCreated = emailService.createCampaign(campaign);
            System.out.println("Campaign created: " + campaignCreated);

            // 5. Create ticket
            CustomerCare ticket = new CustomerCare("T005", "C001", "Support issue", "Open", "High", "Pending");
            String ticketId = careService.createTicket(ticket);
            System.out.println("Ticket created: " + ticketId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}