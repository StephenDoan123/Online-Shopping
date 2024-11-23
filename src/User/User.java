package User;

public abstract class User {
    private String name;
    private int ID;
    private double balance;

    public User(String name, int ID, double balance){
        this.name = name;
        this.ID = ID;
        this.balance = balance;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getID(){
        return this.ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public double getBalance(){
        return this.balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }

    public void addMoney(double amount){
        this.balance += amount;
    }
    public void substractMoney(double amount){
        this.balance -= amount;
    }
}
