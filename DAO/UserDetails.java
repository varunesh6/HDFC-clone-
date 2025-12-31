package DAO;

public class UserDetails {

    private int userId;
    private String userName;
    private long mobileNumber;
    private long accountNumber;
    private String adharNumber;
    private String panCard;
    private String userAddress;
    private String gender;
    private double balance;
    private String password;

    // No-argument constructor
    public UserDetails() {}

    // Parameterized constructor
    public UserDetails(String userName, long mobileNumber, String adharNumber,
                       String panCard, String userAddress, String gender,
                       double balance, String password) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.adharNumber = adharNumber;
        this.panCard = panCard;
        this.userAddress = userAddress;
        this.gender = gender;
        this.balance = balance;
        this.password = password;
    }

    // Getters & Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public long getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(long mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getAdharNumber() { return adharNumber; }
    public void setAdharNumber(String adharNumber) { this.adharNumber = adharNumber; }

    public String getPanCard() { return panCard; }
    public void setPanCard(String panCard) { this.panCard = panCard; }

    public String getUserAddress() { return userAddress; }
    public void setUserAddress(String userAddress) { this.userAddress = userAddress; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

}
