package implService;

import DAO.UserDAO;
import controller.InputUtils;
import service.depositAmount;
import service.withdrawAmount;
import service.moneyTransfer;

public class ImplAccService implements depositAmount, withdrawAmount, moneyTransfer {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void depositAmount() {

        System.out.print("Enter Account Number: ");
        long accNo = InputUtils.getScanner().nextLong();

        System.out.print("Enter Deposit Amount: ");
        double amount = InputUtils.getScanner().nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be greater than 0");
            return;
        }

        userDAO.deposit(accNo, amount);
        System.out.println("Amount Deposited Successfully");
    }

    @Override
    public void withdrawAmount() {
        System.out.println("Withdraw not implemented yet");
    }

    @Override
    public void moneyTransfer() {
        System.out.println("Transfer not implemented yet");
    }
}
