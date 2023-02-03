package com.srp.java_telegram_bots.utils;

import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;

public class SendMessageFactory {
    public static EditMessageText getEditMessageTextForPassword(Object chatID, int messageID, String messageText) {
        EditMessageText editMessageText = new EditMessageText(chatID, messageID, messageText);
        editMessageText.replyMarkup(InlineKeyboardMarkupFactory.enterPasswordKeyboard());
        return editMessageText;
    }
}
