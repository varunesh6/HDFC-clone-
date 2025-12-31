package controller;
import service.AuthService;
import service.AccountService;
import service.TranscationService;
import controller.InputUtils;
import implService.ImplAuthService;
import implService.ImplTransService;
import implService.ImplAccService;


public class Main_Controller  {
    AuthService authService = new ImplAuthService();
    AccountService accountService = new ImplAccService();
    TranscationService transcationService = new ImplTransService();

    public void Start(){                                          
        int userChoice = InputUtils.getScanner().nextInt();
        switch (userChoice) {
            case 1 -> authService.registerUser();
            case 2 -> authService.loginUser();
            case 3 -> accountService.depositAmount();
            case 4 -> accountService.withdrawAmount();
            case 5 -> accountService.moneyTransfer();
            case 6 -> transcationService.userDetails();
            case 7 -> transcationService.checkBalance();
            case 8 -> transcationService.transHistory();
            case 9 -> authService.closeAccount();
            default -> throw new IllegalArgumentException("invalid choice ");
        }
    }
}
