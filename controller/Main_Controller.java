package controller;

import implService.ImplAuthService;
import implService.ImplAccService;
import implService.ImplTransService;
import session.UserSession;

public class Main_Controller {

    private final ImplAuthService authService = new ImplAuthService();
    private final ImplAccService accService = new ImplAccService();
    private final ImplTransService transService = new ImplTransService();

    public void startMainMenu() {

        while (true) {

            if (!UserSession.isLoggedIn()) {

                System.out.println("\n--- HDFC Bank Menu ---");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = InputUtils.getScanner().nextInt();

                switch (choice) {
                    case 1 -> authService.registerUser();
                    case 2 -> authService.loginUser();
                    case 3 -> {
                        System.out.println("Thank you for using HDFC Bank ");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Choice!");
                }

            } else {
                startServiceMenu();
            }
        }
    }

    private void startServiceMenu() {

        System.out.println("\n--- Welcome " +
                UserSession.getCurrentUser().getUserName() + " ---");

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
            case 3 -> accService.depositAmount();
            case 4 -> accService.withdrawAmount();
            case 5 -> accService.moneyTransfer();
            case 6 -> transService.userDetails();
            case 7 -> transService.checkBalance();
            case 8 -> transService.transHistory();
            case 9 -> authService.closeAccount();
            case 10 -> {
                UserSession.logout();
                System.out.println(" Logged out successfully.");
            }
            default -> System.out.println("Invalid choice!");
        }
    }
}
