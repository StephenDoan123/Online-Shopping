package Transaction;

public class Product{
    String name;
    int ID;
    float price;
    int code;

    public Product(){
        this.name = " ";
        this.ID = 000000;
        this.price = 0;
        this.code = 0;
    }

    public Product(String name, int ID, float price, String category, int code){
        this.name = name;
        this.ID = ID;
        this.price = price;
        this.code = code;
    }

    public String getName(){
        return this.name;
    }
    public int getID(){
        return this.ID;
    }
    public float getPrice(){
        return this.price;
    }
    public int getCode(){
        return this.code;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public void setPrice(float price){
        this.price = price;
    }
    public void setCode(int code){
        this.code = code;
    }

}