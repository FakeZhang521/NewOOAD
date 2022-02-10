import java.util.ArrayList;
import java.util.Random;

<<<<<<< HEAD
interface ProjectMessage<E>{
      void sendMessage(String event,E informamtion);
      void receiveMessage(String event,E information);

      private void handler(String event, E information){
             scheduler.messageHandler(event,information);
      }
=======
interface ProjectMessage{
      void receiveMessage(String event,ArrayList<Item> items);
>>>>>>> 358aede315c514873e12ac418af7356850d43700
}
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
                 System.out.println("DAY: "+day);
                 System.out.println("--------------------------------------------");
                 shipToStore();
                 workAssignment();
                 sendMessage("work",null);
                 oneDayPassed();
                 System.out.println("--------------------------------------------");
             }while (day != 30);
      }

<<<<<<< HEAD
      static void messageHandler<E>(String event,E ){

      }
    public
    //This function adds the correct item to the waiting queue.
=======
      //This function adds the correct item to the waiting queue.
>>>>>>> 358aede315c514873e12ac418af7356850d43700
      static void shipToStore(){
            for(ArrayList<Integer> x:waitingQue){
                if(x.get(1) == day){
                    store.get(0).mailBox.add(x);
                }
            }
            waitingQue.removeIf(entry->entry.get(1) == day);

      }

<<<<<<< HEAD
=======
      static void sendMessage(String event,ArrayList<Item> items){
            for (ProjectMessage tem : store) {
                  tem.receiveMessage(event,items);
            }
            for (ProjectMessage tem : staff) {
                  tem.receiveMessage(event,items);
            }
      }

>>>>>>> 358aede315c514873e12ac418af7356850d43700
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
      static void scheduleShipping(int sku){
          for(ArrayList<Integer> x:waitingQue){
<<<<<<< HEAD
=======

>>>>>>> 358aede315c514873e12ac418af7356850d43700
              if(x.get(0) == sku)return;
          }
           int dayArrived = day + random.nextInt(1,4);
           int purchasePrice = random.nextInt(1,51);
           var newOrder = new ArrayList<Integer>();
           newOrder.add(sku);
           newOrder.add(dayArrived);
           newOrder.add(purchasePrice);
           waitingQue.add(newOrder);
      }

      static void oneDayPassed(){
          if(day == 30)return;
          day ++;
      }
      static int getDay(){
          return day;
      }
      static void withdrawMoney(){
          bankMoney += 1000;
      }
}
