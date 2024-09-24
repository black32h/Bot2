package org.example;

import java.util.HashMap;
import java.util.Map;

public class CarLoanCalculator {

    public static void main(String[] args) {
        // Параметри кредиту.
        double carPrice = 1000000; // Ціна автомобіля
        double downPaymentPercentage = 0.30; // 30% початкового внеску
        double downPayment = carPrice * downPaymentPercentage; // Розрахунок авансового внеску
        double loanAmount = carPrice - downPayment; // Сума кредиту

        // Массиви процентних ставок і термінів кредитування
        double[] rates = {3.49, 6.99, 8.99, 11.99, 11.99};
        int[] terms = {12, 24, 36, 48, 60};

        // Обчислення щомісячних платежів
        for (int i = 0; i < rates.length; i++) {
            double adjustedLoanAmount = loanAmount;
            if (terms[i] != 48 && terms[i] != 60) {
                adjustedLoanAmount += loanAmount * 0.0299; // Додаємо комісію
                System.out.printf("Кредит на %d місяців під %.2f%% річних (включаючи одноразову комісію): платіж %.2f\n",
                        terms[i], rates[i], calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]));
                System.out.println("Одноразова комісія складає 2,99% від суми кредиту");
            } else {
                System.out.printf("Кредит на %d місяців під %.2f%% річних (без одноразової комісії): платіж %.2f\n",
                        terms[i], rates[i], calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]));
            }
        }

        // Створюємо екземпляр LoanChecker
        LoanChecker loanChecker = new LoanChecker();
        loanChecker.checkLoanOptions(loanAmount, downPayment, rates, terms, 10000);
    }

    // Метод для розрахунку щомісячного платежу
    public static double calculateMonthlyPayment(double loanAmount, double annualRate, int months) {
        double monthlyRate = annualRate / 100 / 12; // Перетворення річної ставки в місячну
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months)); // Формула ануїтетного платежу
    }

    // Метод для розрахунку умов кредиту
    public Map<Integer, Double> calculateLoanTerms(double carPrice, double downPaymentPercentage) {
        double downPayment = carPrice * (downPaymentPercentage / 100);
        double loanAmount = carPrice - downPayment;

        Map<Integer, Double> results = new HashMap<>();
        double[] rates = {3.49, 6.99, 8.99, 11.99, 11.99}; // Массив процентних ставок
        int[] terms = {12, 24, 36, 48, 60}; // Массив термінів кредитування

        // Обчислення щомісячних платежів
        for (int i = 0; i < rates.length; i++) {
            double adjustedLoanAmount = loanAmount;
            if (terms[i] != 48 && terms[i] != 60) {
                adjustedLoanAmount += loanAmount * 0.0299; // Додаємо комісію
            }
            double monthlyPayment = calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]);
            results.put(terms[i], monthlyPayment); // Зберігаємо результат
        }

        return results; // Повертаємо результати
    }
}
