package com.cbs.javabank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String othername;
    private String gender;
    private String address;
    private String stateoforigin;
    private String accountnumber;
    private BigDecimal accountbalance;
    private String email;
    private String phonenumber;
    private String alternativephonenumber;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdat;
    @UpdateTimestamp
    private LocalDateTime modifiedat;

}
