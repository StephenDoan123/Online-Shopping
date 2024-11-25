package User;

import Transaction.Product;
import Interface.ManageProduct;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User implements ManageProduct{
    Map<Integer, Integer> Cart;
    Map<Integer, Integer> Purchased;

    public Customer(){
        super(" ", 000000, " ", 0);
    }

    public Customer(String name, int ID, String password, double balance){
        super(name, ID, password, balance);
        Cart = new HashMap<Integer, Integer>();
        Purchased = new HashMap<Integer, Integer>();
    }

    public Map<Integer, Integer> getCart(){
        return this.Cart;
    }
    public Map<Integer, Integer> getPurchased(){
        return this.Purchased;
    }

    public void setCart(Map<Integer, Integer> Cart){
        this.Cart = Cart;
    }
    public void setPurchased(Map<Integer, Integer> Purchased){
        this.Purchased = Purchased;
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

    public void addToCart(Product p, int amount){
        this.addProduct(this.Cart, p.getID(), amount);
    }
    public void addToPurchased(int id, int amount){
        this.addProduct(this.Purchased, id, amount);
        this.removeProduct(this.Cart, id, amount);
    }
}
