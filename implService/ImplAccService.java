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
		System.out.println("Current Balance: " + userDAO.checkBalance(accNo));
	}

	@Override
	public void withdrawAmount() {
		System.out.print("Enter Account Number: ");
		long accNo = InputUtils.getScanner().nextLong();

		System.out.print("Enter Withdraw Amount: ");
		double amount = InputUtils.getScanner().nextDouble();


		if (amount <= 0) {
			System.out.println("Amount must be greater than 0");
			return;
		}

		userDAO.withdraw(accNo, amount);
		System.out.println("Amount Deposited Successfully");
		System.out.println("Current Balance: " + userDAO.checkBalance(accNo));
	}

	@Override
	public void moneyTransfer() {
		System.out.print("Enter From Account Number: ");
		long fromAccNo = InputUtils.getScanner().nextLong();

		System.out.print("Enter To Account Number: ");
		long toAccNo = InputUtils.getScanner().nextLong();

		System.out.print("Enter Amount: ");
		double amount = InputUtils.getScanner().nextDouble();

		boolean success = userDAO.transfer(fromAccNo, toAccNo, amount);

		if (success) {
			System.out.println(" Transfer successful");
		} else {
			System.out.println(" Transfer failed");
		}



	}
}
