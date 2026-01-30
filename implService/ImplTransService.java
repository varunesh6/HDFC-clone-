package implService;
import DAO.UserDAO;
import controller.InputUtils;
import service.checkBalance;
import service.userDetails;
import service.transHistory;

public class ImplTransService implements userDetails,checkBalance,transHistory {
	private final UserDAO userDAO = new UserDAO();
	@Override
	public void userDetails() {
		System.out.println("User Details Displayed");
	}
	@Override
	public void checkBalance() {
		System.out.print("Enter Account Number: ");
		long accNo = InputUtils.getScanner().nextLong();
		double balance = userDAO.checkBalance(accNo);

		if (balance >= 0) {
			System.out.println("Current Balance: " + balance);
		} else {
			System.out.println("Account not found");
		}
	}
	@Override
	public void transHistory() {
		System.out.println("Transaction History Displayed");
	}
}
