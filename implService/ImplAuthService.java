package implService;

import DAO.UserDAO;
import DAO.UserDetails;
import controller.InputUtils;
import service.registerUser;
import service.loginUser;
import service.closeAccount;
import session.UserSession;

public class ImplAuthService implements registerUser, loginUser, closeAccount {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void registerUser() {
        UserDetails user = new UserDetails();

        InputUtils.getScanner().nextLine();
        System.out.print("Enter Mobile: ");
        user.setMobileNumber(Long.parseLong(InputUtils.getScanner().nextLine()));

        System.out.print("Enter Name: ");
        user.setUserName(InputUtils.getScanner().nextLine());

        System.out.print("Enter Password: ");
        user.setPassword(InputUtils.getScanner().nextLine());

        System.out.print("Enter Aadhaar: ");
        user.setAdharNumber(InputUtils.getScanner().nextLine());

        System.out.print("Enter PAN: ");
        user.setPanCard(InputUtils.getScanner().nextLine());

        System.out.print("Enter Address: ");
        user.setUserAddress(InputUtils.getScanner().nextLine());

        System.out.print("Enter Gender: ");
        user.setGender(InputUtils.getScanner().nextLine());

        user.setBalance(0);

        if (userDAO.saveUser(user)) {
            System.out.println(" User registered successfully!");
        }
    }

    @Override
    public boolean loginUser() {

        if (UserSession.isLoggedIn()) {
            System.out.println(" Already logged in!");
            return true;
        }

        InputUtils.getScanner().nextLine();
        System.out.print("Enter Mobile: ");
        long mobile = Long.parseLong(InputUtils.getScanner().nextLine());

        System.out.print("Enter Password: ");
        String pass = InputUtils.getScanner().nextLine();

        UserDetails user = userDAO.getUserByMobileAndPassword(mobile, pass);

        if (user != null) {
            UserSession.login(user);   // ⭐ STORE SESSION
            System.out.println(" Login Successful");
            System.out.println("Welcome " + user.getUserName());
            System.out.println("Account No: " + user.getAccountNumber());
            return true;
        } else {
            System.out.println(" Invalid credentials");
            return false;
        }
    }

    @Override
    public void closeAccount() {
        if (!UserSession.isLoggedIn()) {
            System.out.println(" Please login first");
            return;
        }

        UserDetails user = UserSession.getCurrentUser();
        userDAO.deleteUser(user.getUserId());
        UserSession.logout();
        System.out.println(" Account closed successfully");
    }
}
