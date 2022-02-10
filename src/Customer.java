import java.util.ArrayList;
import java.util.Random;

public class Customer{
    String type;
    Object item;
    private static final Random random = new Random(System.currentTimeMillis());

    Customer(){
        int SKU = 14;
        Message grapItemName = new Message("getItemName");
        grapItemName.setSKU(SKU);
        scheduler.sendMessage(grapItemName);
        String SKUtype = grapItemName.getExtraInfo();

        System.out.println("The SKU is: "+SKU);
        System.out.println("What I got back from the message: "+SKUtype);
        System.out.println("Correct answer: PracticeAmp");
    }
}
