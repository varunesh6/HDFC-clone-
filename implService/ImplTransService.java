package implService;
import service.checkBalance;
import service.userDetails;
import service.transHistory;

public class ImplTransService implements userDetails,checkBalance,transHistory {
    @Override
    public void userDetails() {
        System.out.println("User Details Displayed");
    } 
    @Override
    public void checkBalance() {    
        System.out.println("Balance Checked");
    }       
    @Override
    public void transHistory() {        
        System.out.println("Transaction History Displayed");
    }  
}
