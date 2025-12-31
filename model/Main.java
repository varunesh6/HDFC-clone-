package model;

import controller.Main_Controller;

public class Main {
    public static void main(String[] args){
        Main_Controller main_Controller = new Main_Controller();
        while(true){
            System.out.println("\n--- HDFC Bank Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Money Transfer");
            System.out.println("6. User Details");
            System.out.println("7. Check Balance");
            System.out.println("8. Tranaction History");
            System.out.println("9  close the account");
            System.out.println("10 Exit");
            System.out.println("Enter your choice : ");
            main_Controller.Start();
        }
    }
}
