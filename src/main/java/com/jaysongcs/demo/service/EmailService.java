package com.jaysongcs.demo.service;

import com.jaysongcs.demo.entity.EmailEntity;

public class EmailService {
    public boolean isServiceActive() {
        return true;
    }

    public void send(EmailEntity emailEntity) {
        System.out.println("Sending message -> " + emailEntity.getMessage());
    }
}
