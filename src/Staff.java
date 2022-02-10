import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

class Staff implements ProjectMessage{
      String name;
      int dayWorked;
      boolean workToday = false;
      private static final Random random = new Random(System.currentTimeMillis());
      Staff(String name){
            this.name = name;
            dayWorked = 0;
      }
      //A staff member's initial action.
      private void ArriveAtStore(){
            System.out.println("*****************************************************");
            dayWorked ++;
            System.out.println(name+" arrives at the store on Day "+(scheduler.getDay()+1));
            System.out.println("First, as a good worker, "+name+" goes ahead to check the mailbox");
            scheduler.sendMessage(new Message("checkMail"));
            System.out.println("All arrived items have been moved into the inventory");
            CheckRegister();
      }
      //His second action
      private void CheckRegister(){
            System.out.println("*****************************************************");
            System.out.println("Now, "+ name+" checks the money in the cash register");
            scheduler.sendMessage(new Message("checkRegister"));
      }
      //This action will be performed, when the store object replies "insufficient money".
      private void GoToBank(){
            System.out.println("*****************************************************");
            scheduler.withdrawMoney();
            scheduler.sendMessage(new Message("checkMoney"));
            System.out.println("The is no enough money. "+ name + " took some money from the bank");
            System.out.println("Bank Debt: "+scheduler.getDebt());
            DoInventory();

      }
      //His third action.
      //Basic logic flow:
      //(1) Create a buffer. Java allows passing by reference when parameters are objects.
      //(2) Send this buffer with a message "checkInventory" to the Store object.
      //(3) Since we are just working on a single-thread program right now, No need to fear of synchronization problems.
      //(4) Once the line "int totalValue = 0" gets executed, buffer has access to the item list.
      //(5) the rest is a piece of cake.
      private void DoInventory(){
            System.out.println("*****************************************************");
            System.out.println(name+" will start checking inventory");
            int totalValue = 0;

            Message pack = new Message();
            pack.setEvent("checkInventory");
            scheduler.sendMessage(pack);

            ArrayList<Item> items = pack.getItems();
            int[] inventory = pack.getIntegerArray();
            for(Item item:items)totalValue += item.purchasePrice;

            for(int x = 0;x<inventory.length;x++){
                  if(inventory[x] == 0){
                        Message grapItemName = new Message("getItemName");
                        grapItemName.setSKU(x);
                        scheduler.sendMessage(grapItemName);
                        System.out.println(grapItemName.getExtraInfo()+" is out of stock");
                        var DidweOrder = scheduler.checkIfOrder(x);
                        if(DidweOrder)System.out.println("But we have already bought some.");
                        else{
                              PlaceAnOrder(x,grapItemName.getExtraInfo());
                        }

                  }
            }

            System.out.println("The total value in our inventory is: "+ totalValue);
            System.out.println("*****************************************************");
            if(scheduler.getDay() == (29)){
                  System.out.println("customer get item name test".toUpperCase(Locale.ROOT));
                  OpenTheStore();
            }
      }


      private void PlaceAnOrder(int sku,String itemName){
            //Randomly generate items
            var newOrder = new ArrayList<Integer>();
            int dayArrived = scheduler.getDay() + random.nextInt(1,4);
            int purchasePrice = random.nextInt(1,51);
            int condition = random.nextInt(1,5);
            int count = 3;

            int totalPayment = purchasePrice * 3;
            Message payment = new Message("payment");
            payment.setExtrainfo(String.valueOf(totalPayment));
            scheduler.sendMessage(payment);

            newOrder.add(sku);
            newOrder.add(dayArrived);
            newOrder.add(purchasePrice);
            newOrder.add(condition);
            newOrder.add(count);
            scheduler.scheduleShipping(newOrder);
      }

      private void removeItem(String ItemName){
           Message message = new Message("removeItem");
           message.setExtrainfo(ItemName);
           scheduler.sendMessage(message);
      }

      private void changeItemListPrice(String ItemName,int amount){
           Message message = new Message("changeListPrice");
           message.setExtrainfo(ItemName);
           message.putEExtrainfo(String.valueOf(amount));
           scheduler.sendMessage(message);
      }

      private void grapAnItem(String name){
           Message message = new Message("grapAnItem");
           message.setExtrainfo(name);
           scheduler.sendMessage(message);
      }

      private void AddOneItem(Item item){
           Message message = new Message("addOneItem");
           message.put(item);
           scheduler.sendMessage(message);
      }

      void OpenTheStore(){
            // TODO: Every time a deal is made, we need to check the inventory and place an order if something runs out of storage.
            // You may choose whatever approach to solve the problem.
            //However, please consider using the message-passing style I have used in my code.
            int buying_customers = random.nextInt(4,10);
            int selling_customers = random.nextInt(1,4);
            Customer test = new Customer();
      }

      void CleanTheStore(){
            // TODO: 2/7/2022 Please implement this.Feel free to change my code if you have another idea.
      }

      void LeaveTheStore(){
            // TODO: 2/7/2022 Please implement this.Feel free to change my code if you have another idea.
      }

      //The implementation of ProjectMessage interface
      //Basic logic flow:
      //The Scheduler class calls this method with a given event.
      //This class calls its method, responding to the event.
      //For more details, please check the sendMessage() method of the scheduler class.
      @Override
      public void receiveMessage(Message message) {
            if(!workToday)return;//A staff member not working today does not respond to any message.
            switch (message.getEvent()) {
                  case "work" -> ArriveAtStore();
                  case "insufficient_money" -> GoToBank();
                  case "sufficient_money" -> DoInventory();
                  case "ask_staff_getItemName"->{
                        message.setEvent("getItemName");
                        scheduler.sendMessage(message);
                        message.setEvent("give_user_itemName");
                        scheduler.sendMessage(message);
                  }
            }
      }
}
