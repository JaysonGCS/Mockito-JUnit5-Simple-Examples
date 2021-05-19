package com.jaysongcs.demo.service;

import com.jaysongcs.demo.entity.EmailEntity;
import com.jaysongcs.demo.utility.EncryptionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<EmailEntity> emailEntityArgCaptor;

    private MessageService classToTest;

    @BeforeEach
    public void init() {
        classToTest = new MessageService(emailService);
    }

    @Test
    void when_emailService_is_inactive_sendEmailMsg_then_result_is_false_and_no_email_sent() {
        String msg = "Test Message";
        Mockito.when(emailService.isServiceActive()).thenReturn(false);

        boolean result = classToTest.sendEmailMsg(msg);

        Assertions.assertEquals(false, result);
        // assertFalse is more concise
        Assertions.assertFalse(result);

        // never() or times(0) are not expecting method to be called in this case
        // both of them are doing the same job, just giving example here
        Mockito.verify(emailService, Mockito.never()).send(any(EmailEntity.class));
        Mockito.verify(emailService, Mockito.times(0)).send(any(EmailEntity.class));
    }

    @Test
    void given_emailService_is_active_when_sendEmailMsg_then_result_is_true_and_email_sent() {
        String msg = "Test Message";
        Mockito.when(emailService.isServiceActive()).thenReturn(true);
        boolean result = classToTest.sendEmailMsg(msg);

        Assertions.assertTrue(result);
        Mockito.verify(emailService, Mockito.times(1)).send(any(EmailEntity.class));

    }

    @Test
    void given_emailService_is_active_when_sendEmailMsg_then_result_is_true_and_email_sent_has_input_message() {
        String msg = "Test Message";
        Mockito.when(emailService.isServiceActive()).thenReturn(true);

        boolean result = classToTest.sendEmailMsg(msg);
        Assertions.assertTrue(result);
        Mockito.verify(emailService).send(emailEntityArgCaptor.capture());
        EmailEntity emailEntity = emailEntityArgCaptor.getValue();
        Assertions.assertEquals(emailEntity.getMessage(), msg);
    }

    @Test
    void given_emailService_is_active_when_sendEncryptedEmailMsg_then_result_is_true_and_email_sent() {
        String msg = "Test Message";
        Mockito.when(emailService.isServiceActive()).thenReturn(true);

        boolean result = classToTest.sendEncryptedEmailMsg(msg);
        Assertions.assertTrue(result);
        Mockito.verify(emailService, Mockito.times(1)).send(any(EmailEntity.class));

    }

    @Test
    void given_emailService_is_active_when_sendEncryptedEmailMsg_then_result_is_true_and_email_sent_has_encrypted_message() {
        String msg = "Test Message";
        Mockito.when(emailService.isServiceActive()).thenReturn(true);

        boolean result = classToTest.sendEncryptedEmailMsg(msg);
        Assertions.assertTrue(result);
        Mockito.verify(emailService).send(emailEntityArgCaptor.capture());
        EmailEntity emailEntity = emailEntityArgCaptor.getValue();
        Assertions.assertNotEquals(emailEntity.getMessage(), msg);
    }

    @Test
    void given_emailService_is_active_when_sendEncryptedEmailMsg_then_result_is_true_and_simpleEncrypt_is_used_and_email_sent_has_encrypted_message() {
        String msg = "Test Message";
        String encryptedMsg = "encrypted";
        Mockito.when(emailService.isServiceActive()).thenReturn(true);

        try (MockedStatic<EncryptionUtils> encryptionUtilsMS = Mockito.mockStatic(EncryptionUtils.class)) {
            // Here we expect simpleEncrypt to return crafted message if called
            encryptionUtilsMS.when(() -> EncryptionUtils.simpleEncrypt(msg)).thenReturn(encryptedMsg);
            boolean result = classToTest.sendEncryptedEmailMsg(msg);
            Assertions.assertTrue(result);
            Mockito.verify(emailService).send(emailEntityArgCaptor.capture());
            EmailEntity emailEntity = emailEntityArgCaptor.getValue();

            // We can verify if the static simpleEncrypt method is called
            encryptionUtilsMS.verify(() -> EncryptionUtils.simpleEncrypt(msg), Mockito.times(1));
            encryptionUtilsMS.verify(() -> EncryptionUtils.simpleEncrypt(any(String.class)), Mockito.times(1));

            // We also verify that not so simple encrypt method is not called
            encryptionUtilsMS.verify(() -> EncryptionUtils.notSoSimpleEncrypt(any()), Mockito.never());

            Assertions.assertNotEquals(emailEntity.getMessage(), msg);

            // Here we are able to assert the email entity contains encrypted message
            Assertions.assertEquals(emailEntity.getMessage(), encryptedMsg);
        }
    }

    @Test
    void given_emailService_is_active_when_sendEncryptedEmailMsg_then_result_is_true_and_notSoSimpleEncrypt_is_used_and_email_sent_has_encrypted_message() {
        String msg = "Test Message That Is Too Long";
        String encryptedMsg = "encrypted";
        Mockito.when(emailService.isServiceActive()).thenReturn(true);

        try (MockedStatic<EncryptionUtils> encryptionUtilsMS = Mockito.mockStatic(EncryptionUtils.class)) {
            // Here we expect notSoSimpleEncrypt to return crafted message if called
            encryptionUtilsMS.when(() -> EncryptionUtils.notSoSimpleEncrypt(msg)).thenReturn(encryptedMsg);
            boolean result = classToTest.sendEncryptedEmailMsg(msg);
            Assertions.assertTrue(result);
            Mockito.verify(emailService).send(emailEntityArgCaptor.capture());
            EmailEntity emailEntity = emailEntityArgCaptor.getValue();

            // We can verify if the static notSoSimpleEncrypt method is called
            encryptionUtilsMS.verify(() -> EncryptionUtils.notSoSimpleEncrypt(msg), Mockito.times(1));
            encryptionUtilsMS.verify(() -> EncryptionUtils.notSoSimpleEncrypt(any(String.class)), Mockito.times(1));

            // We also verify that simple encrypt method is not called
            encryptionUtilsMS.verify(() -> EncryptionUtils.simpleEncrypt(any()), Mockito.never());

            Assertions.assertNotEquals(emailEntity.getMessage(), msg);

            // Here we are able to assert the email entity contains encrypted message
            Assertions.assertEquals(emailEntity.getMessage(), encryptedMsg);
        }
    }
}