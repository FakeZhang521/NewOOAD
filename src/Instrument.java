class Stringed extends Instrument{
    protected String electric;
    
     Stringed(String electric)
    {
        this.electric = electric;
    }
  
    public String getElectric()
    {
        return electric;
    }
    
    public void setElectric(String isElectric)
    {
        electic = isElectric;
    }
}

class Wind extends Instrument{

}
