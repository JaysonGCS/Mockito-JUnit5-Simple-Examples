package com.jaysongcs.demo.service;

import com.jaysongcs.demo.entity.EmailEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EmailServiceTest {
    private EmailService classToTest;

    @BeforeEach
    public void init() {
        classToTest = new EmailService();
    }

    @Test
    public void given_message_in_emailEntity_when_convertEmailEntityToCharArr_then_result_charArr_same_as_message_in_charArr() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String testMsg = "message";
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setMessage(testMsg);

        // Using reflection to get the method
        Method convertEmailEntityToCharArrMethod = EmailService.class.getDeclaredMethod("convertEmailEntityToCharArr", EmailEntity.class);
        // Override the accessor
        convertEmailEntityToCharArrMethod.setAccessible(true);
        // Invoke the method and provide input object
        char[] result = (char[]) convertEmailEntityToCharArrMethod.invoke(classToTest, emailEntity);

        // Validate result
        Assertions.assertArrayEquals(testMsg.toCharArray(), result);
    }
}