package User;

import Other.Utils;
import Transaction.Shop;

import java.util.ArrayList;

public class Seller extends User{
    ArrayList<String> shops;

    public Seller(String name, String ID, String password){
        super(name, ID, password, 0);
        shops = new ArrayList<>();
    }

    public Seller(String name, String ID, String password, double balance){
        super(name, ID, password, balance);
        shops = new ArrayList<>();
    }

    public ArrayList<String> getShops(){
        return this.shops;
    }
    public void setShops(ArrayList<String> shops){
        this.shops = shops;
    }

    @Override
    public void addMoney(double amount){
        super.addMoney(amount);
        Utils.writeSellerFile(this);
    }
    public void subtractMoney(double amount){
        super.subtractMoney(amount);
        Utils.writeSellerFile(this);
    }
}