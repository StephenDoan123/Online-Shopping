package User;

import Transaction.Product;
import Interface.ManageProduct;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User implements ManageProduct{
    Map<Product, Integer> Cart;
    Map<Product, Integer> Purchased;

    public Customer(){
        super(" ", 000000, 0);
    }

    public Customer(String name, int ID, double balance){
        super(name, ID, balance);
    }

    @Override
    public void addProduct(Map<Product, Integer> list, Product p, int amount){
        Integer a = Integer.valueOf(amount);
        list.put(p, a);
        System.out.println("Add product successfully!");
    }

    public void removeProduct(Map<Product, Integer> list, Product p, int amount){
        if(list.remove(p) == null){
            System.out.println("There is no product in the cart!");
        }
        else{
            System.out.println("Remove product successfully!");
        }
    }
}
