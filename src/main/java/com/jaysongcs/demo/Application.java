package com.jaysongcs.demo;

import com.jaysongcs.demo.service.EmailService;
import com.jaysongcs.demo.service.MessageService;

public class Application {
    public static void main(String[] args) {
        EmailService emailService = new EmailService();

        MessageService messageService = new MessageService(emailService);
        messageService.sendEmailMsg("Sending message via email");
    }
}
