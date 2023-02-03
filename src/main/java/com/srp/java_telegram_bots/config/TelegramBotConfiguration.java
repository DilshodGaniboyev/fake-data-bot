package com.srp.java_telegram_bots.config;

import com.pengrad.telegrambot.TelegramBot;

import java.util.ResourceBundle;

public class TelegramBotConfiguration {
    private static ResourceBundle setting = ResourceBundle.getBundle("settings");
    private static ThreadLocal<TelegramBot> telegramBotThreadLocal = ThreadLocal.withInitial(() -> new TelegramBot(setting.getString("bot.token")));

    public static TelegramBot get() {
        return telegramBotThreadLocal.get();
    }
}
