package impl;

import CMS.EmailCampaign;
import CMS.EmailMarketingServicePOA;

import java.util.HashMap;
import java.util.Map;

public class EmailMarketingServiceImpl extends EmailMarketingServicePOA {
    private Map<String, EmailCampaign> campaignDatabase = new HashMap<>();

    public EmailMarketingServiceImpl() {
        this.campaignDatabase = new HashMap<>();

        campaignDatabase.put("CMP001", new EmailCampaign("CMP001", "Welcome Campaign", "Welcome to our service!", "New Users", "Draft"));
    }

    @Override
    public boolean createCampaign(EmailCampaign campaign) {
        if (campaignDatabase.containsKey(campaign.campaignId)) {
            return false; // Campaign with this ID already exists
        }
        campaignDatabase.put(campaign.campaignId, campaign);
        return true;
    }

    @Override
    public boolean sendCampaign(String campaignId) {
        EmailCampaign campaign = campaignDatabase.get(campaignId);
        if (campaign != null && campaign.status.equals("Draft")) {
            campaign.status = "Sent";
            campaignDatabase.put(campaign.campaignId, campaign);
            return true;
        }
        return false;
    }

    @Override
    public String[] getCustomerSegment(String segmentCriteria) {
        if ("New Users".equals(segmentCriteria)) {
            return new String[]{"C001", "C002", "C003"};
        }
        return new String[0];
    }

    @Override
    public EmailCampaign getCampaign(String campaignId) {
        return campaignDatabase.get(campaignId);
    }
}
