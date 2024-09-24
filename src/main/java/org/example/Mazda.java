package org.example;

import org.example.Car;

public class Mazda extends Car {

    // Конструктор класу Mazda, який викликає конструктор базового класу Car.
    public Mazda(double price, double downPayment, int loanTerm, double interestRate, double commission) {
        super("Mazda", price, downPayment, loanTerm, interestRate, commission); // Викликаємо конструктор суперкласу Car
    }
}
