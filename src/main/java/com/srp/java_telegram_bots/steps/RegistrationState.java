package com.srp.java_telegram_bots.steps;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistrationState implements State {
    USERNAME(null, new StringBuilder()),
    PASSWORD(null, new StringBuilder());

    private String username;
    private StringBuilder password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(StringBuilder password) {
        this.password = password;
    }

}
