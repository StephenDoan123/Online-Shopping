package Transaction;

import Other.Utils;

import java.util.*;

public class Sold {
    String ID;
    String name;
    double price;
    Map<String, Integer> purchasedBy;

    public Sold(String name, String ID, double price){
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
        Utils.writeSoldFile(this);
    }
    public int amountOfPurchased(String customerID){
        return purchasedBy.get(customerID);
    }

    public String printPurchased(){
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, Integer> entry: purchasedBy.entrySet()){
            result.append("<").append(entry.getKey()).append(": ").append(entry.getValue()).append("> ");
        }
        return result.toString().trim();
    }

    @Override
    public String toString(){
        return "Name: "+name+" ID: "+ID+" Price: "+price;
    }
}
