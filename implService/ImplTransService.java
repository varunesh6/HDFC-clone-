package implService;
import service.TranscationService;

public class ImplTransService implements TranscationService {
    @Override
    public void userDetails(){
        System.out.println("userDetails Successfuly");
    }
    @Override
    public void checkBalance(){
        System.out.println("checkBalance Successfuly");
    }
     @Override
    public void transHistory(){
        System.out.println( "transHistory Successfuly");
    }
}
