package org.example;

public class CarBuilder {
    private String carModel; // Модель автомобіля
    private double price; // Ціна автомобіля
    private double downPayment; // Авансовий внесок
    private int loanTerm; // Термін кредитування
    private double interestRate; // Відсоткова ставка
    private double commission; // Комісія

    // Метод для встановлення моделі автомобіля
    public CarBuilder setCarModel(String carModel) {
        this.carModel = carModel;
        return this; // Повертаємо поточний об'єкт для ланцюгового виклику
    }

    // Метод для встановлення ціни автомобіля
    public CarBuilder setPrice(double price) {
        this.price = price;
        return this; // Повертаємо поточний об'єкт для ланцюгового виклику
    }

    // Метод для встановлення авансового внеску
    public CarBuilder setDownPayment(double downPayment) {
        this.downPayment = downPayment;
        return this; // Повертаємо поточний об'єкт для ланцюгового виклику
    }

    // Метод для встановлення терміна кредитування
    public CarBuilder setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
        return this; // Повертаємо поточний об'єкт для ланцюгового виклику
    }

    // Метод для встановлення відсоткової ставки
    public CarBuilder setInterestRate(double interestRate) {
        this.interestRate = interestRate;
        return this; // Повертаємо поточний об'єкт для ланцюгового виклику
    }

    // Метод для встановлення комісії.
    public CarBuilder setCommission(double commission) {
        this.commission = commission;
        return this; // Повертаємо поточний об'єкт для ланцюгового виклику
    }

    // Метод для створення об'єкту Car з заданими параметрами
    public Car build() {
        return new Car(carModel, price, downPayment, loanTerm, interestRate, commission);
    }
}
