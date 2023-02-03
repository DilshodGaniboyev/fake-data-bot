package com.srp.java_telegram_bots.daos;

import com.srp.java_telegram_bots.domains.UserDomain;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserDao extends Dao {

    public void save(@NonNull UserDomain domain) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement("insert into users(chat_id,username, password, firstname) values(?,?,?,?);");
        pst.setString(1, domain.getChatID());
        pst.setString(2, domain.getUsername());
        pst.setString(3, domain.getPassword());
        pst.setString(4, domain.getFirstName());
        pst.execute();
    }
}
