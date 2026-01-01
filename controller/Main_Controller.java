package controller;

import service.AuthService;
import service.AccountService;
import service.TranscationService;
import implService.ImplAuthService;
import implService.ImplAccService;
import implService.ImplTransService;

public class Main_Controller {

    private final AuthService authService = new ImplAuthService();
    private final AccountService accountService = new ImplAccService();
    private final TranscationService transactionService = new ImplTransService();

    // MAIN MENU
    public void startMainMenu() {

        System.out.println("\n--- HDFC Bank Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        int choice = InputUtils.getScanner().nextInt();

        switch (choice) {
            case 1 -> authService.registerUser();
            case 2 -> {
                if (authService.loginUser()) {
                    startServiceMenu();   
                }
            }
            case 3 -> {
                System.out.println("Thank you for using HDFC Bank ");
                System.exit(0);
            }
            default -> System.out.println("Invalid Choice!");
        }
    }

    // SERVICE MENU (After Login)
    private void startServiceMenu() {

        boolean isLoggedIn = true;

        while (isLoggedIn) {

            System.out.println("\n--- Account Services ---");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Money Transfer");
            System.out.println("6. User Details");
            System.out.println("7. Check Balance");
            System.out.println("8. Transaction History");
            System.out.println("9. Close Account");
            System.out.println("10. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtils.getScanner().nextInt();

            switch (choice) {
                case 3 -> accountService.depositAmount();
                case 4 -> accountService.withdrawAmount();
                case 5 -> accountService.moneyTransfer();
                case 6 -> transactionService.userDetails();
                case 7 -> transactionService.checkBalance();
                case 8 -> transactionService.transHistory();
                case 9 -> {
                    authService.closeAccount();
                    isLoggedIn = false;
                }
                case 10 -> {
                    System.out.println("Logged out successfully.");
                    isLoggedIn = false;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
