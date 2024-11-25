package User;

public abstract class User {
    private String name;
    private int ID;
    private String password;
    private double balance;

    public User(String name, int ID, String password, double balance){
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.balance = balance;
    }

    public String getName(){
        return this.name;
    }
    public int getID(){
        return this.ID;
    }
    public String getPassword(){return this.password;}
    public double getBalance(){
        return this.balance;
    }


    public void setName(String name){
        this.name = name;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public void setPassword(String password){
        this.password = password;
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
