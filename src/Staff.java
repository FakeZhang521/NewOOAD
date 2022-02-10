import java.util.ArrayList;
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
            dayWorked ++;
            System.out.println(name+" arrives at the store on Day "+scheduler.getDay());
            scheduler.sendMessage("checkMail",null);
            CheckRegister();
      }
      //His second action
      private void CheckRegister(){
             scheduler.sendMessage("checkRegister",null);
            System.out.println(name+" checks cash register");
      }
      //This action will be performed, when the store object replies "insufficient money".
      private void GoToBank(){
            scheduler.withdrawMoney();
            scheduler.sendMessage("add_1000",null);
            System.out.println(name + " goes to take some money from the bank");
            DoInventory();

      }
      //His third action.
      //Basic logic flow:
      //(1) Create a buffer. Java allows passing by reference when parameters are objects.
      //(2) Send this buffer with a message "checkInventory" to the Store object.
      //(3) Since we are just working on a single-thread program right now, No need to fear of synchronization problems.
      //(4) Once the line "int totalValue = 0" gets executed, buffer has access to the item list.
      //(5) the rest is a piece of cake.
      // TODO: 2/7/2022 You can replace "item" with "item.name" 
      private void DoInventory(){
<<<<<<< HEAD
            ArrayList<Item> buffer = new ArrayList<>();//This is the buffer I mentioned in (1)
            scheduler.sendMessage("checkInventory",buffer);//(2)
            int[] temStorage = new int[17];
            int totalValue = 0;//(4)
            for (Item item : buffer){
                  temStorage[item.SKU] ++;
                  totalValue += item.purchasePrice;
            }
            for(int x =0;x<temStorage.length;x++){
                  if(temStorage[x] == 0)scheduler.sendMessage("getItemName",);
            }

=======
            final ArrayList<Item> buffer = new ArrayList<>();//This is the buffer I mentioned in (1)
            scheduler.sendMessage("checkInventory",buffer);//(2)
            int totalValue = 0;//(4)
            for (Item item : buffer) {
                  System.out.println(item +" is in our list. Amount: "+item.amount);
                  if(item.purchasePrice<0)throw new IllegalStateException("Error:negative price.");
                  else if (item.amount == 0) {
                        System.out.println(item + " is out of stock");
                        PlaceAnOrder(item.SKU);
                  }
                  totalValue += item.purchasePrice * item.amount;
            }
>>>>>>> 358aede315c514873e12ac418af7356850d43700
            System.out.println(name+" has announced that the total value of inventory is: "+totalValue);
      }


      // TODO: 2/7/2022 Each item should have an identifier "sku".Feel free to change my code if you have another idea.
      private void PlaceAnOrder(int sku){
            scheduler.scheduleShipping(sku);
      }

      void OpenTheStore(){
            // TODO: 2/7/2022 Please implement this.Feel free to change my code if you have another idea.
            // You may choose whatever approach to solve the problem.
            //However, please consider using the message-passing style I have used in my code.
            int buying_customers = random.nextInt(4,10);
            int selling_customers = random.nextInt(1,4);
            while((buying_customers != 0) && (selling_customers != 0)){

            }

      }

      void CleanTheStore(){
            // TODO: 2/7/2022 Please implement this.Feel free to change my code if you have another idea.
      }

      void LeaveTheStore(){
            // TODO: 2/7/2022 Please implement this.Feel free to change my code if you have another idea.
            scheduler.sendMessage("Leaving Store");
      }

      //The implementation of ProjectMessage interface
      //Basic logic flow:
      //The Scheduler class calls this method with a given event.
      //This class calls its method, responding to the event.
      //For more details, please check the sendMessage() method of the scheduler class.
<<<<<<< HEAD
=======
      @Override
>>>>>>> 358aede315c514873e12ac418af7356850d43700
      public void receiveMessage(String event, ArrayList<Item> input) {
            if(!workToday)return;//A staff member not working today does not respond to any message.
            switch (event) {
                  case "work" -> ArriveAtStore();
                  case "insufficient_money" -> GoToBank();
                  case "sufficient_money" -> DoInventory();
            }
      }
}
