import CMS.CustomerInfoService;
import CMS.CustomerInfoServiceHelper;
import CMS.CustomerUpdateService;
import CMS.CustomerUpdateServiceHelper;
import CMS.EmailMarketingService;
import CMS.EmailMarketingServiceHelper;
import CMS.CustomerCareService;
import CMS.CustomerCareServiceHelper;

import gui.CustomerInfoGUI;
import gui.UpdateCustomerGUI;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Lấy các service từ CORBA Naming Service
            CustomerInfoService infoService =
                    CustomerInfoServiceHelper.narrow(ncRef.resolve_str("CustomerInfoService"));
            CustomerUpdateService updateService =
                    CustomerUpdateServiceHelper.narrow(ncRef.resolve_str("CustomerUpdateService"));
            EmailMarketingService emailService =
                    EmailMarketingServiceHelper.narrow(ncRef.resolve_str("EmailMarketingService"));
            CustomerCareService careService =
                    CustomerCareServiceHelper.narrow(ncRef.resolve_str("CustomerCareService"));

            // Mở giao diện chính
            SwingUtilities.invokeLater(() -> {
                JFrame mainFrame = new JFrame("Hệ thống quản lý khách hàng CORBA");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setSize(400, 300);
                mainFrame.setLayout(new java.awt.GridLayout(4, 1));

                JButton btnCustomerList = new JButton("Danh sách khách hàng");
                btnCustomerList.addActionListener(e -> new CustomerInfoGUI(infoService).setVisible(true));
                mainFrame.add(btnCustomerList);

                JButton btnUpdateCustomer = new JButton("Cập nhật khách hàng");
                btnUpdateCustomer.addActionListener(e -> new UpdateCustomerGUI(updateService).setVisible(true));
                mainFrame.add(btnUpdateCustomer);

                JButton btnEmailMarketing = new JButton("Chiến dịch Email");
                btnEmailMarketing.addActionListener(e -> new gui.EmailCampaignGUI(emailService).setVisible(true));
                mainFrame.add(btnEmailMarketing);

                JButton btnCustomerCare = new JButton("Chăm sóc khách hàng");
                btnCustomerCare.addActionListener(e -> new gui.CustomerCareGUI(careService).setVisible(true));
                mainFrame.add(btnCustomerCare);

                mainFrame.setVisible(true);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
