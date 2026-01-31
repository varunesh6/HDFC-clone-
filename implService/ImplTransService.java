package implService;

import DAO.UserDAO;
import DAO.UserDetails;
import service.checkBalance;
import service.userDetails;
import service.transHistory;
import session.UserSession;

public class ImplTransService implements userDetails, checkBalance, transHistory {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void userDetails() {

        if (!UserSession.isLoggedIn()) {
            System.out.println(" Please login first");
            return;
        }

        UserDetails user = UserSession.getCurrentUser();
        UserDetails full = userDAO.getUserDetails(user.getAccountNumber());

        System.out.println("Name: " + full.getUserName());
        System.out.println("Account: " + full.getAccountNumber());
        System.out.println("Balance: " + full.getBalance());
    }

    @Override
    public void checkBalance() {

        if (!UserSession.isLoggedIn()) {
            System.out.println(" Please login first");
            return;
        }

        UserDetails user = UserSession.getCurrentUser();
        double balance = userDAO.checkBalance(user.getAccountNumber());

        System.out.println(" Balance: " + balance);
    }

    @Override
    public void transHistory() {

        if (!UserSession.isLoggedIn()) {
            System.out.println(" Please login first");
            return;
        }

        UserDetails user = UserSession.getCurrentUser();
        userDAO.transactionHistory(user.getAccountNumber());
    }
}
