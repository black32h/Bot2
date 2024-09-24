package org.example;

public class KreditAgrikol {

    // Основний метод для розрахунку умов автокредиту в "Креді Агріколь"
    public static String calculate(double carPrice, double downPaymentPercentage, int loanTerm) {
        // Розраховуємо суму першого внеску на основі відсотка
        double downPayment = carPrice * downPaymentPercentage / 100;
        // Визначаємо суму кредиту (ціна автомобіля мінус перший внесок)
        double loanAmount = carPrice - downPayment;

        // Перевірка: перший внесок не повинен бути більшим або рівним вартості автомобіля
        if (loanAmount <= 0) {
            return "Перший внесок не може бути більшим або дорівнювати вартості автомобіля.";
        }

        // Встановлюємо річну процентну ставку (13.51%)
        double interestRate = 13.51 / 100;
        // Перетворюємо річну ставку на місячну
        double monthlyRate = interestRate / 12;
        // Розраховуємо щомісячний платіж за формулою ануїтету
        double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loanTerm));

        // Розраховуємо витрати:
        double kascoAnnual = 0.06 * carPrice; // КАСКО — 6% від вартості автомобіля щороку
        double lifeInsurance = 21373; // Фіксована сума на страхування життя
        double registrationCost = 65250; // Вартість першої реєстрації автомобіля
        double notaryServices = 5000; // Вартість послуг нотаріуса

        // Загальні витрати на КАСКО за весь термін кредиту
        double totalKasco = kascoAnnual * (loanTerm / 12.0);
        // Щомісячний платіж по КАСКО
        double monthlyKasco = kascoAnnual / 12;

        // Загальна сума всіх виплат, включаючи кредит, КАСКО, страхування життя, реєстрацію та нотаріуса
        double totalPayment = (monthlyPayment * loanTerm) + totalKasco + lifeInsurance + registrationCost + notaryServices;

        // Формуємо результат з усіма розрахунками
        return String.format("Креді Агріколь:\n" +
                        "Сума кредиту: %.2f грн\n" +
                        "Щомісячний платіж: %.2f грн\n" +
                        "Щомісячний платіж по КАСКО: %.2f грн\n" +
                        "Річна вартість КАСКО: %.2f грн\n" +
                        "Загальна вартість КАСКО за весь термін: %.2f грн\n" +
                        "Страхування життя: %.2f грн\n" +
                        "Вартість першої реєстрації: %.2f грн\n" +
                        "Послуги нотаріуса: %.2f грн\n" +
                        "Одноразова комісія: 0.00 грн\n" +
                        "Загальна сума виплат: %.2f грн\n" +
                        "Примітка: КАСКО становить 6%% від вартості автомобіля.\n",
                loanAmount, monthlyPayment, monthlyKasco, kascoAnnual, totalKasco, lifeInsurance, registrationCost, notaryServices, totalPayment);
    }
}
