import java.util.ArrayList;
import java.util.Random;

public class Customer{
    String type;
    Item item;
    int desireType;
    private static final Random random = new Random(System.currentTimeMillis());

    Customer(String type){
        this.type = type;
        desireType = random.nextInt(0,17);
        item = ItemFactory.randomItem(Store.SKUitemclass(desireType));
    }
}
