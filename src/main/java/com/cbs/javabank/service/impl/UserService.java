package com.cbs.javabank.service.impl;

import com.cbs.javabank.dto.BankResponse;
import com.cbs.javabank.dto.CreditDebitRequest;
import com.cbs.javabank.dto.EnquiryRequest;
import com.cbs.javabank.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);

    BankResponse creditAccount(CreditDebitRequest request);

    BankResponse debitAccount(CreditDebitRequest request);



}
