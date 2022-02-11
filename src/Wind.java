class Flute extends Wind{
    protected String type; //example of ABSTRACTION
    public String getType() //example of getters and setter -> ENCAPSULATION
    {
        return type;
    }
}

class Harmonica extends Wind{
    protected String key;
    public String getKey() //example of getters and setter -> ENCAPSULATION
    {
        return Key;
    }

    public void setKey(String inKey)
    {
        key = inKey;
    }
}
