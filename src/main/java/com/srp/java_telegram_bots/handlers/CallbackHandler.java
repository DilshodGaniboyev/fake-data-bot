package com.srp.java_telegram_bots.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.srp.java_telegram_bots.config.TelegramBotConfiguration;
import com.srp.java_telegram_bots.steps.RegistrationState;
import com.srp.java_telegram_bots.steps.State;

import static com.srp.java_telegram_bots.config.ThreadSafeBeansContainer.registerUserCallbackProcessor;
import static com.srp.java_telegram_bots.config.ThreadSafeBeansContainer.userState;

public class CallbackHandler implements Handler {
    private final TelegramBot bot = TelegramBotConfiguration.get();


    @Override
    public void handle( Update update ) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatID = callbackQuery.message().chat().id();
        State state = userState.get(chatID);
        if ( state instanceof RegistrationState registrationState )
            registerUserCallbackProcessor.get().process(update, registrationState);

    }
}
