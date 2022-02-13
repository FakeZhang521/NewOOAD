import java.util.ArrayList;

class Message { //implements POLYMORPHISM
     private int SKU;
     private String event;
     private ArrayList<Item> items;
     private int[] numbers;
     private String extrainfo;
     private String eextrainfo;
    private ArrayList<Customer> customers;


     Message(){
         SKU = 0;
         event = "";
         numbers = new int[17];
         items = new ArrayList<>();
         extrainfo = "";
     }

     Message(String event){
         this.event = event;
         numbers = new int[17];
         items = new ArrayList<>();
     }

     Message(String event,int[] integerArray,ArrayList<Item> items){
         numbers = integerArray;
         this.event = event;
         this.items = items;
     }

    void setEvent(String event){
         this.event = event;
    }

    void setSKU(int number){
        SKU = number;
    }
    void setExtrainfo(String info){
         extrainfo = info;
    }

     void put(String event,int[] integerArray){
         this.event = event;
         numbers = integerArray;
     }
     void put(String event,ArrayList<Item> items){
          this.event = event;
          this.items = items;
     }

     void put(int[] integerArray){
          numbers = integerArray;
     }
     void put(ArrayList<Item> items){
         this.items = items;
     }
     void putEExtrainfo(String input){
          eextrainfo= input;
     }
     void put(Item input){
         items.add(input);
     }

     Item getItem(int index){
         return items.get(index);
     }

     int[] getIntegerArray(){
         return numbers;
     }

     ArrayList<Item> getItems(){
         return items;
     }

     int getSKU(){
         return SKU;
     }
     String getExtraInfo(){
         return extrainfo;
     }
     String getEExtrainfo(){
         return eextrainfo;
     }
     String getEvent(){
         return event;
     }

     void clear(){
         numbers = null;
         items = null;
     }

    ArrayList<Customer> viewCustomers(){
        return customers;
    }


}
