package gui;

import CMS.EmailCampaign;
import CMS.EmailMarketingService;

import javax.swing.*;
import java.awt.*;

public class EmailCampaignGUI extends JFrame {
    private EmailMarketingService emailService;

    private JTextField campaignIdField, titleField, contentField, targetField, statusField;
    private JTextField sendCampaignIdField, segmentCriteriaField;

    public EmailCampaignGUI(EmailMarketingService emailService) {
        this.emailService = emailService;
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản lý Email Marketing");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel tạo campaign
        JPanel createPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        campaignIdField = new JTextField();
        titleField = new JTextField();
        contentField = new JTextField();
        targetField = new JTextField();
        statusField = new JTextField();

        createPanel.setBorder(BorderFactory.createTitledBorder("Tạo chiến dịch mới"));
        createPanel.add(new JLabel("Mã chiến dịch:"));
        createPanel.add(campaignIdField);
        createPanel.add(new JLabel("Tiêu đề:"));
        createPanel.add(titleField);
        createPanel.add(new JLabel("Nội dung:"));
        createPanel.add(contentField);
        createPanel.add(new JLabel("Phân khúc mục tiêu:"));
        createPanel.add(targetField);
        createPanel.add(new JLabel("Trạng thái:"));
        createPanel.add(statusField);

        JButton btnCreate = new JButton("Tạo chiến dịch");
        createPanel.add(new JLabel());
        createPanel.add(btnCreate);

        // Panel gửi campaign
        JPanel sendPanel = new JPanel(new FlowLayout());
        sendPanel.setBorder(BorderFactory.createTitledBorder("Gửi chiến dịch"));
        sendCampaignIdField = new JTextField(10);
        JButton btnSend = new JButton("Gửi");
        sendPanel.add(new JLabel("Mã chiến dịch:"));
        sendPanel.add(sendCampaignIdField);
        sendPanel.add(btnSend);

        // Panel lấy phân khúc khách hàng
        JPanel segmentPanel = new JPanel(new FlowLayout());
        segmentPanel.setBorder(BorderFactory.createTitledBorder("Lấy phân khúc khách hàng"));
        segmentCriteriaField = new JTextField(10);
        JButton btnGetSegment = new JButton("Lấy phân khúc");
        segmentPanel.add(new JLabel("Tiêu chí phân khúc:"));
        segmentPanel.add(segmentCriteriaField);
        segmentPanel.add(btnGetSegment);

        // Panel xem thông tin campaign
        JPanel infoPanel = new JPanel(new FlowLayout());
        JButton btnGetCampaignInfo = new JButton("Xem thông tin chiến dịch");
        infoPanel.add(btnGetCampaignInfo);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createPanel);
        mainPanel.add(sendPanel);
        mainPanel.add(segmentPanel);
        mainPanel.add(infoPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Action listeners
        btnCreate.addActionListener(e -> createCampaign());
        btnSend.addActionListener(e -> sendCampaign());
        btnGetSegment.addActionListener(e -> getSegment());
        btnGetCampaignInfo.addActionListener(e -> showCampaignInfo());
    }

    private void createCampaign() {
        try {
            EmailCampaign campaign = new EmailCampaign();
            campaign.campaignId = campaignIdField.getText();
            campaign.title = titleField.getText();
            campaign.content = contentField.getText();
            campaign.targetSegment = targetField.getText();
            campaign.status = statusField.getText();

            boolean result = emailService.createCampaign(campaign);
            if (result) {
                JOptionPane.showMessageDialog(this, "Tạo chiến dịch thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể tạo chiến dịch.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void sendCampaign() {
        try {
            String campaignId = sendCampaignIdField.getText();
            boolean result = emailService.sendCampaign(campaignId);
            if (result) {
                JOptionPane.showMessageDialog(this, "Đã gửi chiến dịch.");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể gửi chiến dịch.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void getSegment() {
        try {
            String criteria = segmentCriteriaField.getText();
            String[] segment = emailService.getCustomerSegment(criteria);
            JOptionPane.showMessageDialog(this, "Phân khúc khách hàng:\n" + String.join(", ", segment));
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showCampaignInfo() {
        try {
            String id = sendCampaignIdField.getText();
            EmailCampaign campaign = emailService.getCampaign(id);
            String message = "Mã: " + campaign.campaignId + "\n"
                    + "Tiêu đề: " + campaign.title + "\n"
                    + "Nội dung: " + campaign.content + "\n"
                    + "Phân khúc: " + campaign.targetSegment + "\n"
                    + "Trạng thái: " + campaign.status;
            JOptionPane.showMessageDialog(this, message);
        } catch (Exception e) {
            showError("Không tìm thấy chiến dịch: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + msg);
    }
}
