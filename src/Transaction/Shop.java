package Transaction;

import Interface.ManageProduct;
import java.util.HashMap;
import java.util.Map;

public class Shop implements ManageProduct {
    String name;
    int ID;
    Map<Product, Integer> products = new HashMap<>();

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