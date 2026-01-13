package controller;

import service.registerUser;
import service.checkBalance;
import service.userDetails;
import service.transHistory;
import service.depositAmount;
import service.loginUser;
import service.withdrawAmount;
import service.moneyTransfer;
import service.closeAccount;
import implService.ImplAuthService;
import implService.ImplAccService;
import implService.ImplTransService;

public class Main_Controller {

    private final registerUser registerUserControl = new ImplAuthService();
    private final loginUser loginUserControl = new ImplAuthService();
    private final closeAccount closeAccountControl = new ImplAuthService();

    private final depositAmount depositAmountControl = new ImplAccService();
    private final withdrawAmount withdrawAmountControl = new ImplAccService(); 
    private final moneyTransfer moneyTransferControl = new ImplAccService();

    private final checkBalance checkBalanceControl = new ImplTransService();
    private final userDetails userDetailsControl = new ImplTransService();
    private final transHistory transHistoryControl = new ImplTransService();

    // MAIN MENU
    public void startMainMenu() {

        System.out.println("\n--- HDFC Bank Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        int choice = InputUtils.getScanner().nextInt();

        switch (choice) {
            case 1 -> registerUserControl.registerUser();
            case 2 -> {
                if (loginUserControl.loginUser()) {
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
                case 3 -> depositAmountControl.depositAmount();
                case 4 -> withdrawAmountControl.withdrawAmount();
                case 5 -> moneyTransferControl.moneyTransfer();
                case 6 -> userDetailsControl.userDetails();
                case 7 -> checkBalanceControl.checkBalance();
                case 8 -> transHistoryControl.transHistory();
                case 9 -> {
                    closeAccountControl.closeAccount();
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
