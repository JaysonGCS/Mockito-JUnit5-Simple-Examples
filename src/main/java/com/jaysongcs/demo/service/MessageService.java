package com.jaysongcs.demo.service;

import com.jaysongcs.demo.entity.EmailEntity;
import com.jaysongcs.demo.utility.EncryptionUtils;

public class MessageService {
    private final EmailService emailService;

    public MessageService(EmailService emailService) {
        this.emailService = emailService;
    }

    public boolean sendEmailMsg(String message) {
        if (emailService.isServiceActive()) {
            EmailEntity emailEntity = new EmailEntity();
            emailEntity.setMessage(message);
            emailService.send(emailEntity);
            return true;
        } else {
            return false;
        }
    }

    public boolean sendEncryptedEmailMsg(String message) {
        if (emailService.isServiceActive()) {
            String encryptedMsg;
            if (message.length() < 15) {
                encryptedMsg = EncryptionUtils.simpleEncrypt(message);
            } else {
                encryptedMsg = EncryptionUtils.notSoSimpleEncrypt(message);
            }

            EmailEntity emailEntity = new EmailEntity();
            emailEntity.setMessage(encryptedMsg);
            emailService.send(emailEntity);
            return true;
        } else {
            return false;
        }
    }
}
