package com.srp.java_telegram_bots.processors;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.srp.java_telegram_bots.config.TelegramBotConfiguration;
import com.srp.java_telegram_bots.domains.UserDomain;
import com.srp.java_telegram_bots.steps.RegistrationState;
import com.srp.java_telegram_bots.utils.AnswerCallbackQueryFactory;
import com.srp.java_telegram_bots.utils.SendMessageFactory;

import static com.srp.java_telegram_bots.config.ThreadSafeBeansContainer.userService;

public class RegisterUserCallbackProcessor implements Processor<RegistrationState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();

    @Override
    public void process( Update update, RegistrationState state ) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        int messageId = message.messageId();
        Long chatID = callbackQuery.message().chat().id();
        String data = callbackQuery.data();
        if ( state.equals(RegistrationState.PASSWORD) ) {
            if ( data.equals("done") ) {
                if ( state.getPassword().length() == 4 ) {
                    UserDomain domain = UserDomain.builder()
                            .chatID(chatID.toString())
                            .username(update.callbackQuery().from().username())
                            .password(state.getPassword().toString())
                            .firstName(update.callbackQuery().from().firstName())
                            .build();
                    userService.get().create(domain);
                    bot.execute(new SendMessage(chatID,"User succesfully created"));
                } else
                    bot.execute(AnswerCallbackQueryFactory.answerCallbackQuery(callbackQuery.id(), "Password must contain four numbers"));
            } else if ( data.equals("d") ) {
                StringBuilder password = state.getPassword();
                int length = password.length();
                if ( length == 0 )
                    bot.execute(AnswerCallbackQueryFactory.answerCallbackQuery(callbackQuery.id(), "Password field is empty"));
                else {
                    length = length - 1;
                    String messageText = "Enter Password Please\n\n" + "*️⃣".repeat(length) + " _ ".repeat(4 - length);
                    bot.execute(SendMessageFactory.getEditMessageTextForPassword(chatID, messageId, messageText));
                    state.setPassword(password.deleteCharAt(length));
                }
            } else {
                StringBuilder password = state.getPassword();
                password.append(data);
                int length = password.length();
                if ( length <= 4 ) {
                    String messageText = "Enter Password Please\n\n" + "*️⃣".repeat(length) + " _ ".repeat(4 - length);
                    bot.execute(SendMessageFactory.getEditMessageTextForPassword(chatID, messageId, messageText));
                    if ( length == 4 )
                        state.setPassword(password);
                } else
                    bot.execute(AnswerCallbackQueryFactory.answerCallbackQuery(callbackQuery.id(), "Password field is full!\nPlease press ✅ button"));

            }
        }
    }

}
