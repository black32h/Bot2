package org.example;

public class Oschad {

    // Основний метод для розрахунку умов автокредиту
    public static String calculate(double carPrice, double downPaymentPercentage, int loanTerm) {
        // Розраховуємо суму першого внеску на основі відсотка
        double downPayment = carPrice * downPaymentPercentage / 100;
        // Визначаємо суму кредиту (ціна автомобіля мінус перший внесок)
        double loanAmount = carPrice - downPayment;

        // Перевірка: перший внесок не повинен бути більшим або рівним ціні автомобіля
        if (loanAmount <= 0) {
            return "Перший внесок не може бути більше або дорівнювати вартості автомобіля.";
        }

        // Отримуємо річну процентну ставку на основі терміну кредиту та відсотку першого внеску
        double interestRate = getInterestRate(loanTerm, downPaymentPercentage);
        // Перетворюємо річну ставку в місячну
        double monthlyRate = interestRate / 100 / 12;
        // Розраховуємо щомісячний платіж по кредиту за ануїтетною схемою
        double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loanTerm));

        // Розраховуємо витрати на КАСКО (7% від вартості авто щорічно)
        double kascoAnnual = 0.07 * carPrice;
        // Отримуємо одноразову комісію за обслуговування кредиту
        double commissionRate = getCommissionRate(loanTerm);
        // Розраховуємо одноразову комісію
        double commission = loanAmount * commissionRate;

        // Фіксована вартість першої реєстрації автомобіля
        double registrationCost = 65250;
        // Комісія за видачу кредиту (1.5% від суми кредиту)
        double loanIssuanceFeeRate = 0.015;
        // Розраховуємо комісію за видачу кредиту
        double loanIssuanceFee = loanAmount * loanIssuanceFeeRate;

        // Загальні витрати на КАСКО за весь термін кредиту
        double totalKasco = kascoAnnual * (loanTerm / 12.0);
        // Щомісячний платіж по КАСКО
        double monthlyKasco = kascoAnnual / 12;
        // Загальна сума всіх виплат за кредитом
        double totalPayment = (monthlyPayment * loanTerm) + totalKasco + commission + registrationCost + loanIssuanceFee;

        // Формуємо результат з усіма розрахунками
        return String.format("Ощадбанк:\n" +
                        "Сума кредиту: %.2f грн\n" +
                        "Щомісячний платіж: %.2f грн\n" +
                        "Щомісячний платіж по КАСКО: %.2f грн\n" +
                        "Річна вартість КАСКО: %.2f грн\n" +
                        "Загальна вартість КАСКО за весь термін: %.2f грн\n" +
                        "Одноразова комісія: %.2f грн\n" +
                        "Вартість першої реєстрації: %.2f грн\n" +
                        "Комісія за видачу кредиту (1.5%% від суми кредиту): %.2f грн\n" +
                        "Загальна сума виплат: %.2f грн\n" +
                        "Примітка: КАСКО становить 7%% від вартості автомобіля.\n",
                loanAmount, monthlyPayment, monthlyKasco, kascoAnnual, totalKasco, commission, registrationCost, loanIssuanceFee, totalPayment);
    }

    // Метод для визначення одноразової комісії на основі терміну кредиту
    private static double getCommissionRate(int loanTerm) {
        if (loanTerm <= 12) {
            return 0.0199; // 1.99% для терміну до 12 місяців
        } else if (loanTerm <= 24) {
            return 0.035; // 3.5% для терміну до 24 місяців
        } else if (loanTerm <= 36) {
            return 0.035; // 3.5% для терміну до 36 місяців
        } else if (loanTerm <= 60) {
            return 0.0; // 0% для терміну до 60 місяців
        } else if (loanTerm <= 84) {
            return 0.0; // 0% для терміну до 84 місяців
        }
        return 0; // Якщо термін не підходить, повертаємо 0
    }

    // Метод для визначення процентної ставки на основі терміну кредиту і першого внеску
    private static double getInterestRate(int loanTerm, double downPaymentPercentage) {
        if (loanTerm <= 12) {
            return getRateForTerm12Months(downPaymentPercentage);
        } else if (loanTerm <= 24) {
            return getRateForTerm24Months(downPaymentPercentage);
        } else if (loanTerm <= 36) {
            return getRateForTerm36Months(downPaymentPercentage);
        } else if (loanTerm <= 60) {
            return getRateForTerm60Months(downPaymentPercentage);
        } else if (loanTerm <= 84) {
            return getRateForTerm84Months(downPaymentPercentage);
        }
        return 0; // Якщо термін не підходить, повертаємо 0
    }

    // Процентні ставки для різних термінів кредиту:

    // Для терміну 12 місяців
    private static double getRateForTerm12Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 6.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 4.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 2.99;
        } else if (downPaymentPercentage >= 50) {
            return 0.01;
        }
        return 0; // Якщо внесок не підходить, повертаємо 0
    }

    // Для терміну 24 місяці
    private static double getRateForTerm24Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 10.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 8.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 7.99;
        } else if (downPaymentPercentage >= 50) {
            return 3.99;
        }
        return 0;
    }

    // Для терміну 36 місяців
    private static double getRateForTerm36Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 11.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 9.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 8.99;
        } else if (downPaymentPercentage >= 50) {
            return 7.99;
        }
        return 0;
    }

    // Для терміну 60 місяців
    private static double getRateForTerm60Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 14.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 12.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 12.99;
        } else if (downPaymentPercentage >= 50) {
            return 11.99;
        }
        return 0;
    }

    // Для терміну 84 місяці
    private static double getRateForTerm84Months(double downPaymentPercentage) {
        if (downPaymentPercentage >= 20 && downPaymentPercentage < 30) {
            return 15.99;
        } else if (downPaymentPercentage >= 30 && downPaymentPercentage < 40) {
            return 14.99;
        } else if (downPaymentPercentage >= 40 && downPaymentPercentage < 50) {
            return 12.99;
        } else if (downPaymentPercentage >= 50) {
            return 12.99;
        }
        return 0;
    }
}
