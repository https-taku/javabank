package com.cbs.javabank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String firstname;
    private String lastname;
    private String othername;
    private String gender;
    private String address;
    private String stateoforigin;

    private String email;
    private String phonenumber;
    private String alternativephonenumber;


}
