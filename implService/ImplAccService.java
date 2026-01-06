package implService;
import controller.InputUtils;
import service.AccountService;

public class ImplAccService implements AccountService {
    @Override
    public void depositAmount(){
        while (true) {
             System.out.println("Enter the Deposit Amount:");
             double depositAmount =  InputUtils.getScanner().nextDouble();
             if(depositAmount>=1){
                break;
            }else{
                System.out.println("Amount must be Greater than 0");
            }
        }
    }
    @Override
    public void withdrawAmount(){
            System.out.println("Amount must be Greater than 0");
    }
    @Override
    public void moneyTransfer(){
        System.out.println("transfer successfully");
    }
}
