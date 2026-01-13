package implService;

import DAO.UserDAO;
import DAO.UserDetails;
import controller.InputUtils;
import service.registerUser;
import service.loginUser;  
import service.closeAccount;


public class ImplAuthService implements  registerUser, loginUser, closeAccount {

    private final UserDAO userDAO = new UserDAO();
        @Override
    public void registerUser() {

        UserDetails user = new UserDetails();
        long mobile;
        while (true) {
            InputUtils.getScanner().nextLine();
            System.out.print("Enter Mobile (10 digits): ");
            String input = InputUtils.getScanner().nextLine();
            if (input.matches("^[6-9]\\d{9}$")) {
                mobile = Long.parseLong(input);
                break;
            } else {
                System.out.println(" Invalid mobile number.");
            }
        }
        user.setMobileNumber(mobile);

        while (true) {
            System.out.print("Enter Name: ");
            String name = InputUtils.getScanner().nextLine();
            if (name.matches("^[A-Za-z ]{3,50}$")) {
                user.setUserName(name);
                break;
            } else {
                System.out.println(" Name must contain only letters (min 3 chars).");
            }
        }

        while (true) {
            System.out.print("Enter Password: ");
            String password = InputUtils.getScanner().nextLine();
            if (password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{6,20}$")) {
                user.setPassword(password);
                break;
            } else {
                System.out.println(
                    " Password must have Uppercase, Lowercase, Digit & Special char (@#$%!)"
                );
            }
        }

        while (true) {
            System.out.print("Enter Aadhaar (12 digits): ");
            String adhar = InputUtils.getScanner().nextLine();
            if (adhar.matches("^\\d{12}$")) {
                user.setAdharNumber(adhar);
                break;
            } else {
                System.out.println(" Invalid Aadhaar number.");
            }
        }

        while (true) {
            System.out.print("Enter PAN (AAAAA9999A): ");
            String pan = InputUtils.getScanner().nextLine().toUpperCase();
            if (pan.matches("^[A-Z]{5}[0-9]{4}[A-Z]$")) {
                user.setPanCard(pan);
                break;
            } else {
                System.out.println(" Invalid PAN format.");
            }
        }

        System.out.print("Enter Address: ");
        user.setUserAddress(InputUtils.getScanner().nextLine());

        while (true) {
            System.out.print("Enter Gender (Male/Female/Other): ");
            String gender = InputUtils.getScanner().nextLine();
            if (gender.matches("(?i)^(male|female|other)$")) {
                user.setGender(gender);
                break;
            } else {
                System.out.println(" Invalid gender.");
            }
        }

        user.setBalance(0.0);

        if (userDAO.saveUser(user)) {
            System.out.println(" User registered successfully!");
        }
    }
    @Override
    public boolean loginUser() {

        InputUtils.getScanner().nextLine(); 

        System.out.print("Enter Mobile Number: ");
        String mobileInput = InputUtils.getScanner().nextLine().trim();

        if (mobileInput.isEmpty()) {
            System.out.println(" Mobile Number cannot be empty");
            return false;
        }

        long mobile;
        try {
            mobile = Long.parseLong(mobileInput);
        } catch (NumberFormatException e) {
            System.out.println(" Invalid mobile number");
            return false;
        }

        System.out.print("Enter Password: ");
        String pass = InputUtils.getScanner().nextLine().trim();

        if (pass.isEmpty()) {
            System.out.println(" Password cannot be empty");
            return false;
        }

        UserDetails user = userDAO.getUserByMobileAndPassword(mobile, pass);

        if (user != null) {
            System.out.println(" Login Successful");
            System.out.println("Account No: " + user.getAccountNumber());
            System.out.println("Welcome " + user.getUserName());
            return true;
        } else {
            System.out.println(" Invalid credentials");
            return false;
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
            System.out.println(" Account Closed Successfully");
        } else {
            System.out.println(" Invalid details");
        }   
    }
}
