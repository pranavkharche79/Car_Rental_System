package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.demo.models.Car;
import com.demo.models.Customer;
import com.demo.models.Rental;

public class CarServiceImpl implements CarService
{
	private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    Scanner sc=new Scanner(System.in);

    public CarServiceImpl() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        addCar(car1);
        addCar(car2);
        addCar(car3);
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }

	@Override
	public void rentacar() {
		System.out.println("\n== Rent a Car ==\n");
        System.out.print("Enter your name: ");
        String customerName = sc.nextLine();

        System.out.println("\nAvailable Cars:");
        for (Car car : cars) {
            if (car.isAvailable()) {
                System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
            }
        }

        System.out.print("\nEnter the car ID you want to rent: ");
        String carId = sc.nextLine();

        System.out.print("Enter the number of days for rental: ");
        int rentalDays = sc.nextInt();
        sc.nextLine(); // Consume newline

        Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
        addCustomer(newCustomer);

        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getCarId().equals(carId) && car.isAvailable()) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar != null) {
            double totalPrice = selectedCar.calculatePrice(rentalDays);
            System.out.println("\n== Rental Information ==\n");
            System.out.println("Customer ID: " + newCustomer.getCustomerId());
            System.out.println("Customer Name: " + newCustomer.getName());
            System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
            System.out.println("Rental Days: " + rentalDays);
            System.out.printf("Total Price: $%.2f%n", totalPrice);

            System.out.print("\nConfirm rental (Y/N): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                rentCar(selectedCar, newCustomer, rentalDays);
                System.out.println("\nCar rented successfully.");
            } else {
                System.out.println("\nRental canceled.");
            }
        } else {
            System.out.println("\nInvalid car selection or car not available for rent.");
        }
		
	}

	@Override
	public void ReturnACar() {
		 System.out.println("\n== Return a Car ==\n");
         System.out.print("Enter the car ID you want to return: ");
         String carId = sc.nextLine();

         Car carToReturn = null;
         for (Car car : cars) {
             if (car.getCarId().equals(carId) && !car.isAvailable()) {
                 carToReturn = car;
                 break;
             }
         }

         if (carToReturn != null) {
             Customer customer = null;
             for (Rental rental : rentals) {
                 if (rental.getCar() == carToReturn) {
                     customer = rental.getCustomer();
                     break;
                 }
             }

             if (customer != null) {
                 returnCar(carToReturn);
                 System.out.println("Car returned successfully by " + customer.getName());
             } else {
                 System.out.println("Car was not rented or rental information is missing.");
             }
         } else {
             System.out.println("Invalid car ID or car is not rented.");
         }
		
	}
}
