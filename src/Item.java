abstract class Item {
        int SKU;
        String type;
        String name;
        double purchasePrice;
        double listPrice;
        boolean used;
        int dayArrived;
        int condition;
        double salesPrice;
        int daySold;
}

class Music extends Item{
        String band;
        String album;
        Music(){
                super.type = "music";
                band = "";
                album = "";
        }
        Music(String band,String album){
                super.type = "music";
                this.band = band;
                this.album = album;
        }
}

class Player extends Item{
        Player(){
                super.type = "player";
        }
}

class Instrument extends Item{
        int accuracy;
        Instrument(){
                super.type = "instrument";
        }

}

class Clothing extends Item{
        int durability;
        String texture;
        Clothing(){
                super.type = "clothing";
        }

}

class Accessory extends Item{
        Accessory(){
                super.type = "accessory";
        }
}

