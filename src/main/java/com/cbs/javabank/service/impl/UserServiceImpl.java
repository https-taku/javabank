package com.cbs.javabank.service.impl;

import com.cbs.javabank.dto.*;
import com.cbs.javabank.entity.User;
import com.cbs.javabank.repository.UserRepo;
import com.cbs.javabank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        /**
         * creating a new user account
         * VALIDATION CHECKING IF USER ALREADY HAS ACCOUNT
         */
        if (userRepo.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()

                    .responsecode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responsemessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)

                    .build();


        }

        //balance enquiry, name Enquiry, credit , debit, transfer

        User newUser = User.builder()
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .othername(userRequest.getOthername())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateoforigin(userRequest.getStateoforigin())
                .accountnumber(AccountUtils.generateaccountnumber())
                .accountbalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phonenumber(userRequest.getPhonenumber())
                .alternativephonenumber(userRequest.getAlternativephonenumber())
                .status("ACTIVE")

                .build();

        User saveduser = userRepo.save(newUser);
        //send email alerty
        EmailDetails emailDetails = EmailDetails.builder()

                .recipient(saveduser.getEmail())
                .subject("ACCOUNT CREATION")
                .message("Congratulations Account Has Been created successfully" + "\n" +
                        "Below are your account details" + "\n" +
                        "Kindly keep it safe" + "\n" +
                        "ACCOUNT DETAILS" + "\n" +
                        "ACCOUNT NAME" + saveduser.getFirstname() + " " + saveduser.getLastname() +
                        " " + saveduser.getOthername() + "\n" +
                        "ACCOUNT NUMBER" + saveduser.getAccountnumber() + "\n" +
                        "ACCOUNT BALANCE" + saveduser.getAccountbalance() + "\n" +
                        "STATUS" + saveduser.getStatus() +

                        "Thank you for banking with us" + "\n" +
                        "Best Regards" + "\n" +
                        "java BANK")


                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankResponse.builder()

                .responsecode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responsemessage(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .accountinfo(AccountInfo.builder()

                        .accountbalance(saveduser.getAccountbalance())
                        .accountnumber(saveduser.getAccountnumber())
                        .accountname(saveduser.getFirstname() + " " + saveduser.getLastname() +
                                " " + saveduser.getOthername())

                        .build())

                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        //check if account number exists
        boolean isAccountExists = userRepo.existsByAccountnumber(request.getAccountnumber());
        if (!isAccountExists) {
            return BankResponse.builder()

                    .responsecode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responsemessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountinfo(null)

                    .build();
        }

        User founduser = userRepo.findByAccountnumber(request.getAccountnumber());

        return BankResponse.builder()

                .responsecode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responsemessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountinfo(AccountInfo.builder()

                        .accountbalance(founduser.getAccountbalance())
                        .accountnumber(request.getAccountnumber())
                        .accountname(founduser.getFirstname() + " "
                                + founduser.getLastname() +
                                " " + founduser.getOthername())

                        .build())


                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean isAccountExists = userRepo.existsByAccountnumber(request.getAccountnumber());
        if (!isAccountExists) {
            return AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE;

        }
        User founduser = userRepo.findByAccountnumber(request.getAccountnumber());
        return founduser.getFirstname() + " " + founduser.getLastname()
                + " " + founduser.getOthername();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        //checking if the account exists

        boolean isAccountExists = userRepo.existsByAccountnumber(request.getAccountnumber());
        if (!isAccountExists) {
            return BankResponse.builder()

                    .responsecode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responsemessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountinfo(null)

                    .build();


        }
        User userToCredit = userRepo.findByAccountnumber(request.getAccountnumber());
        userToCredit.setAccountbalance(userToCredit.getAccountbalance().add(request.getAmount()));
        User creditedUser = userRepo.save(userToCredit);


        return BankResponse.builder()

                .responsecode(AccountUtils.ACCOUNT_CREDITED_SUCCESSFULLY_CODE)
                .responsemessage(AccountUtils.ACCOUNT_CREDITED_SUCCESSFULLY_MESSAGE)
                .accountinfo(AccountInfo.builder()

                        .accountbalance(userToCredit.getAccountbalance())
                        .accountnumber(request.getAccountnumber())
                        .accountname(userToCredit.getFirstname() + " "
                                + userToCredit.getLastname() +
                                " " + userToCredit.getOthername())

                        .build())


                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        //check if account exists
        //check if money
        boolean isAccountExists = userRepo.existsByAccountnumber(request.getAccountnumber());
        if (!isAccountExists) {
            return BankResponse.builder()

                    .responsecode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responsemessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountinfo(null)

                    .build();
        }

        User userToDebit = userRepo.findByAccountnumber(request.getAccountnumber());
        int availableBalance = userToDebit.getAccountbalance().compareTo(request.getAmount());
        int debitAmount = request.getAmount().compareTo(BigDecimal.ZERO);
        if (debitAmount < 0 || availableBalance < debitAmount) {
            return BankResponse.builder()

                    .responsecode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responsemessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountinfo(null)

                    .build();
        } else {
            userToDebit.setAccountbalance(userToDebit.getAccountbalance().subtract(request.getAmount()));
            User debitedUser = userRepo.save(userToDebit);
            return BankResponse.builder()

                    .responsecode(AccountUtils.ACCOUNT_DEBITED_SUCCESSFULLY_CODE)
                    .responsemessage(AccountUtils.ACCOUNT_DEBITED_SUCCESSFULLY_MESSAGE)
                    .accountinfo(AccountInfo.builder()

                            .accountbalance(userToDebit.getAccountbalance())
                            .accountnumber(request.getAccountnumber())
                            .accountname(userToDebit.getFirstname() + " "
                                    + userToDebit.getLastname() +
                                    " " + userToDebit.getOthername())

                            .build())

                    .build();
        }


    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        //ge to debit
        //check if account exists
        //check if money is available
        //debit account
        //credit account
        boolean isAccountExists = userRepo.existsByAccountnumber(request.getSourceaccountnumber());
        boolean isDestinationAccountExists = userRepo.existsByAccountnumber(request.getDestinationaccountnumber());



        if(!isAccountExists || !isDestinationAccountExists){
            return BankResponse.builder()
                    .responsecode(AccountUtils.ACCOUNT_TRANSFER_FAILED_CODE)
                    .responsemessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountinfo(null)
                    .build();
        }
        User sourceAccountuser =  userRepo.findByAccountnumber(request.getSourceaccountnumber());
        User destinationAccountuser =  userRepo.findByAccountnumber(request.getDestinationaccountnumber());
        if (request.getAmount()
                .compareTo(String.valueOf(sourceAccountuser.getAccountbalance())) <0) {
            return BankResponse.builder()
                    .responsecode(AccountUtils.ACCOUNT_TRANSFER_FAILED_CODE)
                    .responsemessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountinfo(null)
                    .build();
        }


        sourceAccountuser.setAccountbalance(sourceAccountuser.getAccountbalance().subtract(new BigDecimal(request.getAmount())));
        String sourceUsername = sourceAccountuser.getFirstname() + " " +
                sourceAccountuser.getLastname();

        String recipientUsername = destinationAccountuser.getFirstname() + " " +
                destinationAccountuser.getLastname();
        userRepo.save(sourceAccountuser);
        EmailDetails DebitAlert = EmailDetails.builder()
                .recipient(sourceAccountuser.getEmail())
                .subject(" SUCCESSFUL DEBIT ALERT - you sent money")
                .message("You have successfully debited your account with the sum of " + request.getAmount() + " on " + sourceAccountuser.getAccountnumber())
                .build();

        emailService.sendEmailAlert(DebitAlert);



        User destinstionaccountuser = userRepo.findByAccountnumber(request.getDestinationaccountnumber());
        destinstionaccountuser.setAccountbalance(destinstionaccountuser.getAccountbalance().add(new BigDecimal(request.getAmount())));

        EmailDetails CreditAlert = EmailDetails.builder()
                .recipient(sourceAccountuser.getEmail())
                .subject("CREDIT ALERT - you have received money")
                .message("You have credited your account with the sum of " + request.getAmount() + " on " + sourceAccountuser.getAccountnumber())
                .build();

        emailService.sendEmailAlert(CreditAlert);
}
}
