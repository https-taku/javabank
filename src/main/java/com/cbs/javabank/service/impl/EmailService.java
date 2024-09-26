package com.cbs.javabank.service.impl;

import com.cbs.javabank.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);

}
