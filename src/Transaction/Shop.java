package Transaction;

import Other.Category;

import Interface.ManageProduct;
import Other.Utils;

import java.util.*;

public class Shop implements ManageProduct {
    private String name;
    private String ID;
    private Category category;
    private Map<String, Integer> goods;
    private ArrayList<String> bills;


    public Shop(String name, String ID, Category category){
        this.name = name;
        this.ID = ID;
        this.category = category;
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


    public boolean isProductAvailable(String id){
        int amount = this.goods.get(id).intValue();
        if(amount > 0) return true;
        return false;
    }


    @Override
    public void addProduct(Map<String, Integer> list, String id, int amount){
        Integer a = Integer.valueOf(amount);
        list.put(id, a);
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
        int left = this.goods.get(id).intValue() - amount;
        Integer value = Integer.valueOf(left);
        this.goods.put(id, value);
        Utils.writeShopFile(this);
    }
    public void increaseProduct(Map<String, Integer> list, String id, int amount){
        int add = list.get(id).intValue() + amount;
        Integer value = Integer.valueOf(add);
        list.put(id, value);
        Utils.writeShopFile(this);
    }

    public void addToGoods(String id, int amount){
        this.addProduct(this.goods, id, amount);
    }



}