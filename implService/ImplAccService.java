package implService;

import DAO.UserDAO;
import DAO.UserDetails;
import controller.InputUtils;
import service.depositAmount;
import service.withdrawAmount;
import service.moneyTransfer;
import session.UserSession;

public class ImplAccService implements depositAmount, withdrawAmount, moneyTransfer {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void depositAmount() {

        if (!UserSession.isLoggedIn()) {
            System.out.println(" Please login first");
            return;
        }

        UserDetails user = UserSession.getCurrentUser();

        System.out.print("Enter Deposit Amount: ");
        double amount = InputUtils.getScanner().nextDouble();

        userDAO.deposit(user.getAccountNumber(), amount);
        System.out.println(" Deposit Successful");
    }

    @Override
    public void withdrawAmount() {

        if (!UserSession.isLoggedIn()) {
            System.out.println(" Please login first");
            return;
        }

        UserDetails user = UserSession.getCurrentUser();

        System.out.print("Enter Withdraw Amount: ");
        double amount = InputUtils.getScanner().nextDouble();

        userDAO.withdraw(user.getAccountNumber(), amount);
        System.out.println(" Withdraw Successful");
    }

    @Override
    public void moneyTransfer() {

        if (!UserSession.isLoggedIn()) {
            System.out.println(" Please login first");
            return;
        }

        UserDetails user = UserSession.getCurrentUser();

        System.out.print("Enter To Account Number: ");
        long toAcc = InputUtils.getScanner().nextLong();

        System.out.print("Enter Amount: ");
        double amount = InputUtils.getScanner().nextDouble();

        boolean success = userDAO.transfer(
            user.getAccountNumber(),
            toAcc,
            amount
        );

        System.out.println(success ? " Transfer Successful" : " Transfer Failed");
    }
}
