package com.contact_manager.scm.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
