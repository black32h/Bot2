package org.example;

public class MG extends Car {

    // Конструктор класу MG, який викликає конструктор базового класу Car.
    public MG(double price, double downPayment, int loanTerm, double interestRate, double commission) {
        super("MG", price, downPayment, loanTerm, interestRate, commission); // Викликаємо конструктор класу Car
    }
}
