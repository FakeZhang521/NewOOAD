import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


class Store{
     private double register = 75;
     private final ArrayList<Item> goods;
     final ArrayList<ArrayList<Integer>> mailBox;
     private int[] inventorylist;
     private final Random random = new Random(System.currentTimeMillis());
     private final ArrayList<Item> soldList;
     Store(){
          // TODO: 2/7/2022 Please implement the initialization part
          //For the use of SKU attribute, please check how I coded the
          //scheduleShipping method and shipToStore method in Scheduler.java
          //And cleanMailBox() method in s class.

          //The following is my test code.
         goods = new ArrayList<>();
         mailBox = new ArrayList<>();
         inventorylist = new int[17];
         soldList = new ArrayList<>();
         for(int i=0; i<17; i++){
             inventorylist[i]=0;
         }
           for(int x = 0; x<inventorylist.length;x++){
               addItem(true,x,0,random.nextInt(1,51), 0,3);
           }

     }

     private void addItem(boolean isForInitial,int ... input){
         for(int x =0;x<input[4];x++){
             Item newItem = ItemFactory.createItem(SKUitemclass(input[0]));
             newItem.SKU = input[0];
             newItem.dayArrived = input[1];
             newItem.purchasePrice = input[2];
             newItem.condition = random.nextInt(1,6);
             newItem.type = SKUitemclass(newItem.SKU);
             newItem.name = newItem.type + random.nextInt()%100;
             newItem.listPrice = newItem.purchasePrice * 2;
             goods.add(newItem);
             if(!isForInitial)System.out.println(newItem.name+ " has been added to the inventory");
             inventorylist[newItem.SKU] ++;
             if(!isForInitial)System.out.println("Current stock size: "+ goods.size());
         }
     }


     private int getSKU(Item item){
         int SKU = switch (item.type) {
             case "PaperScore" -> 0;
             case "CD" -> 1;
             case "Vinyl" -> 2;
             case "CDPlayer" -> 3;
             case "RecordPlayer" -> 4;
             case "MP3" -> 5;
             case "Guitar" -> 6;
             case "Bass" -> 7;
             case "Mandolin" -> 8;
             case "Flute" -> 9;
             case "Harmonica" -> 10;
             case "Hat" -> 11;
             case "Shirt" -> 12;
             case "Bandana" -> 13;
             case "PracticeAmp" -> 14;
             case "Cable" -> 15;
             case "StringAcc" -> 16;
             default -> -1;
         };
         return SKU;
     }

    private void getItemName(Message message){
         message.setExtrainfo(SKUitemclass(message.getSKU()));
    }

    private String SKUitemclass(int SKU){
        String item = switch (SKU) {
            case 0 -> "PaperScore";
            case 1 -> "CD";
            case 2 -> "Vinyl";
            case 3 -> "CDPlayer";
            case 4 -> "RecordPlayer";
            case 5 -> "MP3";
            case 6 -> "Guitar";
            case 7 -> "Bass";
            case 8 -> "Mandolin";
            case 9 -> "Flute";
            case 10 -> "Harmonica";
            case 11 -> "Hat";
            case 12 -> "Shirt";
            case 13 -> "Bandana";
            case 14 -> "PracticeAmp";
            case 15 -> "Cable";
            case 16 -> "StringAcc";
            default -> "undefined";
        };
        return item;
    }

     //Add all shipped things to the goods list.
     private void cleanMailBox(){
         if(mailBox.size() == 0)System.out.println("Nothing has arrived yet");
          mailBox.forEach(entry->{
                        addItem(false,entry.get(0),entry.get(1),entry.get(2),entry.get(3),3);
                    }
               );
          mailBox.clear();
     }

     private void printInventory(){
         System.out.println("*****************************************************");
         System.out.println("Current stock:");
         int value = 0;
         int newLine = 0;
         for(Item item:goods){
             value += item.purchasePrice;
             if(newLine ==2){
                 newLine = 0;
                 System.out.print("\n");
             }
             System.out.print(" "+item.name + " "+"(cost:"+ item.purchasePrice+",condition:"+item.getCondition()+")     ");
             newLine ++;
         }/////

         System.out.println("\nTotal value: "+value);
         System.out.println("Total numbers: "+ Arrays.stream(inventorylist).sum());
         System.out.println("End");
         System.out.println("*****************************************************");
     }

     private void checkMoney(Message message){
         System.out.println("The cash register has: "+register);
          if(register<75){
               message.setExtrainfo("needMoney");
          }
          else{
              message.setExtrainfo("doNotNeedMoney");
          }
     }

     private void add_1000(){
          register += 1000;
          System.out.println("Cash register now has: "+register);
     }

    //return the user the goods list.
     private void sumInventory(Message message){
          ArrayList<Item> ItemCopy = new ArrayList<>(goods);
          int[] InventoryCopy = new int[17];
          System.arraycopy(inventorylist,0,InventoryCopy,0,inventorylist.length);
          message.put(ItemCopy);
          message.put(InventoryCopy);
     }

    private void payment(Message message){
         int paidAmount = Integer.parseInt(message.getExtraInfo());
         register -= paidAmount;
         System.out.println("Payment: "+paidAmount);
         if(register <= 0){
             System.out.println("Negative money. The staff went ahead to take some money from the bank");
             scheduler.sendMessage(new Message("gotoBank"));
         }
    }

    private void removeItem(Message message){
         goods.removeIf(item->item.name.equals(message.getExtraInfo()));
         inventorylist[message.getSKU()] -= 1;
         System.out.println(message.getExtraInfo()+" has been removed from the inventory");
    }
    private void changeListPrice(Message message){
         goods.forEach(item -> {
             if (item.name.equals(message.getExtraInfo())){
                 item.listPrice = Integer.parseInt(message.getExtraInfo());
             }
         });
    }
    private void grapOneItem(Message message){
         for(Item item : goods){
             if(item.name.equals(message.getExtraInfo()))message.put(item);
         }
    }
    private void addOneItem(Message message){
         Item item = message.getItem(0);
         if(item.listPrice != item.purchasePrice*2)item.listPrice = item.purchasePrice * 2;
    }

    private void grabAllGoods(Message message){
         message.put(goods);
    }
    private void removeALLBrokentItem(){
         goods.removeIf(item -> item.condition <=0);
         recountInventory();
    }
     private void recountInventory(){
         inventorylist = new int[17];
         for(Item item: goods){
             inventorylist[getSKU(item)] ++;
         }
     }
     public void receiveMessage(Message message) {
          switch (message.getEvent()) {
               case "checkMail" -> cleanMailBox();
               case "checkRegister" -> checkMoney(message);
               case "add_1000" -> add_1000();
               case "checkInventory"-> sumInventory(message);
               case "getItemName"-> getItemName(message);
               case "payment"-> payment(message);
               case "removeItem"->removeItem(message);
               case "changeListPrice"->changeListPrice(message);
               case  "grapAnItem"->grapOneItem(message);
               case  "AddOneItem"->addOneItem(message);
               case  "grabAllGoods"-> grabAllGoods(message);
               case  "printInventory"->printInventory();
               case  "removeBrokenItem"->removeALLBrokentItem();
               case  "printCash"->System.out.println("Cash Register has: "+ register);
          }
     }

}
