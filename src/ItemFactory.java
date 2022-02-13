import java.util.Objects;
import java.util.Random;

interface ItemFactory {
       Random random = new Random();
        static  Item createItem(String itemName){
                Item result;
            switch (itemName) {
                case "PaperScore" -> result = new PaperScore();
                case "CD" -> result = new CD();
                case "Vinyl" -> result = new Vinyl();
                case "CDPlayer" -> result = new CDPlayer();
                case "RecordPlayer" -> result = new RecordPlayer();
                case "MP3" -> result = new MP3();
                case "Guitar" -> result = new Guitar();
                case "Bass" -> result = new Bass();
                case "Mandolin" -> result = new Mandolin();
                case "Flute" -> result = new Flute();
                case "Harmonica" -> result = new Harmonica();
                case "Hat" -> result = new Hat();
                case "Shirt" -> result = new Shirt();
                case "Bandana" -> result = new Bandana();
                case "PracticeAmp" -> result = new PracticeAmp();
                case "Cable" -> result = new Cable();
                case "StringAcc" -> result = new StringAcc();
                default -> result = null;
            }
            if(result == null)throw new IllegalStateException("creation cannot be null");
            return result;
        }
        static Item randomItem(String itemType){
            Item item = createItem(itemType);
            item.type = itemType;
            item.SKU = Store.getSKU(item);
            item.name = item.type + random.nextInt(1,1300);
            item.used = random.nextBoolean();
            item.condition = random.nextInt(1,6);
            item.daySold = -1;
            item.dayArrived = 0;
            item.purchasePrice = random.nextInt(3,50);
            item.listPrice = -1;
            return  item;
        }
}
