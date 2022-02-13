import java.util.ArrayList;
import java.util.Random;

public class Customer{
    String type;
    Item item;
    private static final Random random = new Random(System.currentTimeMillis());

    Customer(){
        int SKU = 14;
        Message grabItemName = new Message("getItemName");
        grabItemName.setSKU(SKU);
        scheduler.sendMessage(grabItemName);
        String SKUtype = grabItemName.getExtraInfo();

        System.out.println("The SKU is: "+SKU);
        System.out.println("What I got back from the message: "+SKUtype);
        System.out.println("Correct answer: PracticeAmp");
    }
}
