package Transaction;

public class Product{
    String name;
    String ID;
    float price;

    public Product(String name, String ID, float price){
        this.name = name;
        this.ID = ID;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }
    public String getID(){
        return this.ID;
    }
    public float getPrice(){
        return this.price;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public void setPrice(float price){
        this.price = price;
    }

}
