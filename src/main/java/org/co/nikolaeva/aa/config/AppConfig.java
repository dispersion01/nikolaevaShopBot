package org.co.nikolaeva.aa.config;

import org.co.nikolaeva.aa.bot.BotApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.generics.TelegramBot;

//добавить токен из .properties
public class AppConfig {
    @Value("${telegram.bot.token}")
    private String token;

/*    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);

        return bot;
    }
*/

}
