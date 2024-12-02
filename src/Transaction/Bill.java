package Transaction;

import Other.Utils;

import java.util.*;

public class Bill {
    String ID;
    String name;
    double price;
    Map<String, Integer> purchasedBy;

    public Bill(String name, String ID, double price){
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
    public double getPrice(){
        return this.price;
    }
    public Map<String, Integer> getPurchasedBy(){return this.purchasedBy;}

    public void setName(String name){
        this.name = name;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setPurchasedBy(Map<String, Integer> purchasedBy){this.purchasedBy = purchasedBy;}


    public void updateCustomer(String customerID, int amount){
        purchasedBy.put(customerID, purchasedBy.getOrDefault(customerID, 0)+amount);
        Utils.writeBillFile(this);
    }
    public int amountOfPurchased(String customerID){
        int amount =  purchasedBy.get(customerID);
        return amount;
    }
}
