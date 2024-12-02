package Transaction;

import Other.Category;

import Interface.ManageProduct;
import Other.Utils;

import java.util.*;

public class Shop implements ManageProduct {
    private String name;
    private String ID;
    private Category category;
    private double balance;
    private Map<String, Integer> goods;
    private ArrayList<String> bills;


    public Shop(String name, String ID){
        this.name = name;
        this.ID = ID;
        goods = new HashMap<>();
    }
    public Shop(String name, String ID, Category category, double balance){
        this.name = name;
        this.ID = ID;
        this.category = category;
        this.balance = balance;
        goods = new HashMap<>();
    }

    public String getName(){
        return this.name;
    }
    public String getID(){
        return this.ID;
    }
    public Category getCategory(){
        return this.category;
    }
    public Map<String, Integer> getGoods(){
        return this.goods;
    }
    public ArrayList<String> getBills(){
        return this.bills;
    }
    public double getBalance(){
        return this.balance;
    }


    public void setName(String name){
        this.name = name;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public void setCategory(Category category){
        this.category = category;
    }
    public void setGoods(Map<String, Integer> goods){
        this.goods = goods;
    }
    public void setBills(ArrayList<String> bills){
        this.bills = bills;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public void addMoney(double amount){
        this.balance += amount;
    }
    public void subtractMoney(double amount){
        this.balance -= amount;
    }


    public int amountProduct(String id){
        return goods.get(id).intValue();
    }


    //====================================== Manage Product =============================================
    @Override
    public void addProduct(Map<String, Integer> list, String id, int amount){
        Integer a = Integer.valueOf(amount);
        list.put(id, list.getOrDefault(id, 0)+amount);
        Utils.writeShopFile(this);
    }

    public void removeProduct(Map<String, Integer> list, String id, int amount){
        Integer a = Integer.valueOf(amount);
        list.remove(id);
        Utils.writeShopFile(this);
    }
    public void clearProduct(Map<String, Integer> list){
        list.clear();
        Utils.writeShopFile(this);
    }
    public void reduceProduct(Map<String, Integer> list, String id, int amount){
        int left = list.get(id).intValue() - amount;
        list.put(id, left);
        Utils.writeShopFile(this);
    }

    public void addToGoods(String id, int amount){
        this.addProduct(this.goods, id, amount);
    }
    public void sellProduct(String id, int amount){
        this.reduceProduct(goods, id, amount);
    }
    public void updateBill(String ID, String customerID, int amount){
        if(!Utils.hasAccount(ID, "Bill")){
            Product product = Utils.readProductFile(ID);
            Bill bill = new Bill(product.getName(), product.getID(), product.getPrice());
            Utils.writeBillFile(bill);
        }
        Bill bill = Utils.readBillFile(ID);
        bill.updateCustomer(customerID, amount);
        bills.add(bill.getID());
    }
}