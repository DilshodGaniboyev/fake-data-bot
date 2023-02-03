package com.srp.java_telegram_bots.services;

import com.srp.java_telegram_bots.daos.UserDao;
import com.srp.java_telegram_bots.domains.UserDomain;
import com.srp.java_telegram_bots.utils.PasswordEncoderUtils;
import lombok.NonNull;

import java.sql.SQLException;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void create(@NonNull UserDomain domain) {

        domain.setPassword(PasswordEncoderUtils.encode(domain.getPassword()));
        domain.setChatID(String.valueOf(domain.getChatID()));
        try {
            userDao.save(domain);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
