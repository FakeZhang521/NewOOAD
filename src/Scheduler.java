import java.util.ArrayList;
import java.util.Random;

class scheduler{
      private static final Random random = new Random(System.currentTimeMillis());
      private static int bankMoney = 0;
      private static int day = 0;
      private static final ArrayList<Store> store = new ArrayList<>();
      private static final ArrayList<Staff> staff = new ArrayList<>();
      private static final ArrayList<ArrayList<Integer>> waitingQue = new ArrayList<>();

      static void startEmulation() throws IllegalAccessException {
             Store store = new Store();
             Staff clerk1 = new Staff("Shaggy");
             Staff clerk2 = new Staff("Velma");
             scheduler.store.add(store);
             scheduler.staff.add(clerk1);
             scheduler.staff.add(clerk2);

             do{
                 // TODO: 2/7/2022 Main loop
                 System.out.print("\n\n\n");
                 System.out.println("=========================================================================================");
                 System.out.println("DAY: "+(day+1));
                 shipToStore();
                 if((day+1)%7==0){
                     System.out.println("The store is closed today");
                     oneDayPassed();
                     continue;
                 }
                     workAssignment();
                     sendMessage(new Message("work"));
                     oneDayPassed();
                     System.out.println("=========================================================================================");
             }while (day != 30);

            summary();
      }

      private static void summary(){
             System.out.println("\n\n\n\t\t\tSUMMARY\t\t\t\t");
             sendMessage(new Message("printInventory"));
             sendMessage(new Message("printSoldItem"));
             sendMessage(new Message("printCash"));
             System.out.println("Band debt is: "+ bankMoney);
      }
      static void shipToStore(){
            for(ArrayList<Integer> x:waitingQue){
                if(x.get(1) == day){
                    store.get(0).mailBox.add(x);
                }
            }
             waitingQue.removeIf(entry->entry.get(1) == day);
          }

      static void sendMessage(Message message){
           store.forEach(store->store.receiveMessage(message));
           staff.forEach(staff->staff.receiveMessage(message));
      }

      //Determines which staff to work.
      private static void workAssignment() throws IllegalAccessException {
          if(staff.size() == 0)throw new IllegalAccessException("Staff list cannot be zero");
          while(true){
              int whoToWork = random.nextInt(0,staff.size());
              Staff tem = staff.get(whoToWork);
              if(tem.dayWorked<3){
                  tem.workToday = true;
                  staff.set(whoToWork,tem);
                  staff.forEach(member->{
                      if(!member.equals(tem))member.workToday = false;
                  });
                  break;
              }
          }
          staff.forEach(member->{if(member.dayWorked >= 3)member.dayWorked = 0;});
      }

      static ArrayList<Customer> createCustomers(){
          int buyer = random.nextInt(4,10);
          int seller = random.nextInt(1,5);
          ArrayList<Customer> customers = new ArrayList<>();
          for(int i=0;i<buyer;i++){
              customers.add(new Customer("buying"));
          }
          for(int i =0;i<seller;i++){
              customers.add(new Customer("selling"));
          }
          return customers;
      }
     //Schedule shipping
      static void scheduleShipping(ArrayList<Integer> orderInfo){
          for(ArrayList<Integer> x:waitingQue){
              if(x.get(0).equals(orderInfo.get(0)))return;
          }
          waitingQue.add(orderInfo);
      }
      static Boolean checkIfOrder(int sku){
          for(ArrayList<Integer> x:waitingQue){
              if(x.get(0).equals(sku))return true;
          }
          return false;
      }
      static void oneDayPassed(){
          if(day == 30)return;
          day ++;
      }
      static int getDebt(){
          return bankMoney;
      }
      static int getDay(){
          return day;
      }
      static void withdrawMoney(){
          bankMoney += 1000;
          sendMessage(new Message("add_1000"));
      }
}
