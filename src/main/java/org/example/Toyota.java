package org.example;

public class Toyota extends Car {

    // Конструктор класу Toyota, який викликає конструктор базового класу Car.
    public Toyota(double price, double downPayment, int loanTerm, double interestRate, double commission) {
        super("Toyota", price, downPayment, loanTerm, interestRate, commission); // Викликаємо конструктор класу Car
    }
}
