package Transaction;

import Other.Utils;

import java.util.*;

public class Bill {
    String ID;
    String name;
    float price;
    Map<String, Integer> purchasedBy;

    public Bill(String name, String ID, float price){
        this.name = name;
        this.ID = ID;
        this.price = price;
        purchasedBy = new HashMap<>();
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
    public Map<String, Integer> getPurchasedBy(){return this.purchasedBy;}

    public void setName(String name){
        this.name = name;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public void setPrice(float price){
        this.price = price;
    }
    public void setPurchasedBy(Map<String, Integer> purchasedBy){this.purchasedBy = purchasedBy;}


    public void updateBill(String customerID, int amount){
        this.purchasedBy.put(customerID, amount);
        Utils.writeBillFile(this);
    }
}
