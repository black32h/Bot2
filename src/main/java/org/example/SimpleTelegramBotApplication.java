package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication // Анотація, що позначає цей клас як Spring Boot додаток
public class SimpleTelegramBotApplication {

    public static void main(String[] args) {
        // Запускаємо Spring Boot додаток
        SpringApplication.run(SimpleTelegramBotApplication.class, args);

        // Створюємо екземпляр TelegramBot
        TelegramBot telegramBot = new TelegramBot(); //  клас TelegramBot

        try {
            // Ініціалізуємо TelegramBotsApi
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Реєструємо  бота
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            // Обробка виключення, якщо сталася помилка при реєстрації бота
            e.printStackTrace();
        }
    }
}
