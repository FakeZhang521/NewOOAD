import java.util.ArrayList;
import java.util.Random;

class Staff{
      private final String name;
      int dayWorked;
      boolean workToday = false;
      private static final Random random = new Random(System.currentTimeMillis());
      Staff(String name){
            this.name = name;
            dayWorked = 0;
      }

      //A staff member's initial action.
      private void ArriveAtStore(){
            dayWorked ++;
            System.out.println(name+" arrives at the store on Day "+(scheduler.getDay()+1));
            System.out.println("First, as a good worker, "+name+" goes ahead to check the mailbox");
            scheduler.sendMessage(new Message("checkMail"));
            System.out.println("All arrived items have been moved into the inventory");
            System.out.println();
            CheckRegister();
      }
      //His second action
      private void CheckRegister(){
            System.out.println();
            System.out.println("Now, "+ name+" checks the money in the cash register");
            System.out.println();
            Message message =new Message("checkRegister");
            scheduler.sendMessage(message);
            if(message.getExtraInfo().equals("doNotNeedMoney"))DoInventory();
            else if(message.getExtraInfo().equals("needMoney")){
                  GoToBank();
                  DoInventory();
            }
            else{
                  throw new IllegalStateException("Unknown state:CheckRegister function");
            }
      }
      //This action will be performed, when the store object replies "insufficient money".
      private void GoToBank(){
            System.out.println();
            System.out.println("The is no enough money. "+ name + " took some money from the bank");
            scheduler.withdrawMoney();
            System.out.println("Bank Debt: "+scheduler.getDebt());
            System.out.println();
      }
      //His third action.
      //Basic logic flow:
      //(1) Create a buffer. Java allows passing by reference when parameters are objects.
      //(2) Send this buffer with a message "checkInventory" to the Store object.
      //(3) Since we are just working on a single-thread program right now, No need to fear of synchronization problems.
      //(4) Once the line "int totalValue = 0" gets executed, buffer has access to the item list.
      //(5) the rest is a piece of cake.
      private void DoInventory(){
            int totalValue = 0;
            System.out.println();
            System.out.println(name+" will start checking inventory");
            Message pack = new Message();
            pack.setEvent("checkInventory");
            scheduler.sendMessage(pack);

            ArrayList<Item> items = pack.getItems();
            int[] inventory = pack.getIntegerArray();
            for(Item item:items)totalValue += item.purchasePrice;
            scheduler.sendMessage(new Message("printInventory"));

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
            System.out.println();
            OpenTheStore();
      }


      private void PlaceAnOrder(int sku,String itemName){
            //Randomly generate items
            System.out.println("place order for: "+itemName);
            var newOrder = new ArrayList<Integer>();
            int dayArrived = scheduler.getDay() + random.nextInt(1,4);
            int purchasePrice = random.nextInt(1,30);
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
      //A customer object now can communicate with a store object.
      void OpenTheStore(){
            // TODO: Every time a deal is made, we need to check the inventory and place an order if something runs out of storage.
            System.out.println("====================================================================================");
            // You may choose whatever approach to solve the problem.
            //However, please consider using the message-passing style I have used in my code.
            //A test
            Message message = new Message("viewCustomerLine");
            ArrayList<Customer> customers = message.viewCustomers();
            int sellodds;
            int condmod;
            double price;
            boolean pricechange=false;
            boolean sold=false;
            Item item;
            int count=1;
            for(Customer customer:customers){
                  sellodds=random.nextInt(1, 2);
                  if(customer.type=="buying"){
                  }
                  else if(customer.type=="selling"){
                        item=customer.item;
                        switch(item.condition){
                              case 1: condmod=random.nextInt(1, 2); break;
                              case 2: condmod=random.nextInt(2, 4); break;
                              case 3: condmod=random.nextInt(4, 7); break;
                              case 4: condmod=random.nextInt(5, 10); break;
                              case 5: condmod=random.nextInt(7, 15); break;
                              default: condmod=0;
                        }
                        price=random.nextInt(1,20)+condmod;
                        if(sellodds==1){
                              sold=true;
                              pricechange=false;
                        }
                        else if(sellodds==2){
                              sellodds=random.nextInt(1,4);
                              if(sellodds==4){
                                    sold=false;
                              }
                              else{
                                    sold=true;
                                    pricechange=true;
                                    price=price*(0.9);
                              }
                        }
                        if(sold){
                           item.purchasePrice=price;
                           item.listPrice=2*price;
                           if(pricechange){
                                 System.out.println(name+"bought a "+item.type+" in "+item.condition+" from Customer"+count+"at a 10% increase for $"+price);
                           }
                           else if (!pricechange){
                                 System.out.println(name+"bought a "+item.type+" in "+item.condition+" from Customer"+count+" for $"+price);
                           }
                           goods.
                        }
                        else if(!sold){
                              System.out.println(name+"bought a "+item.type+" in "+item.condition+" from Customer"+count+" for $"+price);
                        }
                        customers.remove(count);
                        count++;

                  }
            }

            System.out.println("Store is open for customers");
            System.out.println("====================================================================================");
            CleanTheStore();
      }

      private void CleanTheStore(){
            System.out.println();
            System.out.println("Eight hours has passed. "+name+" starts to clean the store and prepare to go home\n");
            Message message = new Message("grabAllGoods");
            scheduler.sendMessage(message);
            ArrayList<Item> items = message.getItems();
            items.forEach(item -> {
                  boolean damage_happen;
                  switch (name){
                        case "Shaggy"-> damage_happen = random.nextInt(0, 101) <= 20;
                        case "Velma"-> damage_happen = random.nextInt(0, 101) <= 5;
                        default -> throw new IllegalStateException("Empty name");
                  }
                  if(damage_happen){
                        item.condition --;
                        item.listPrice = Math.round(item.listPrice*0.8);
                        System.out.println("Unfortunately "+name+ " has damaged "+item.name);
                        if(item.condition <=0){
                              System.out.println("Item: "+item.name+" has broken");
                        }
                        else{
                              System.out.println("The new sate of "+item.name+" is (list price: "+item.listPrice+",condition: "+item.getCondition()+")");
                        }
                  }
            });
            scheduler.sendMessage(new Message("removeBrokenItem"));
            System.out.println();
            scheduler.sendMessage(new Message("printInventory"));
            LeaveTheStore();
      }

      private void LeaveTheStore(){
            System.out.println(name +"locked the door and went back home");

      }

      //The implementation of ProjectMessage interface
      //Basic logic flow:
      //The Scheduler class calls this method with a given event.
      //This class calls its method, responding to the event.
      //For more details, please check the sendMessage() method of the scheduler class.
      public void receiveMessage(Message message) {
            if(!workToday)return;//A staff member not working today does not respond to any message.
            switch (message.getEvent()) {
                  case "work" -> ArriveAtStore();
                  case "gotoBank" -> GoToBank();
                  case "gotoDoInventory" -> DoInventory();
                  case "gotoCheckRegister"->CheckRegister();
                  case "gotoOpenStore"->OpenTheStore();
                  case "gotoCleanStore"->CleanTheStore();
                  case "gotoLeaveTheStore"->LeaveTheStore();
            }
      }
}
