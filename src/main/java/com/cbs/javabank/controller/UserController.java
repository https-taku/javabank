package com.cbs.javabank.controller;

import com.cbs.javabank.dto.BankResponse;
import com.cbs.javabank.dto.CreditDebitRequest;
import com.cbs.javabank.dto.EnquiryRequest;
import com.cbs.javabank.dto.UserRequest;
import com.cbs.javabank.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    private UserRequest userRequest;

    @PostMapping
    public BankResponse createaccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @GetMapping("balanceenquiry")
    public BankResponse balanceenquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.balanceEnquiry(enquiryRequest);

    }

    @GetMapping("nameenquiry")
    public String nameenquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("credit")
    public BankResponse creditaccount(@RequestBody CreditDebitRequest request) {
        return userService.creditAccount(request);

    }

    @PostMapping("debit")
    public BankResponse debitaccount(@RequestBody CreditDebitRequest request) {
        return userService.debitAccount(request);
    }
}
