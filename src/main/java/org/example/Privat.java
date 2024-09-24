package org.example;

public class Privat {

    // Основний метод для розрахунку умов автокредиту в ПриватБанку
    public static String calculate(double carPrice, double downPaymentPercentage, int loanTerm) {
        // Розраховуємо суму першого внеску на основі відсотка
        double downPayment = carPrice * downPaymentPercentage / 100;
        // Визначаємо суму кредиту (ціна автомобіля мінус перший внесок)
        double loanAmount = carPrice - downPayment;

        // Перевірка: перший внесок не повинен бути більшим або рівним вартості автомобіля
        if (loanAmount <= 0) {
            return "Перший внесок не може бути більшим або дорівнювати вартості автомобіля.";
        }

        // Отримуємо процентну ставку для першого періоду
        double interestRateFirstPeriod = getInterestRateFirstPeriod(downPaymentPercentage);
        // Отримуємо процентну ставку після першого періоду
        double interestRateAfterFirstPeriod = getInterestRateAfterFirstPeriod(downPaymentPercentage);

        // Логіка розрахунку для першого періоду (до 24 місяців)
        int firstPeriod = Math.min(loanTerm, 24); // Максимум 24 місяці
        double monthlyPaymentFirstPeriod = calculateMonthlyPayment(loanAmount, interestRateFirstPeriod, firstPeriod);
        double totalPaymentFirstPeriod = monthlyPaymentFirstPeriod * firstPeriod;

        // Остаток суми кредиту після першого періоду
        double remainingLoanAmount = loanAmount;
        // Залишковий термін після першого періоду
        int remainingTerm = loanTerm - firstPeriod;

        // Логіка розрахунку для залишкового періоду
        double monthlyPaymentAfterFirstPeriod = 0;
        if (remainingTerm > 0) {
            monthlyPaymentAfterFirstPeriod = calculateMonthlyPayment(remainingLoanAmount, interestRateAfterFirstPeriod, remainingTerm);
        }

        // Розраховуємо витрати на КАСКО (6.99% від вартості авто щорічно)
        double kascoAnnual = 0.0699 * carPrice;
        // Загальні витрати на КАСКО за весь термін кредиту
        double totalKasco = kascoAnnual * (loanTerm / 12.0);
        // Щомісячний платіж по КАСКО
        double monthlyKasco = kascoAnnual / 12;
        // Одноразова комісія (0%)
        double commission = 0;

        // Загальна сума всіх виплат за кредитом
        double totalPayment = totalPaymentFirstPeriod + (monthlyPaymentAfterFirstPeriod * Math.max(remainingTerm, 0)) + totalKasco + commission;

        // Формуємо результат з усіма розрахунками
        return String.format("Приватбанк:\n" +
                        "Сума кредиту: %.2f грн\n" +
                        "Щомісячний платіж: %.2f грн (перші %d місяців)\n" +
                        "Щомісячний платіж після %d місяців: %.2f грн\n" +
                        "Щомісячний платіж по КАСКО: %.2f грн\n" +
                        "Річна вартість КАСКО: %.2f грн\n" +
                        "Загальна вартість КАСКО за весь термін: %.2f грн\n" +
                        "Одноразова комісія: %.2f грн\n" +
                        "Загальна сума виплат: %.2f грн\n" +
                        "Примітка: КАСКО становить 6.99%% від вартості автомобіля.\n",
                loanAmount, monthlyPaymentFirstPeriod, firstPeriod, firstPeriod, monthlyPaymentAfterFirstPeriod,
                monthlyKasco, kascoAnnual, totalKasco, commission, totalPayment);
    }

    // Метод для отримання процентної ставки для першого періоду на основі першого внеску
    private static double getInterestRateFirstPeriod(double downPaymentPercentage) {
        if (downPaymentPercentage == 20) return 6.5;
        if (downPaymentPercentage == 30) return 4.9;
        if (downPaymentPercentage == 40) return 2.9;
        if (downPaymentPercentage == 50) return 0.01;
        if (downPaymentPercentage == 60) return 0.01;
        return 0; // Якщо внесок не підходить
    }

    // Метод для отримання процентної ставки після першого періоду
    private static double getInterestRateAfterFirstPeriod(double downPaymentPercentage) {
        return 21.9; // Фіксована ставка після першого періоду
    }

    // Метод для розрахунку щомісячного платежу на основі суми кредиту, процентної ставки та терміну
    private static double calculateMonthlyPayment(double loanAmount, double interestRate, int months) {
        if (months <= 0) return 0; // Перевірка на кількість місяців
        double monthlyRate = interestRate / 100 / 12; // Місячна ставка
        // Формула ануїтету для розрахунку щомісячного платежу
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }
}
