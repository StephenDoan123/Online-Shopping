package User;

import Transaction.Shop;

import java.util.ArrayList;

public class Seller extends User{
    ArrayList<Integer> shops;

    public Seller(){
        super(" ", 000000, " ", 0);
    }

    public Seller(String name, int ID, String password, double balance){
        super(name, ID, password, balance);
        shops = new ArrayList<>();
    }

}
