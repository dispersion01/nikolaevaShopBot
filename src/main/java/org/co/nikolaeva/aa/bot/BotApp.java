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
                    sendInvoice("chatId", "title", "description", "test", "RUB", "500",
                            "11111");
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


    public void sendInvoice(String chatId, String title,
                            String description, String payload,
                            String currency, String prices, String provider_token) {
      /*  try {
            URL urlPayment = new URL(TELEGRAM_API_URL);

            HttpURLConnection connect = (HttpURLConnection) urlPayment.openConnection();
            String requestMethod = connect.getRequestMethod();
            connect.setRequestProperty("Content-Type", "application/json; utf-8");
            connect.setRequestProperty("Accept", "application/json");
            connect.setDoOutput(true);
            String jsonInputString = String.format(
                    "{\"chat_id\":\"%s\", " +
                            "\"title\":\"%s\", " +
                            "\"description\":\"%s\", " +
                            "\"payload\":\"%s\", " +
                            "" +
                            "\"provider_token\":\"%s\"," +
                            " \"currency\":\"%s\", " +
                            "\"prices\":%s}",
                    chatId, title, description, payload, provider_token, currency, prices);
            try (OutputStream os = connect.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } */

        // Создание счета
        SendInvoice invoice = new SendInvoice();
        invoice.setChatId(chatId); // Установка идентфикатора чата
        invoice.setTitle("Название товара"); // Название инвойса
        invoice.setDescription("Описание товара"); // Описание инвойса
        invoice.setProviderToken(providerToken); // Токен провайдера платежей (например, Stripe)
        //invoice.setProviderToken("YOUR_PROVIDER_TOKEN"); // Токен провайдера платежей (например, Stripe)
        // invoice.setProviderToken("7010944257:TEST:f3d034f7-9c19-4ffa-84c2-d8df24b13e61"); // Токен провайдера платежей
        invoice.setStartParameter("payment"); // Начальный параметр платежа
        invoice.setCurrency("RUB"); // Валюта
        invoice.setPrices(Collections.singletonList(new LabeledPrice("Название товара", 5000))); // Цена в копейках (5000 копеек = 50 USD)
        invoice.getPayload();
        invoice.setPayload("https://api.telegram.org/bot7792446993:AAH6WBI9DXpjA5OvfwT65Yzvt7LrsBfpWYI/sendInvoice");

        try {
            execute(invoice); // Отправка инвойса
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
