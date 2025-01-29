package org.co.nikolaeva.aa.bot;

import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.Collections;

import static jdk.javadoc.internal.tool.Main.execute;

public class BotApp extends TelegramLongPollingBot {


  /*
  ConfigBot configBot = new ConfigBot("config.properties");
  String botToken = configBot.getProperty("telegram.bot.token");

    TelegramBot bot = new TelegramBot(botToken);
    */


    private static final String BOT_TOKEN = "7792446993:AAH6WBI9DXpjA5OvfwT65Yzvt7LrsBfpWYI";
    private static final String BOT_USERNAME = "SHOP BOT";


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            String userText = message.getText();
            SendMessage response = new SendMessage();
            response.setChatId(chatId);
            switch (userText) {
                case "/start":
                    response.setText("Привет! Я бот. \n /start - Начать работу \n " +
                            "/help - Получить справку" + "\n /bill - Оплатить счет");
                    break;
                case "/help":
                    response.setText("Я могу выполнять эту команду");
                    break;
                case "/bill":
                    sendInvoice(chatId);
                    break;
                default:
                    response.setText("Неизвестная команда. Попробуйте /start или /help.");
            }
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


    public void sendInvoice(String chatId) {
        // Создание счета
        SendInvoice invoice = new SendInvoice();
        invoice.setChatId(chatId); // Установка идентфикатора чата
        invoice.setTitle("Название товара"); // Название инвойса
        invoice.setDescription("Описание товара"); // Описание инвойса
        // invoice.setProviderToken("YOUR_PROVIDER_TOKEN"); // Токен провайдера платежей (например, Stripe)
        // invoice.setProviderToken("7010944257:TEST:f3d034f7-9c19-4ffa-84c2-d8df24b13e61"); // Токен провайдера платежей
        invoice.setProviderToken("Stripe TEST MODE"); // Токен провайдера платежей
        invoice.setStartParameter("payment"); // Начальный параметр платежа
        invoice.setCurrency("RUB"); // Валюта
        invoice.setPrices(Collections.singletonList(new LabeledPrice("Название товара", 5000))); // Цена в копейках (5000 копеек = 50 USD)

        try {
            execute(invoice); // Отправка инвойса
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
