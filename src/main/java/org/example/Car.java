package org.example;

public class Car {
    private String model; // Модель автомобіля
    private double price; // Ціна автомобіля
    private double downPayment; // Авансовий внесок
    private int loanTerm; // Термін кредитування
    private double interestRate; // Відсоткова ставка
    private double commission; // Комісія

    // Конструктор класу Car
    public Car(String model, double price, double downPayment, int loanTerm, double interestRate, double commission) {
        this.model = model;
        this.price = price;
        this.downPayment = downPayment;
        this.loanTerm = loanTerm;
        this.interestRate = interestRate;
        this.commission = commission;
    }

    // Геттер для моделі автомобіля
    public String getModel() {
        return model;
    }

    // Сеттер для моделі автомобіля,
    public void setModel(String model) {
        this.model = model;
    }

    // Геттер для ціни автомобіля
    public double getPrice() {
        return price;
    }

    // Сеттер для ціни автомобіля
    public void setPrice(double price) {
        this.price = price;
    }

    // Геттер для авансового внеску
    public double getDownPayment() {
        return downPayment;
    }

    // Сеттер для авансового внеску
    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    // Геттер для терміна кредитування
    public int getLoanTerm() {
        return loanTerm;
    }

    // Сеттер для терміна кредитування
    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    // Геттер для відсоткової ставки
    public double getInterestRate() {
        return interestRate;
    }

    // Сеттер для відсоткової ставки
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // Геттер для комісії
    public double getCommission() {
        return commission;
    }

    // Сеттер для комісії
    public void setCommission(double commission) {
        this.commission = commission;
    }
}
