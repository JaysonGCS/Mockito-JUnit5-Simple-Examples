package com.jaysongcs.demo.service;

import com.jaysongcs.demo.entity.EmailEntity;

public class EmailService {
    public boolean isServiceActive() {
        return true;
    }

    public void send(EmailEntity emailEntity) {
        char[] msgArr = convertEmailEntityToCharArr(emailEntity);
        System.out.println("Message char array length" + msgArr.length);
    }

    // A simple method that converts emailEntity's message into char array
    private char[] convertEmailEntityToCharArr(EmailEntity emailEntity) {
        return emailEntity.getMessage().toCharArray();
    }
}
