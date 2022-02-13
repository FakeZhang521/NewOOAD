class Hat extends Clothing  //example of ENCAPSULATION -> using getters and setters
{
    int hatSize;
    public int getShirtSize() //example of getters and setter -> ENCAPSULATION
    {
        return hatSize;
    }
    
    public void setShirtSize(int size)
    {
        hatSize = size;
    }

}

class Shirt extends Clothing
{
     int shirtSize;
    public int getShirtSize() //example of getters and setter -> ENCAPSULATION
    {
        return shirtSize;
    }

    public void setShirtSize(int size)
    {
        shirtSize = size;
    }
}

class Bandana extends Clothing
{
}


