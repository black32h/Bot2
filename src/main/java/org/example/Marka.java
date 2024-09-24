package org.example;

public class Marka {

    private double price; // Ціна автомобіля
    private double downPayment; // Перший внесок
    private int loanTerm; // Термін кредиту в місяцях
    private double interestRate; // Відсоткова ставка
    private double commission; // Комісія

    // Конструктор класу, що ініціалізує всі параметри
    public Marka(double price, double downPayment, int loanTerm, double interestRate, double commission) {
        this.price = price; // Ініціалізація ціни
        this.downPayment = downPayment; // Ініціалізація першого внеску
        this.loanTerm = loanTerm; // Ініціалізація терміна кредиту
        this.interestRate = interestRate; // Ініціалізація відсоткової ставки
        this.commission = commission; // Ініціалізація комісії
    }

    // Геттери і сеттери для всіх полів
    public double getPrice() {
        return price; // Повертає ціну
    }

    public void setPrice(double price) {
        this.price = price; // Встановлює нову ціну
    }

    public double getDownPayment() {
        return downPayment; // Повертає перший внесок
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment; // Встановлює новий перший внесок
    }

    public int getLoanTerm() {
        return loanTerm; // Повертає термін кредиту
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm; // Встановлює новий термін кредиту
    }

    public double getInterestRate() {
        return interestRate; // Повертає відсоткову ставку
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate; // Встановлює нову відсоткову ставку
    }

    public double getCommission() {
        return commission; // Повертає комісію
    }

    public void setCommission(double commission) {
        this.commission = commission; // Встановлює нову комісію
    }

    // Метод для валідації полів
    public void validate() throws IllegalArgumentException {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero."); // Перевірка на позитивність ціни
        }
        if (downPayment < 0 || downPayment > price) {
            throw new IllegalArgumentException("Down payment must be between 0 and the price of the car."); // Перевірка першого внеску
        }
        if (loanTerm <= 0) {
            throw new IllegalArgumentException("Loan term must be greater than zero."); // Перевірка терміна кредиту
        }
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate must be non-negative."); // Перевірка відсоткової ставки
        }
        if (commission < 0) {
            throw new IllegalArgumentException("Commission must be non-negative."); // Перевірка комісії.
        }
    }
}
