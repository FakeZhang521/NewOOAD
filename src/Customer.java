<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 358aede315c514873e12ac418af7356850d43700
import java.util.Random;

public class Customer{
    String type;
    Object item;
    private static final Random random = new Random(System.currentTimeMillis());

    Customer(){
        int SKU = random.nextInt(0,16);
<<<<<<< HEAD
=======
        String SKUtype = Store.SKUitemclass(SKU);
>>>>>>> 358aede315c514873e12ac418af7356850d43700
    }
}
