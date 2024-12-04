package Menu;

import Other.Utils;
import User.Customer;
import User.Seller;
import Transaction.Shop;
import Transaction.Product;
import Transaction.Bill;


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
            Utils.clearScreen();
            System.out.println("------------ Customer ------------");
            System.out.println("(1) Register");
            System.out.println("(2) Login");
            System.out.println("(3) Exit");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                displayRegisterMenu();
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displayLoginMenu();
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                return;
            }
        }
    }

    public void displayRegisterMenu(){
        String ID = Utils.generateID();
        String name = "";
        String password = "";
        while(name.isEmpty()){
            Utils.clearScreen();
            System.out.println("------------ Register -------------");
            System.out.println("ID:       "+ID);
            System.out.println("Name: ------------");
            System.out.println("Password: --------");
            System.out.println("----------- <Input name> ----------");
            System.out.println("-----------------------------------");
            name = scan.next();
        }
        while(password.isEmpty()){
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
            if(choice.equalsIgnoreCase("1")){
                displayMallMenu();
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displayCartMenu();
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                displayInformationMenu();
                return;
            }
            if(choice.equalsIgnoreCase("4")){
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
            if(choice.equalsIgnoreCase("1")){
                displayCategory();
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displaySearchMenu();
                return;
            }
            if(choice.equalsIgnoreCase("3")){
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
            if(choice.equalsIgnoreCase("1")){
                displayProductCategory("SPORTS");
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displayProductCategory("ELECTRONICS");
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                displayProductCategory("CLOTHES");
                return;
            }
            if(choice.equalsIgnoreCase("4")){
                displayProductCategory("FOOD");
                return;
            }
            if(choice.equalsIgnoreCase("5")){
                displayMallMenu();
                return;
            }
        }
    }

    public void displayProductCategory(String category){
        Utils.clearScreen();
        String choice;
        ArrayList<Shop> shops = Utils.findShopsByCategory(category);
        Map<Product, Integer> products = new HashMap<>();

        //==================== Duyệt qua các file shop --> đọc file product
        for (Shop shop : shops) {
            for (Map.Entry<String, Integer> entry : shop.getGoods().entrySet()) {
                Product product = Utils.readProductFile(entry.getKey());
                Integer amount = entry.getValue();
                products.put(product, amount);
            }
        }
        //=================== Display product
        if(!products.isEmpty()) {
            while (true) {
                System.out.println("-------- " + category + " --------");
                int index = 1;
                for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    int amount = entry.getValue();
                    System.out.println("(" + index + ") " + product + " - Available: " + amount);
                    index++;
                }
                System.out.println("0. Exit");
                System.out.print("Selection: ");
                choice = scan.next();
                int selection = Integer.parseInt(choice);
                if (selection == 0) {
                    break;
                } else if (selection > 0 && selection <= products.size()) {
                    Product selectedProduct = (Product) products.keySet().toArray()[selection - 1];
                    int availableQuantity = products.get(selectedProduct);

                    System.out.print("Amount: ");
                    int quantity = scan.nextInt();

                    if (quantity > 0 && quantity <= availableQuantity) {
                        activeCustomer.addToCart(selectedProduct.getID(), quantity);
                        /*Shop shop = Utils.readShopFile(selectedProduct.getShopID());
                        shop.sellProduct(selectedProduct.getID(), quantity);
                        shop.updateBill(selectedProduct.getID(), activeCustomer.getID(), quantity);*/
                        System.out.println("Added " + quantity + " " + selectedProduct.getName() + " to the cart!");
                    } else {
                        System.out.println("Invalid amount!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
            }
        }
        else{
            System.out.println("No product found!");
        }
        choice = scan.next();
        displayCategory();
    }

    public void displaySearchMenu(){
        Utils.clearScreen();
        String choice;
        while(true){
            System.out.println("---------- Search ----------");
            System.out.print("Enter product: ");
            choice = scan.next();
            ArrayList<Product> foundProducts = Utils.findProduct(choice);
            if(foundProducts.isEmpty()){
                System.out.println("No "+choice+" founded!");
                System.out.println("-- <Press any key to exit> --");
            }
            else{
                System.out.println("---------- "+choice+" ----------");
                for(int i = 0; i < foundProducts.size(); i++){
                    Shop shop = Utils.readShopFile(foundProducts.get(i).getShopID());
                    int order = i+1;
                    int amount = shop.amountProduct(foundProducts.get(i).getID());
                    System.out.println("("+order+") "+"ID: "+foundProducts.get(i).getID() +" "+ foundProducts.get(i)+" - Available: "+amount);
                }
                System.out.println("(0) Exit");
                System.out.print("Selection: ");
                choice = scan.next();
                int selection = Integer.parseInt(choice);
                if(selection == 0){
                    break;
                }
                else if(selection>0&& selection<=foundProducts.size()){
                    Product selectedProduct = foundProducts.get(selection-1);
                    Shop selectedShop = Utils.readShopFile(selectedProduct.getShopID());
                    System.out.print("Amount: ");
                    int quantity = scan.nextInt();
                    if(quantity>0 && quantity <= selectedShop.amountProduct(selectedProduct.getID())){
                        activeCustomer.addToCart(selectedProduct.getID(), quantity);

                        System.out.println("Added "+quantity+" "+selectedProduct.getName()+" to the cart!");
                    }
                    else System.out.println("Invalid amount!");
                }
                else{
                    System.out.println("Invalid choice!");
                }
            }
        }
        choice = scan.next();
        displayMallMenu();
    }

    public void displayCartMenu(){
        Utils.clearScreen();
        String choice;
        while(true){
            Map<String, Integer> cart = activeCustomer.getCart();
            int index = 1;
            System.out.println("---------- Cart ----------");
            for(Map.Entry<String, Integer> entry: cart.entrySet()){
                Product product = Utils.readProductFile(entry.getKey());
                int quantity = entry.getValue();
                System.out.println("("+index+") "+product+" - Amount: "+quantity);
                index++;
            }
            System.out.println("(0) Exit");
            System.out.println("Choose product to purchase: ");
            int selection = scan.nextInt();
            if(selection == 0){
                break;
            }
            else if(selection>0&&selection<=cart.size()){
                String productID = (String) cart.keySet().toArray()[selection-1];
                Product selectedProduct = Utils.readProductFile(productID);
                Shop shop = Utils.readShopFile(selectedProduct.getShopID());
                int availableAmount = shop.amountProduct(productID);
                int quantity = 0;
                double price;
                double totalMoney = 0;
                while(!activeCustomer.hasMoney(totalMoney)) {
                    System.out.println("Available: " + availableAmount);
                    System.out.println("Amount: ");
                    quantity = scan.nextInt();
                    price = selectedProduct.getPrice();
                    totalMoney = quantity * price;
                }

                if(quantity>0 && quantity <= cart.get(productID) && quantity <= availableAmount){
                    activeCustomer.buyPartial(productID, quantity);
                    activeCustomer.subtractMoney(totalMoney);
                    shop.addMoney(totalMoney);
                    shop.sellProduct(productID, quantity);

                    //Create bill
                    shop.updateBill(productID, activeCustomer.getID(), quantity);
                    System.out.println("Purchased successfully!");
                }
                else{
                    System.out.println("Invalid amount.");
                }
            }
            else{
                System.out.println("Invalid choice");
            }
        }
        choice = scan.next();
        displayAfterAuthMenu();
    }

    public void displayPurchasedMenu(){
        Utils.clearScreen();
        String choice;
        Map<String, Integer> purchased = activeCustomer.getPurchased();
        System.out.println("---------- Purchased ----------");
        int index = 1;
        for (Map.Entry<String, Integer> entry: purchased.entrySet()){
            Product product = Utils.readProductFile(entry.getKey());
            Bill bill = Utils.readBillFile(product.getID());
            System.out.println("("+index+") "+bill.getName()+" --- "+bill.getPrice()+" --- Amount: "+bill.amountOfPurchased(activeCustomer.getID()));
        }
        System.out.println("(0) Exit");
        System.out.println("-------------------------------");
        choice = scan.next();
        displayInformationMenu();
    }

    public void displayInformationMenu(){
        Utils.clearScreen();
        String choice;
        while(true){
            System.out.println("---------- Information ----------");
            System.out.println("Name ------- "+activeCustomer.getName());
            System.out.println("ID --------- "+activeCustomer.getID());
            System.out.println("Pass ------- "+activeCustomer.getPassword());
            System.out.println("Balance ---- "+activeCustomer.getBalance());
            System.out.println("(1) View Purchased Product");
            System.out.println("(2) Deposit money");
            System.out.println("(3) Withdraw money");
            System.out.println("(4) Exit");
            System.out.println("---------------------------------");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                displayPurchasedMenu();
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displayDepositMenu();
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                displayWithdrawMenu();
                return;
            }
            if(choice.equalsIgnoreCase("4")){
                displayAfterAuthMenu();
                return;
            }
        }
    }

    public void displayDepositMenu(){
        String value = "";
        double amount = -1;
        while(value.isEmpty() || !Utils.isDouble(value)){
            Utils.clearScreen();
            System.out.println("---------- Deposit ----------");
            System.out.println("--- Balance: "+activeCustomer.getBalance());
            System.out.println("--- Deposit: -----------------");
            System.out.println("------------------------------");
            value = scan.next();
        }
        amount = Double.parseDouble(value);
        if(amount<0){
            Utils.clearScreen();
            System.out.println("---------- Deposit ----------");
            System.out.println("--- Balance: "+activeCustomer.getBalance());
            System.out.println("--- Deposit: <Invalid amount>-");
            System.out.println("------------------------------");
            System.out.println("--- Press any key to exit ----");
        }
        else{
            Utils.clearScreen();
            this.activeCustomer.addMoney(amount);
            System.out.println("---------- Deposit ----------");
            System.out.println("--- Balance: "+activeCustomer.getBalance());
            System.out.println("--- Deposit: "+amount);
            System.out.println("--- <Deposit successfully> ---");
            System.out.println("--- Press any key to exit ----");
        }
        value = scan.next();
        displayInformationMenu();
    }

    public void displayWithdrawMenu(){
        Utils.clearScreen();
        String value;
        System.out.println("---------- Withdraw ----------");
        System.out.println("--- Balance: "+activeCustomer.getBalance());
        System.out.println("--- Withdraw: ----------------");
        System.out.println("------------------------------");
        value = scan.next();
        double amount = Double.parseDouble(value);
        if(!activeCustomer.hasMoney(amount)){
            Utils.clearScreen();
            System.out.println("---------- Withdraw ----------");
            System.out.println("--- Balance: "+activeCustomer.getBalance());
            System.out.println("--- Withdraw: <Invalid amount>");
            System.out.println("------------------------------");
            System.out.println("--- Press any key to exit ----");
        }
        else{
            activeCustomer.subtractMoney(amount);
            Utils.clearScreen();
            System.out.println("---------- Withdraw ----------");
            System.out.println("--- Balance: "+activeCustomer.getBalance());
            System.out.println("--- Withdraw: "+amount);
            System.out.println("--- <Withdraw successfully> ---");
            System.out.println("--- Press any key to exit ----");
        }
        value = scan.next();
        displayInformationMenu();
    }

}
