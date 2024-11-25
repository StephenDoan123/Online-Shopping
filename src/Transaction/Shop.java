package Transaction;

import Other.Category;

import Interface.ManageProduct;
import java.util.HashMap;
import java.util.Map;

public class Shop implements ManageProduct {
    private String name;
    private int ID;
    private Category category;
    private Map<Integer, Integer> goods;


    public Shop(String name, int ID, Category category){
        this.name = name;
        this.ID = ID;
        this.category = category;
        goods = new HashMap<>();
    }

    public String getName(){
        return this.name;
    }
    public int getID(){
        return this.ID;
    }
    public Category getCategory(){
        return this.category;
    }
    public Map<Integer, Integer> getGoods(){
        return this.goods;
    }


    public void setName(String name){
        this.name = name;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public void setCategory(Category category){
        this.category = category;
    }
    public void setGoods(Map<Integer, Integer> goods){
        this.goods = goods;
    }


    @Override
    public void addProduct(Map<Integer, Integer> list, int id, int amount){
        Integer a = Integer.valueOf(amount);
        Integer ID = Integer.valueOf(id);
        list.put(ID, a);
    }

    public void removeProduct(Map<Integer, Integer> list, int id, int amount){
        Integer a = Integer.valueOf(amount);
        Integer ID = Integer.valueOf(id);
        list.remove(ID);
    }
    public void clearProduct(Map<Integer, Integer> list){
        list.clear();
    }

}