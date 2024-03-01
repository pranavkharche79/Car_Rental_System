package com.demo.test;

import java.util.Scanner;
import com.demo.service.CarService;
import com.demo.service.CarServiceImpl;

public class TestCarRentalSystem {

	public static void main(String[] args) {
		
		CarService cserv=new CarServiceImpl();
		Scanner sc=new Scanner(System.in);
        int choice=0;
        
        do {
			System.out.println("\n1. Rent a Car\n2. Return a Car\n3. Exit\nEnter choice:");
			choice = sc.nextInt();
			
			switch(choice) {
			case 1:
				cserv.rentacar();
				break;
				
			case 2:
				cserv.ReturnACar();
				break;
			case 3:
				sc.close();
				System.out.println("Thank you for visiting...");
				break;
			default:
				System.out.println("Invalid choice");
			}
			
			
		}while(choice != 3);

	}

}
