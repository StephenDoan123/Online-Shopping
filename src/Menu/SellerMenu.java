package Menu;

import Other.Utils;
import User.Customer;
import User.Seller;
import Transaction.Shop;
import Transaction.Product;
import Transaction.Bill;

import java.util.*;

public class SellerMenu {
    private Seller activeSeller;
    private Scanner scan = new Scanner(System.in);

    public SellerMenu(Seller seller){activeSeller = seller;}

    public void displayAuthMenu(){
        String choice;
        while(true){
            System.out.println("------------ Seller ------------");
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
        Seller seller = new Seller(name, ID, password);
        Utils.writeSellerFile(seller);
        activeSeller = seller;
        displayAfterAuthMenu();
    }

    public void displayLoginMenu(){
        Utils.clearScreen();
        String ID = "null";
        while(!Utils.hasAccount(ID, "Data/Seller/")){
            Utils.clearScreen();
            System.out.println("-------------- Login ---------------");
            System.out.println("ID: ----------");
            System.out.println("Password: ----");
            System.out.println("------------- <Input ID> -----------");
            ID = scan.next();
        }
        Seller seller = Utils.readSellerFile(ID);
        String password = "";
        while(!seller.isCorrectPassword(password)){
            Utils.clearScreen();
            System.out.println("-------------- Login ---------------");
            System.out.println("ID:   "+ID);
            System.out.println("Password: ----");
            System.out.println("---------- <Input password> --------");
            password = scan.next();
            if(!seller.isCorrectPassword(password)){
                displayAuthMenu();
                return;
            }
        }
        activeSeller = seller;
        displayAfterAuthMenu();
    }

    public void displayAfterAuthMenu(){
        Utils.clearScreen();
        String choice;
        while(true){
            System.out.println("---------- Seller Menu ----------");
            System.out.println("(1) Manage shop");
            System.out.println("(2) Information");
            System.out.println("(3) Log out");
            System.out.println("-----------------------------------");
            choice = scan.next();
            if(choice == "1"){
                displayShopMenu();
                return;
            }
            if(choice == "2"){
                displayInformationMenu();
                return;
            }
            if(choice == "3"){
                Utils.writeSellerFile(activeSeller);
                activeSeller = null;
                displayAuthMenu();
                return;
            }
        }
    }

    public void displayShopMenu(){

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
        ArrayList<Shop> shops = Utils.findCategory(category);
        ArrayList<String> sellerShopsID = activeSeller.getShops();
        ArrayList<Shop> sellerShop = new ArrayList<>();
        Map<Product, Integer> products = new HashMap<>();
        //==================== Lọc các shop của activeSeller
        for(int i = 0; i<shops.size(); i++){
            for(int j = 0; j<sellerShopsID.size(); j++){
                if(shops.get(i).getID()==sellerShopsID.get(j)){
                    Shop shop = shops.get(i);
                    shops.remove(shop);
                    sellerShop.add(shop);
                }
            }
        }
        //==================== Duyệt qua các file shop --> đọc file product
        for(int i = 0; i <shops.size(); i++){
            for(Map.Entry<String, Integer> entry: shops.get(i).getGoods().entrySet()){
                Product product = Utils.readProductFile(entry.getKey());
                Integer amount = entry.getValue();
                products.put(product, amount);
            }
        }
        //=================== Display product =======================
        String choice;
        while(true){
            System.out.println("-------- "+category+" --------");
            int index = 1;
            for(Map.Entry<Product, Integer> entry: products.entrySet()){
                Product product = entry.getKey();
                int amount = entry.getValue();
                System.out.println("("+index+") "+product+" - Available: "+amount);
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
                    Shop selectedShop = chooseShopMenu(sellerShop);
                    if(selectedShop==null){
                        break;
                    }
                    selectedShop.addToGoods(selectedProduct.getID(), quantity);
                    Shop sellShop = Utils.readShopFile(selectedProduct.getShopID());
                    Product transferProduct = Utils.readProductFile(selectedProduct.getID());
                    transferProduct.setShopID(selectedShop.getID());
                    Utils.writeProductFile(transferProduct);
                    sellShop.sellProduct(selectedProduct.getID(), quantity);
                    sellShop.updateBill(selectedProduct.getID(), activeSeller.getID(), quantity);
                    System.out.println("Added "+quantity+" "+selectedProduct.getName()+" to the shop!");
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

    public Shop chooseShopMenu(ArrayList<Shop> sellerShop){
        String choice;
        while(true){
            System.out.println("---------- Shop ----------");
            for (int i = 0; i<sellerShop.size();i++){
                Shop shop = sellerShop.get(i);
                int order = i+1;
                System.out.println("("+order+") "+"ID: "+shop.getID()+" Name: "+shop.getName());
            }
            System.out.println("(0) Exit");
            System.out.println("--- Selection ---");
            choice = scan.next();
            int selection = Integer.parseInt(choice);
            if(selection == 0){
                break;
            }
            else if(selection>0&& selection<=sellerShop.size()){
                return sellerShop.get(selection-1);
            }
            else{
                System.out.println("Invalid choice!");
            }
        }
        return null;
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
                        ArrayList<Shop> sellerShop =
                        Shop selectedShop = chooseShopMenu(sellerShop);
                        if(selectedShop==null){
                            break;
                        }
                        selectedShop.addToGoods(selectedProduct.getID(), quantity);
                        Shop sellShop = Utils.readShopFile(selectedProduct.getShopID());
                        Product transferProduct = Utils.readProductFile(selectedProduct.getID());
                        transferProduct.setShopID(selectedShop.getID());
                        Utils.writeProductFile(transferProduct);
                        sellShop.sellProduct(selectedProduct.getID(), quantity);
                        sellShop.updateBill(selectedProduct.getID(), activeSeller.getID(), quantity);
                        System.out.println("Added "+quantity+" "+selectedProduct.getName()+" to the shop!");
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

    public void displayInformationMenu(){

    }
}
