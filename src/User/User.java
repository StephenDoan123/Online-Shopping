package User;

public abstract class User {
    private String name;
    private String ID;
    private String password;
    private double balance;

    public User(String name, String ID, String password, double balance){
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.balance = balance;
    }

    public String getName(){
        return this.name;
    }
    public String getID(){
        return this.ID;
    }
    public String getPassword(){return this.password;}
    public double getBalance(){return this.balance;}


    public void setName(String name){
        this.name = name;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }


    public boolean hasMoney(double amount){
        if(amount<0) return false;
        return this.balance >= amount;
    }
    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }

    public void addMoney(double amount){
        this.balance += amount;
    }
    public void subtractMoney(double amount){
        this.balance -= amount;
    }
}
