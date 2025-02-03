package org.co.nikolaeva.aa.bot;

import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

import static jdk.javadoc.internal.tool.Main.execute;


public class BotApp extends TelegramLongPollingBot {


    private String botToken;
    private String userNameBot;
    private String providerToken;

    public BotApp() {
        loadProperties();
    }

    // загрузка данных для подключения
    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find bot.properties");
                return;
            }
            properties.load(input);
            botToken = properties.getProperty("telegram.bot.token");
            userNameBot = properties.getProperty("telegram.bot.username");
            providerToken = properties.getProperty("telegram.bot.provider.token");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //опбработка команд бота
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            String userText = message.getText();
            SendMessage response = new SendMessage();
            SendInvoice sendInvoice = new SendInvoice();
            response.setChatId(chatId);
            switch (userText) {
                case "/start":
                    response.setText("Привет! Я бот. \n /start - Начать работу \n " +
                            "/help - Получить справку" + "\n /bill - Оплатить счет");
                    break;
                case "/help":
                    response.setText("Я могу выполнять команду /bill");
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
        return userNameBot;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    public void sendInvoice(String chatId) {

        // Создание счета
        SendInvoice invoice = new SendInvoice();
        invoice.setChatId(chatId); // Установка идентфикатора чата, возвращается после команды /start
        invoice.setTitle("Название товара"); // Название инвойса
        invoice.setDescription("Описание товара"); // Описание инвойса
        invoice.setProviderToken(providerToken); // Токен провайдера платежей
        invoice.setCurrency("USD"); // Валюта
        invoice.setPrices(Collections.singletonList(new LabeledPrice("Название товара", 100))); // Цена в копейках (5000 копеек = 50 USD)
        invoice.getPayload();
        invoice.setPayload("https://api.telegram.org/bot" + botToken + "/sendInvoice");

        try {
            execute(invoice); // Отправка инвойса
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
