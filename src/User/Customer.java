package User;

import Other.Utils;
import Transaction.Product;
import Interface.ManageProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User implements ManageProduct{
    Map<String, Integer> Cart;
    Map<String, Integer> Purchased;

    public Customer(String name, String password, String ID){
        super(name, ID, password, 0);
        Cart = new HashMap<>();
        Purchased = new HashMap<>();
    }

    public Customer(String name, String ID, String password, double balance){
        super(name, ID, password, balance);
        Cart = new HashMap<>();
        Purchased = new HashMap<>();
    }

    public Map<String, Integer> getCart(){
        return this.Cart;
    }
    public Map<String, Integer> getPurchased(){
        return this.Purchased;
    }

    public void setCart(Map<String, Integer> Cart){
        this.Cart = Cart;
    }
    public void setPurchased(Map<String, Integer> Purchased){
        this.Purchased = Purchased;
    }

    @Override
    public void addProduct(Map<String, Integer> list, String id, int amount){
        Integer a = Integer.valueOf(amount);
        list.put(id, a);
        Utils.writeCustomerFile(this);
    }
    public void removeProduct(Map<String, Integer> list, String id, int amount){
        Integer a = Integer.valueOf(amount);
        list.remove(id);
        Utils.writeCustomerFile(this);
    }
    public void clearProduct(Map<String, Integer> list){
        list.clear();
        Utils.writeCustomerFile(this);
    }
    public void reduceProduct(Map<String, Integer> list, String id, int amount){
        int left = list.get(id).intValue() - amount;
        Integer value = Integer.valueOf(left);
        list.put(id, value);
        Utils.writeCustomerFile(this);
    }
    public void increaseProduct(Map<String, Integer> list, String id, int amount){
        int add = list.get(id).intValue() + amount;
        Integer value = Integer.valueOf(add);
        list.put(id, value);
        Utils.writeCustomerFile(this);
    }

    public void addToCart(String id, int amount){
        this.addProduct(this.Cart, id, amount);
    }
    public void buyProduct(String id, int amount){
        this.addProduct(this.Purchased, id, amount);
        this.removeProduct(this.Cart, id, amount);
    }
    public void buyPartial(String id, int amount){
        this.reduceProduct(Cart, id, amount);
        this.increaseProduct(Purchased, id, amount);
    }

    @Override
    public void addMoney(double amount){
        super.addMoney(amount);
        Utils.writeCustomerFile(this);
    }
    public void substractMoney(double amount){
        super.subtractMoney(amount);
        Utils.writeCustomerFile(this);
    }
}
