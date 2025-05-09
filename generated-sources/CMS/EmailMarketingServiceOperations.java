package CMS;


/**
* CMS/EmailMarketingServiceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/cms.idl
* Friday, April 18, 2025 1:35:57 AM ICT
*/

public interface EmailMarketingServiceOperations 
{
  boolean createCampaign (CMS.EmailCampaign campaign);
  boolean sendCampaign (String campaignId);
  String[] getCustomerSegment (String segmentCriteria);
  CMS.EmailCampaign getCampaign (String campaignId);
} // interface EmailMarketingServiceOperations
