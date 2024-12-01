package Menu;

import Other.Utils;
import User.Customer;
import User.Seller;
import Transaction.Shop;
import Transaction.Product;


import java.util.*;

public class CustomerMenu {
    private Customer activeCustomer;
    private Scanner scan = new Scanner(System.in);

    public CustomerMenu(Customer customer){
        activeCustomer = customer;
    }

    public void displayAuthMenu(){
        String choice;
        while(true){
            System.out.println("------------ Customer ------------");
            System.out.println("(1) Register");
            System.out.println("(2) Login");
            System.out.println("(3) Exit");
            choice = scan.next();
            if(choice == "1"){
                displayRegisterMenu();
                return;
            }
            if(choice == "2"){
                displayLoginMenu();
                return;
            }
            if(choice == "3"){
                return;
            }
        }
    }

    public void displayRegisterMenu(){
        Utils.clearScreen();
        String ID = Utils.generateID();
        String name = "";
        String password = "";
        while(name.length()==0){
            System.out.println("------------ Register -------------");
            System.out.println("ID:       "+ID);
            System.out.println("Name: ------------");
            System.out.println("Password: --------");
            System.out.println("----------- <Input name> ----------");
            System.out.println("-----------------------------------");
            name = scan.next();
        }
        while(password.length()==0){
            Utils.clearScreen();
            System.out.println("------------ Register -------------");
            System.out.println("ID:       "+ID);
            System.out.println("Name:     "+name);
            System.out.println("Password: --------");
            System.out.println("--------- <Input password> --------");
            System.out.println("-----------------------------------");
            password = scan.next();
        }
        Customer customer = new Customer(name, ID, password);
        Utils.writeCustomerFile(customer);
        activeCustomer = customer;
        displayAfterAuthMenu();
    }

    public void displayLoginMenu(){
        Utils.clearScreen();
        String ID = "null";
        while(!Utils.hasAccount(ID, "Data/Customer/")){
            Utils.clearScreen();
            System.out.println("-------------- Login ---------------");
            System.out.println("ID: ----------");
            System.out.println("Password: ----");
            System.out.println("------------- <Input ID> -----------");
            ID = scan.next();
        }
        Customer customer = Utils.readCustomerFile(ID);
        String password = "";
        while(!customer.isCorrectPassword(password)){
            Utils.clearScreen();
            System.out.println("-------------- Login ---------------");
            System.out.println("ID:   "+ID);
            System.out.println("Password: ----");
            System.out.println("---------- <Input password> --------");
            password = scan.next();
            if(!customer.isCorrectPassword(password)){
                displayAuthMenu();
                return;
            }
        }
        activeCustomer = customer;
        displayAfterAuthMenu();
    }

    public void displayAfterAuthMenu(){
        String choice;
        while(true){
            Utils.clearScreen();
            System.out.println("---------- Customer Menu ----------");
            System.out.println("(1) Mall");
            System.out.println("(2) Cart");
            System.out.println("(3) Information");
            System.out.println("(4) Log out");
            System.out.println("-----------------------------------");
            choice = scan.next();
            if(choice == "1"){
                displayMallMenu();
                return;
            }
            if(choice == "2"){
                displayCartMenu();
                return;
            }
            if(choice == "3"){
                displayInformationMenu();
                return;
            }
            if(choice == "4"){
                Utils.writeCustomerFile(activeCustomer);
                activeCustomer = null;
                displayAuthMenu();
                return;
            }
        }
    }

    public void displayMallMenu(){
        Utils.clearScreen();
        String choice;
        while(true){
            System.out.println("--------------- Mall --------------");
            System.out.println("(1) View category");
            System.out.println("(2) Search product");
            System.out.println("(3) Exit");
            System.out.println("-----------------------------------");
            choice = scan.next();
            if(choice == "1"){
                displayCategory();
                return;
            }
            if(choice == "2"){
                displaySearchMenu();
                return;
            }
            if(choice == "3"){
                displayAfterAuthMenu();
                return;
            }
        }
    }

    public void displayCategory(){
        Utils.clearScreen();
        String choice;
        while(true){
            System.out.println("------------ Category -------------");
            System.out.println("(1) Sports");
            System.out.println("(2) Electronics");
            System.out.println("(3) Clothes");
            System.out.println("(4) Foods");
            System.out.println("(5) Exit");
            System.out.println("------------------------------------");
            choice = scan.next();
            if(choice == "1"){
                displayProductCategory("SPORTS");
                return;
            }
            if(choice == "2"){
                displayProductCategory("ELECTRONICS");
                return;
            }
            if(choice == "3"){
                displayProductCategory("CLOTHES");
                return;
            }
            if(choice == "4"){
                displayProductCategory("FOOD");
                return;
            }
            if(choice == "5"){
                displayMallMenu();
                return;
            }
        }
    }

    public void displayProductCategory(String category){
        Utils.clearScreen();
        //==================== Duyệt qua các file shop --> đọc file product
        ArrayList<Shop> shops = Utils.showCategory(category);
        Map<Product, Integer> products = new HashMap<>();
        for(int i = 0; i <shops.size(); i++){
            for(Map.Entry<String, Integer> entry: shops.get(i).getGoods().entrySet()){
                Product product = Utils.readProductFile(entry.getKey());
                Integer amount = entry.getValue();
                products.put(product, amount);
            }
        }
        //=================== Display product
        String choice;
        while(true){
            System.out.println("-------- "+category+" --------");
            int index = 1;
            for(Map.Entry<Product, Integer> entry: products.entrySet()){
                Product product = entry.getKey();
                int amount = entry.getValue();
                System.out.println(index+". "+product+" - Available: "+amount);
                index++;
            }
            System.out.println("0. Exit");
            System.out.print("Selection: ");
            choice = scan.next();
            int selection = Integer.parseInt(choice);
            if(selection == 0){
                break;
            }
            else if(selection > 0 && selection <= products.size()){
                Product selectedProduct = (Product) products.keySet().toArray()[selection-1];
                int availableQuantity = products.get(selectedProduct);

                System.out.print("Amount: ");
                int quantity = scan.nextInt();

                if(quantity > 0 && quantity <= availableQuantity){
                    activeCustomer.addToCart(selectedProduct.getID(), quantity);
                    Shop shop = Utils.readShopFile(selectedProduct.getShopID());
                    shop.reduceProduct(shop.getGoods(), selectedProduct.getID(), quantity);
                    System.out.println("Added "+quantity+" "+selectedProduct.getName()+" to the cart!");
                }
                else{
                    System.out.println("Invalid amount!");
                }
            }
            else{
                System.out.println("Invalid choice!");
            }
        }
        displayCategory();
    }

    public void displaySearchMenu(){
        Utils.clearScreen();
        String choice;
        while(true){
            System.out.println("---------- Search ----------");
            System.out.println("Enter product: ");

        }
    }

    public void displayCartMenu(){

    }

    public void displayInformationMenu(){

    }
}
