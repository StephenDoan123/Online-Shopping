package Transaction;

public class Product{
    String name;
    String shopID;
    String ID;
    double price;

    public Product(String name,String shopID, String ID, float price){
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.shopID = shopID;
    }

    public String getName(){
        return this.name;
    }
    public String getID(){
        return this.ID;
    }
    public double getPrice(){
        return this.price;
    }
    public String getShopID(){return this.shopID;}

    public void setName(String name){
        this.name = name;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setShopID(String shopID){this.shopID = shopID;}

    @Override
    public String toString(){
        return "Product: "+name+ " - Price: "+price+"\n";
    }

}
