package implService;

import DAO.UserDAO;
import DAO.UserDetails;
import controller.InputUtils;
import service.AuthService;

public class ImplAuthService implements AuthService {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void registerUser() {

        UserDetails user = new UserDetails();
        InputUtils.getScanner().nextLine();
        System.out.print("Enter Mobile: ");
        long mobile = Long.parseLong(InputUtils.getScanner().nextLine());
        user.setMobileNumber(mobile);

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

        user.setBalance(0.0);

        if (userDAO.saveUser(user)) {
            System.out.println("🎉 Registration Successful!");
            System.out.println("🏦 Account Number: " + user.getAccountNumber());
        }
    }

    @Override
public void loginUser() {
    // Clear buffer in case previous input was nextInt()
    InputUtils.getScanner().nextLine(); 

    System.out.print("Enter Mobile: ");
    String mobileInput = InputUtils.getScanner().nextLine().trim();

    if (mobileInput.isEmpty()) {
        System.out.println("❌ Mobile cannot be empty");
        return;
    }

    long mobile;
    try {
        mobile = Long.parseLong(mobileInput);
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid mobile number");
        return;
    }

    System.out.print("Enter Password: ");
    String pass = InputUtils.getScanner().nextLine().trim();

    if (pass.isEmpty()) {
        System.out.println("❌ Password cannot be empty");
        return;
    }

    UserDetails user = userDAO.getUserByMobileAndPassword(mobile, pass);

    if (user != null) {
        System.out.println("✅ Login Successful");
        System.out.println("Account No: " + user.getAccountNumber());
        System.out.println("Welcome " + user.getUserName());
    } else {
        System.out.println("❌ Invalid credentials");
    }
}


    
    @Override
    public void closeAccount() {
        InputUtils.getScanner().nextLine();
        System.out.print("Enter Mobile: ");
        long mobile = Long.parseLong(InputUtils.getScanner().nextLine());

        System.out.print("Enter Password: ");
        String pass = InputUtils.getScanner().nextLine();

        UserDetails user = userDAO.getUserByMobileAndPassword(mobile, pass);

        if (user != null && userDAO.deleteUser(user.getUserId())) {
            System.out.println("✅ Account Closed Successfully");
        } else {
            System.out.println("❌ Invalid details");
        }
    }
}
