package User;

import Transaction.Shop;

import java.util.ArrayList;

public class Seller extends User{
    ArrayList<Integer> shops;

    public Seller(){
        super(" ", " ", " ", 0);
        shops = new ArrayList<>();
    }

    public Seller(String name, String ID, String password, double balance){
        super(name, ID, password, balance);
        shops = new ArrayList<>();
    }

}