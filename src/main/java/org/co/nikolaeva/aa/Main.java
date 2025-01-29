package org.co.nikolaeva.aa;

import org.co.nikolaeva.aa.bot.BotApp;
import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.bots.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        try {
            TelegramBotsApi startingBot = new TelegramBotsApi(DefaultBotSession.class);

            startingBot.registerBot(new BotApp());
            System.out.println("Start Bot!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}