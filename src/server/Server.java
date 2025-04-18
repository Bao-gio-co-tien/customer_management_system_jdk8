package server;

import impl.CustomerCareServiceImpl;
import impl.CustomerInfoServiceImpl;
import impl.CustomerUpdateServiceImpl;
import impl.EmailMarketingServiceImpl;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextExtPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import CMS.*;

public class Server {
    public static void main(String[] args) {
        try {
            // Initialize ORB
            ORB orb = ORB.init(args, null);

            // Get root POA
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Create servant instances
            CustomerInfoServiceImpl infoService = new CustomerInfoServiceImpl(orb);
            CustomerUpdateServiceImpl updateService = new CustomerUpdateServiceImpl();
            EmailMarketingServiceImpl emailService = new EmailMarketingServiceImpl();
            CustomerCareServiceImpl careService = new CustomerCareServiceImpl();

            // Bind servants to POA
            org.omg.CORBA.Object infoObj = rootPOA.servant_to_reference(infoService);
            org.omg.CORBA.Object updateObj = rootPOA.servant_to_reference(updateService);
            org.omg.CORBA.Object emailObj = rootPOA.servant_to_reference(emailService);
            org.omg.CORBA.Object careObj = rootPOA.servant_to_reference(careService);

            // Get Naming Service
            org.omg.CORBA.Object namingObj = orb.resolve_initial_references("NameService");
            NamingContextExt namingContext = NamingContextExtHelper.narrow(namingObj);

            // Bind objects to Naming Service
            NameComponent[] infoName = namingContext.to_name("CustomerInfoService");
            NameComponent[] updateName = namingContext.to_name("CustomerUpdateService");
            NameComponent[] emailName = namingContext.to_name("EmailMarketingService");
            NameComponent[] careName = namingContext.to_name("CustomerCareService");

            namingContext.rebind(infoName, infoObj);
            namingContext.rebind(updateName, updateObj);
            namingContext.rebind(emailName, emailObj);
            namingContext.rebind(careName, careObj);

            System.out.println("Server is running...");

            // Run ORB
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}