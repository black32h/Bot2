package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {

    // Зберігаємо стан користувачів, а також дані про ціни авто, перший внесок, термін кредиту та вибір банку.
    private Map<Long, String> userStates = new HashMap<>(); // Стан користувача
    private Map<Long, Double> carPrices = new HashMap<>(); // Ціна автомобіля
    private Map<Long, Double> firstPayments = new HashMap<>(); // Перший внесок
    private Map<Long, Integer> loanTerms = new HashMap<>(); // Термін кредиту
    private Map<Long, String> selectedBanks = new HashMap<>(); // Вибраний банк

    // Метод, який обробляє оновлення від Telegram
    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();

        // Перевіряємо, чи це текстове повідомлення
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            handleMessage(chatId, message); // Обробка текстових повідомлень
        }
        // Перевіряємо, чи це callback-запит (наприклад, натискання кнопки)
        else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            handleCallback(chatId, callbackData); // Обробка callback-запитів
        }
    }

    // Метод для обробки текстових повідомлень
    private void handleMessage(long chatId, String message) {
        switch (userStates.getOrDefault(chatId, "")) {
            case "WAITING_FOR_CAR_PRICE":
                handleCarPriceInput(chatId, message); // Обробка введення ціни автомобіля
                break;
            case "WAITING_FOR_FIRST_PAYMENT":
                handleFirstPaymentInput(chatId, message); // Обробка введення першого внеску
                break;
            case "WAITING_FOR_LOAN_TERM":
                handleLoanTermInput(chatId, message); // Обробка введення терміну кредиту
                break;
            default:
                if (message.equals("/start")) {
                    sendCarOptions(chatId); // Виводимо меню вибору автомобіля
                } else {
                    sendMessage(chatId, "Невідома команда. Будь ласка, використовуйте /start для початку.");
                }
                break;
        }
    }

    // Метод для обробки callback-запитів (натискання кнопок)
    private void handleCallback(long chatId, String callbackData) {
        switch (callbackData) {
            case "Тойота":
            case "Мазда":
            case "MG":
                userStates.put(chatId, "WAITING_FOR_CAR_PRICE");
                sendMessage(chatId, "Введіть вартість автомобіля:");
                break;
            case "Ощадбанк":
            case "Приватбанк":
            case "Кредит Агриколь":
                selectedBanks.put(chatId, callbackData);
                userStates.put(chatId, "WAITING_FOR_LOAN_TERM");
                sendMessage(chatId, "Введіть термін кредиту в місяцях:");
                break;
            default:
                sendMessage(chatId, "Невідома команда.");
                break;
        }
    }

    // Метод обробки введення терміну кредиту
    private void handleLoanTermInput(long chatId, String message) {
        try {
            int loanTerm = Integer.parseInt(message);
            loanTerms.put(chatId, loanTerm);

            double carPrice = carPrices.get(chatId);
            double firstPayment = firstPayments.get(chatId);
            String bank = selectedBanks.get(chatId);

            if (bank == null) {
                sendMessage(chatId, "Будь ласка, виберіть банк перед введенням терміну кредиту.");
                return;
            }

            sendMessage(chatId, String.format("Введені дані: ціна авто = %.2f, перший внесок = %.2f, банк = %s, термін = %d", carPrice, firstPayment, bank, loanTerm));

            double downPaymentPercentage = (firstPayment / carPrice) * 100;
            String loanResults = calculateLoanConditions(carPrice, downPaymentPercentage, bank, loanTerm);
            sendMessage(chatId, loanResults);

            resetUserState(chatId); // Скидаємо стан користувача після введення даних
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Будь ласка, введіть коректний термін кредиту в місяцях.");
        } catch (Exception e) {
            sendMessage(chatId, "Сталася помилка: " + e.getMessage());
        }
    }

    // Скидаємо стан користувача після завершення взаємодії
    private void resetUserState(long chatId) {
        userStates.remove(chatId);
        carPrices.remove(chatId);
        firstPayments.remove(chatId);
        loanTerms.remove(chatId);
        selectedBanks.remove(chatId);
    }

    // Розрахунок умов кредиту в залежності від вибраного банку
    private String calculateLoanConditions(double carPrice, double downPaymentPercentage, String bank, int loanTerm) {
        switch (bank) {
            case "Ощадбанк":
                return Oschad.calculate(carPrice, downPaymentPercentage, loanTerm);
            case "Приватбанк":
                return Privat.calculate(carPrice, downPaymentPercentage, loanTerm);
            case "Кредит Агриколь":
                return KreditAgrikol.calculate(carPrice, downPaymentPercentage, loanTerm);
            default:
                return "Невідомий банк.";
        }
    }

    // Метод для обробки введення ціни автомобіля
    private void handleCarPriceInput(long chatId, String message) {
        try {
            double price = Double.parseDouble(message);
            carPrices.put(chatId, price);
            userStates.put(chatId, "WAITING_FOR_FIRST_PAYMENT");
            sendMessage(chatId, "Введіть перший внесок:");
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Будь ласка, введіть коректну вартість автомобіля.");
        }
    }

    // Метод для обробки введення першого внеску
    private void handleFirstPaymentInput(long chatId, String message) {
        try {
            double firstPayment = Double.parseDouble(message);
            firstPayments.put(chatId, firstPayment);
            userStates.put(chatId, "WAITING_FOR_BANK_SELECTION");
            sendLoanOptions(chatId); // Виводимо меню вибору банку для кредитування
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Будь ласка, введіть коректну суму першого внеску.");
        }
    }

    // Виводимо меню вибору марки автомобіля
    private void sendCarOptions(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Виберіть марку автомобіля:");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder().text("Тойота").callbackData("Тойота").build());
        row1.add(InlineKeyboardButton.builder().text("Мазда").callbackData("Мазда").build());

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text("MG").callbackData("MG").build());

        buttons.add(row1);
        buttons.add(row2);
        keyboardMarkup.setKeyboard(buttons);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message); // Відправка повідомлення з меню
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Виводимо меню вибору банку для кредиту
    private void sendLoanOptions(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Виберіть банк для кредитування:");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder().text("Ощадбанк").callbackData("Ощадбанк").build());
        row1.add(InlineKeyboardButton.builder().text("Приватбанк").callbackData("Приватбанк").build());

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text("Кредит Агриколь").callbackData("Кредит Агриколь").build());

        buttons.add(row1);
        buttons.add(row2);
        keyboardMarkup.setKeyboard(buttons);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Відправляємо просте текстове повідомлення користувачеві
    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Повертаємо ім'я бота
    @Override
    public String getBotUsername() {
        return "MazdaCredit_Bot"; // Ім'я бота
    }

    // Повертаємо токен бота
    @Override
    public String getBotToken() {
        return "7176542474:AAFJbodxYH-70q2zXPFCIs2SsVUZGhFVj8Y"; // Токен бота
    }
}
