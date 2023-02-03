package com.srp.java_telegram_bots.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.srp.java_telegram_bots.config.TelegramBotConfiguration;
import com.srp.java_telegram_bots.steps.DefaultState;
import com.srp.java_telegram_bots.steps.RegistrationState;
import com.srp.java_telegram_bots.steps.State;
import com.srp.java_telegram_bots.utils.InlineKeyboardMarkupFactory;

import static com.srp.java_telegram_bots.config.ThreadSafeBeansContainer.userState;


/*@Slf4j*/
public class MessageHandler implements Handler {
    private final TelegramBot bot = TelegramBotConfiguration.get();

    @Override
    public void handle(Update update) {
        Message message = update.message();
        Chat chat = message.chat();
        Long chatID = chat.id();
        String text = message.text();
        State step = userState.get(chatID);
        if ( step == null )
            startRegister(chatID);
        else {
            if ( step instanceof RegistrationState regState ) {
                if ( regState.equals(RegistrationState.USERNAME) ) {
                    StringBuilder password = regState.getPassword();
                    int length = password.length();
                    String messageText = "Enter Password Please\n\n" + "*️⃣".repeat(length) + " _ ".repeat(4 - length);
                    SendMessage sendMessage = new SendMessage(chatID, messageText);
                    sendMessage.replyMarkup(InlineKeyboardMarkupFactory.enterPasswordKeyboard());
                    bot.execute(sendMessage);
                    userState.put(chatID, RegistrationState.PASSWORD);
                } else if ( regState.equals(RegistrationState.PASSWORD) ) {
                    System.out.println("Password : " + message.text());
                    userState.put(chatID, DefaultState.DELETE);
                    SendMessage sendMessage = new SendMessage(chatID, "Successfully Registered");
                    bot.execute(sendMessage);
                }
            } else if ( step instanceof DefaultState defaultState ) {
                if ( defaultState.equals(DefaultState.DELETE) ) {
                    bot.execute(new DeleteMessage(chatID, message.messageId()));
                }
            }
        }
    }

    private void startRegister(Long chatID) {
        userState.put(chatID, RegistrationState.USERNAME);
        SendMessage sendMessage = new SendMessage(chatID, "Welcome\nPlease Register\nUsername please");
        sendMessage.replyMarkup(new ForceReply());
        bot.execute(sendMessage);
    }
}
