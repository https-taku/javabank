package com.cbs.javabank.repository;

import com.cbs.javabank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean findByEmail(String email);

    Boolean existsByAccountnumber(String accountnumber);


   User findByAccountnumber(String accountnumber);

}
