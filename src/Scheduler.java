import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

interface ProjectMessage{
    void receiveMessage(Message message);
}

class scheduler{
      private static final Random random = new Random(System.currentTimeMillis());
      private static int bankMoney = 0;
      private static int day = 0;
      private static final ArrayList<Store> store = new ArrayList<>();
      private static final ArrayList<Staff> staff = new ArrayList<>();
      private static final ArrayList<ArrayList<Integer>> waitingQue = new ArrayList<>();
      private static int state;

      static void startEmulation() throws IllegalAccessException {
             Store store = new Store();
             Staff clerk1 = new Staff("Shaggy");
             Staff clerk2 = new Staff("Velma");
             scheduler.store.add(store);
             scheduler.staff.add(clerk1);
             scheduler.staff.add(clerk2);

             do{
                 // TODO: 2/7/2022 Main loop
                 System.out.println("DAY: "+(day+1));
                 System.out.println("--------------------------------------------");
                 shipToStore();
                 System.out.println("Hello!");
                 workAssignment();
                 sendMessage(new Message("work"));
                 oneDayPassed();
                 System.out.println("--------------------------------------------");
             }while (day != 30);
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
      }
}
