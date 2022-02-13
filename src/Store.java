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
    //A function designed to handle the initialization process.
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


     static int getSKU(Item item){
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

    static String SKUitemclass(int SKU){
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
     //A hugh function to print whatever the current information is
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
             System.out.print("\t"+item.name + "\t"+"(cost:"+ item.purchasePrice+",condition:"+item.getCondition()+")\t\t");
             newLine ++;
         }

         System.out.println("\nTotal value: "+value);
         System.out.println("Total numbers: "+ Arrays.stream(inventorylist).sum());
         System.out.println("End");
         System.out.println("*****************************************************");
         System.out.println("Sold Item");
         value = 0;
         newLine = 0;
         for(Item sold:soldList){
             value += sold.salesPrice;
             if(newLine ==2){
                 newLine = 0;
                 System.out.print("\n");
             }
             System.out.print("\t"+sold.name + "\t"+"(soldPrice:"+ sold.salesPrice+",daysold:"+sold.daySold+")\t\t");
             newLine ++;
         }
         System.out.println("\nTotal Sales: "+value);
         System.out.println("Total numbers: "+ soldList.size());
         System.out.println("\n*****************************************************");
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
    //The worker will call this function to check if the register has the enough money to do something
    private void payment(Message message){
         double paidAmount = Double.parseDouble(message.getExtraInfo());
         register -= paidAmount;
         System.out.println("Payment: "+paidAmount);
         if(register <= 0){
             System.out.println("Negative money. The staff went ahead to take some money from the bank");
             scheduler.sendMessage(new Message("gotoBank"));
         }
    }
   //Remove one item.
    private void removeItem(Message message){
         goods.removeIf(item->item.name.equals(message.getExtraInfo()));
         inventorylist[message.getSKU()] -= 1;
    }
    private void changeListPrice(Message message){
         goods.forEach(item -> {
             if (item.name.equals(message.getExtraInfo())){
                 item.listPrice = Integer.parseInt(message.getExtraInfo());
             }
         });
    }
    //Grab oneItem
    private void grabOneItem(Message message){
         for(Item item : goods){
             if(item.name.equals(message.getExtraInfo()))message.put(item);
         }
    }
    //Add one item.
    private void addOneItem(Message message){
         Item item = message.getItem(0);
         if(item.listPrice != item.purchasePrice*2)item.listPrice = item.purchasePrice * 2;
         goods.add(item);
         recountInventory();
    }
    //A convenient function to grab all things.
    private void grabAllGoods(Message message){
         message.put(goods);
    }
    //A convenient function to remove all broken things.
    private void removeALLBrokentItem(){
         goods.removeIf(item -> item.condition <=0);
         recountInventory();
    }
    //Everytime there is modification made to the inventory, we should recount what we have.
     private void recountInventory(){
         inventorylist = new int[17];
         for(Item item: goods){
             inventorylist[getSKU(item)] ++;
         }
     }
   //We should add what we bought to the inventory
    private void addBoughtItem(Message message){
         Item item = message.getItem(0);
         Message message1 = new Message();
         message1.setExtrainfo(String.valueOf(item.purchasePrice));
         goods.add(item);
         recountInventory();
    }
    //If the customer wants to buy something, we should only show the stuff that he wants to buy.
     private void showRelatedItem(Message message){
         goods.forEach(item -> {
             if(item.SKU == Integer.parseInt(message.getExtraInfo()))message.put(item);
         });
     }
     //If the staff sold something, we should change the inventory and put the item in the soldlist.
     private void ItemSold(Message message){
         for(Item item:goods){
             if(item.name.equals(message.getEExtrainfo()))soldList.add(item);
         }
         register += Double.parseDouble(message.getExtraInfo());
         Message message1 = new Message("");
         message1.setExtrainfo(message.getEExtrainfo());
         removeItem(message1);
     }
     //This class will take care of the scheduler's sendMessage function.
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
               case  "grapAnItem"-> grabOneItem(message);
               case  "AddOneItem"->addOneItem(message);
               case  "grabAllGoods"-> grabAllGoods(message);
               case  "printInventory"->printInventory();
               case  "removeBrokenItem"->removeALLBrokentItem();
               case  "printCash"->System.out.println("Cash Register has: "+ register);
               case  "addBoughtItem"->addBoughtItem(message);
              case   "showRelatedItem"->showRelatedItem(message);
              case   "Item sold"->ItemSold(message);
          }
     }

}
