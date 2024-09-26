package com.cbs.javabank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "1";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created";

    public static final String ACCOUNT_CREATION_SUCCESS = "Account Created Successfully";

    public static final String ACCOUNT_CREATION_CODE = "2";

    public static final String ACCOUNT_NOT_EXISTS_CODE = "3";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "Account does not exist";

    public static final String ACCOUNT_FOUND_CODE = "4";
    public static final String ACCOUNT_FOUND_MESSAGE = "Account found";

    public static final String ACCOUNT_CREDITED_SUCCESSFULLY_MESSAGE = "Account Credited Successfully";

    public static final String ACCOUNT_CREDITED_SUCCESSFULLY_CODE = "5";

    public static final String ACCOUNT_DEBITED_SUCCESSFULLY_MESSAGE = "Account Debited Successfully";

    public static final String ACCOUNT_DEBITED_SUCCESSFULLY_CODE = "6";

    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";

    public static final String INSUFFICIENT_BALANCE_CODE = "7";






    public static String generateaccountnumber(){
        /**
         * begin with year - random 6 digits -
         * 2024 - 999999
         */

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        //generate random num between min and max

        int randnumber = (int)Math.floor(Math.random() * ((max - min +1) + min));

        // converting year and random to strings , then concatenate

        String year = String.valueOf(currentYear);

        String randomNumber = String.valueOf(randnumber);

        StringBuilder accountnumber = new StringBuilder();

       return accountnumber.append(year).append(randnumber).toString();

    }
}
