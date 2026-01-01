package implService;
import service.AccountService;

public class ImplAccService implements AccountService {
    @Override
    public void depositAmount(){
        
    }
    @Override
    public void withdrawAmount(){
        System.out.println("withdraw Successfuly");
    }
    @Override
    public void moneyTransfer(){
        System.out.println("transfer successfully");
    }
}
