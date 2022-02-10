import java.util.Objects;

interface ItemFactory {
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
}
