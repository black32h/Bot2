package org.example;

public class LoanChecker {

    // Метод перевіряє можливості кредиту на основі введених параметрів
    public String checkLoanOptions(double loanAmount, double downPayment, double[] rates, int[] terms, double maxMonthlyPayment) {
        boolean foundSuitableLoan = false; // Флаг для перевірки, чи знайдено відповідний кредит
        String result = ""; // Результат, що повертається

        // Перебираємо всі можливі відсоткові ставки та терміни
        for (int i = 0; i < rates.length; i++) {
            double adjustedLoanAmount = loanAmount; // Коригуємо суму кредиту
            // Якщо термін не 48 або 60 місяців, додаємо одноразову комісію
            if (terms[i] != 48 && terms[i] != 60) {
                adjustedLoanAmount += loanAmount * 0.0299; // Приклад комісії
            }
            // Розрахунок щомісячного платежу
            double monthlyPayment = CarLoanCalculator.calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]);
            // Перевірка, чи щомісячний платіж не перевищує максимальний
            if (monthlyPayment <= maxMonthlyPayment) {
                result += String.format("Клієнт може дозволити собі кредит на %d місяців під %.2f%% річних, з щомісячним платежем %.2f\n", terms[i], rates[i], monthlyPayment);
                if (terms[i] != 48 && terms[i] != 60) {
                    result += "Це включає одноразову комісію.\n";
                }
                result += String.format("Початковий внесок: %.2f\n", downPayment);
                foundSuitableLoan = true; // Встановлюємо флаг, що кредит знайдено
                break; // Виходимо з циклу
            }
        }

        // Якщо не знайдено підходящий кредит
        if (!foundSuitableLoan) {
            double closestMonthlyPayment = Double.MAX_VALUE; // Найближчий платіж
            int closestTerm = 0; // Найближчий термін
            double closestRate = 0; // Найближча ставка

            // Перебираємо всі ставки і терміни для знаходження найближчого варіанту
            for (int i = 0; i < rates.length; i++) {
                double adjustedLoanAmount = loanAmount;
                if (terms[i] != 48 && terms[i] != 60) {
                    adjustedLoanAmount += loanAmount * 0.0299;
                }
                double monthlyPayment = CarLoanCalculator.calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]);
                // Перевірка, чи є цей платіж найближчим до максимального
                if (Math.abs(monthlyPayment - maxMonthlyPayment) < Math.abs(closestMonthlyPayment - maxMonthlyPayment)) {
                    closestMonthlyPayment = monthlyPayment;
                    closestTerm = terms[i];
                    closestRate = rates[i];
                }
            }

            // Формування результату з найближчими варіантами.
            result += String.format("Найближчий варіант кредиту на %d місяців під %.2f%% річних, з щомісячним платежем %.2f\n (Без одноразової комісії)\n", closestTerm, closestRate, closestMonthlyPayment);
            if (closestTerm != 48 && closestTerm != 60) {
                result += "Це включає одноразову комісію.\n";
            }
            result += String.format("Початковий внесок: %.2f\n", downPayment);
        }
        return result; // Повертаємо результат
    }
}
