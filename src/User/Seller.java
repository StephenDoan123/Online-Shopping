package User;

import Transaction.Shop;

import java.util.ArrayList;

public class Seller extends User{
    ArrayList<Shop> shops;

    public Seller(){
        super(" ", 000000, 0);
    }

    public Seller(String name, int ID, double balance){
        super(name, ID, balance);
    }

}
