package Menu;

import Other.Category;
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
            Utils.clearScreen();
            System.out.println("------------ Seller ------------");
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
        Utils.clearScreen();
        String ID = Utils.generateID();
        String name = "";
        String password = "";
        while(name.isEmpty()){
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
            System.out.println("(1) Shop");
            System.out.println("(2) Create new shop");
            System.out.println("(3) Information");
            System.out.println("(4) Log out");
            System.out.println("-----------------------------------");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                displayAllShopMenu();
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displayNewShopMenu();
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                displayInformationMenu();
                return;
            }
            if(choice.equalsIgnoreCase("4")){
                Utils.writeSellerFile(activeSeller);
                activeSeller = null;
                displayAuthMenu();
                return;
            }
        }
    }

    public void displayAllShopMenu(){
        Utils.clearScreen();
        String choice;
        ArrayList<String> shopID = activeSeller.getShops();
        ArrayList<Shop> sellerShop = new ArrayList<>();

        // ============ Lỗi ở đây
        for (String s : shopID) {
            Shop shop = Utils.readShopFile(s);
            sellerShop.add(shop);
        }
        Shop selectedShop = null;
        while(true){
            System.out.println("---------- Shop ----------");
            for(int i = 0; i<sellerShop.size(); i++){
                int order = i+1;
                System.out.println("("+order+") Name: "+sellerShop.get(i).getName()+" ID: "+sellerShop.get(i).getID());
            }
            System.out.println("(0) Exit");
            System.out.println("--------------------------");
            choice = scan.next();
            int selection = Integer.parseInt(choice);
            if(selection == 0){
                break;
            }
            else if(selection>0&& selection<=sellerShop.size()){
                selectedShop = sellerShop.get(selection-1);
                displayShopMenu(selectedShop);
                break;
            }
            else{
                System.out.println("Invalid choice!");
            }
        }
        displayAfterAuthMenu();
    }

    public void displayShopMenu(Shop shop){
        Utils.clearScreen();
        String choice;
        while(true){
            System.out.println("----- "+shop.getName()+" "+shop.getID()+" ----------");
            System.out.println("(1) Product");
            System.out.println("(2) Bills");
            System.out.println("(3) Balance");
            System.out.println("(4) Delete shop");
            System.out.println("(5) Exit");
            System.out.println("--------------------------");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                displayProductMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displayBillMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                displayShopBalance(shop);
                return;
            }
            if(choice.equalsIgnoreCase("4")){
                displayDeleteShopMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("5")){
                //-----------------------------------------------Sửa ở chỗ này
                Utils.writeShopFile(shop);
                shop = null;
                //displayAllShopMenu();
                return;
            }
        }
    }

    public void displayProductMenu(Shop shop){
        Utils.clearScreen();
        String choice;
        Map<String, Integer> goods = shop.getGoods();
        while(true){
            System.out.println("---------- Product -----------");
            for(Map.Entry<String, Integer> entry: goods.entrySet()){
                Product product = Utils.readProductFile(entry.getKey());
                int amount = entry.getValue();
                System.out.println("--- "+product+" - Amount: "+amount);
            }
            System.out.println("(1) Buy product");
            System.out.println("(2) Create product");
            System.out.println("(3) Remove product");
            System.out.println("(4) Exit");
            System.out.println("------------------------------");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                buyProductMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                createProductMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                removeProductMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("4")){
                displayShopMenu(shop);
                return;
            }
        }
    }

    public void buyProductMenu(Shop shop){
        Utils.clearScreen();
        String choice = "";
        while(true){
            ArrayList<String> sellerShops = activeSeller.getShops();
            String category = Utils.enumToString(shop.getCategory());
            String foundCategory = "";
            ArrayList<Product> foundProducts = new ArrayList<>();
            //================= Lọc category
            while(!foundCategory.equals(category)){
                System.out.println("---------- Search ----------");
                System.out.println("Category: "+category);
                System.out.println("Enter product: ");
                choice = scan.next();
                foundProducts = Utils.findProduct(choice);
                Shop foundShop = Utils.readShopFile(foundProducts.getFirst().getShopID());
                foundCategory = Utils.enumToString(foundShop.getCategory());
            }


            //================= Lọc shop ======================
            for(int i = 0; i<foundProducts.size(); i++){
                Shop foundShop = Utils.readShopFile(foundProducts.get(i).getShopID());
                for(int j = 0; j<sellerShops.size(); j++){
                    boolean isSellerShop = foundShop.getID().equals(sellerShops.get(i));
                    if(isSellerShop){
                        foundProducts.remove(i);
                    }
                }
            }

            if(foundProducts.isEmpty()){
                System.out.println("No "+choice+" founded!");
            }
            else{
                System.out.println("---------- "+choice+" ----------");
                for(int i = 0; i < foundProducts.size(); i++){
                    Shop foundedShop = Utils.readShopFile(foundProducts.get(i).getShopID());
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
                    int quantity = 0;
                    double price = 0;
                    double totalMoney = 0;
                    while(!activeSeller.hasMoney(totalMoney)) {
                        System.out.println("Amount: ");
                        quantity = scan.nextInt();
                        price = selectedProduct.getPrice();
                        totalMoney = quantity * price;
                    }

                    if(quantity>0 && quantity <= selectedShop.amountProduct(selectedProduct.getID())){
                        shop.addToGoods(selectedProduct.getID(), quantity);
                        activeSeller.subtractMoney(totalMoney);
                        shop.addMoney(totalMoney);
                        selectedShop.sellProduct(selectedProduct.getID(), quantity);
                        shop.updateBill(selectedProduct.getID(), activeSeller.getID(), quantity);

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
        displayProductMenu(shop);
    }

    public void createProductMenu(Shop shop){
        String ID = Utils.generateID();
        String name = "";
        String price = "";
        String amount = "";
        while(name.isEmpty()) {
            Utils.clearScreen();
            System.out.println("---------- Create Product ----------");
            System.out.println(" Category: " + Utils.enumToString(shop.getCategory()));
            System.out.println(" Name:   ---------------------------");
            System.out.println(" ID: "+ID);
            System.out.println(" Price:  ---------------------------");
            System.out.println(" Amount: ---------------------------");
            System.out.println("----------- <Input Name> -----------");
            name = scan.next();
        }
        while(price.isEmpty() || !Utils.isDouble(price)){
            Utils.clearScreen();
            System.out.println("---------- Create Product ----------");
            System.out.println(" Category: " + Utils.enumToString(shop.getCategory()));
            System.out.println(" Name: "+ name);
            System.out.println(" ID: "+ID);
            System.out.println(" Price:  ---------------------------");
            System.out.println(" Amount: ---------------------------");
            System.out.println("----------- <Input Price> ----------");
            price = scan.next();
        }
        while(amount.isEmpty() || !Utils.isInteger(amount)){
            Utils.clearScreen();
            System.out.println("---------- Create Product ----------");
            System.out.println(" Category: " + Utils.enumToString(shop.getCategory()));
            System.out.println(" Name: "+ name);
            System.out.println(" ID: "+ID);
            System.out.println(" Price:  "+price);
            System.out.println(" Amount: ---------------------------");
            System.out.println("---------- <Input Amount> ----------");
            amount = scan.next();
        }
        Product product = new Product(name, shop.getID(), ID, Double.parseDouble(price));
        Utils.writeProductFile(product);
        shop.addToGoods(ID, Integer.parseInt(amount));
        Utils.clearScreen();
        System.out.println("---------- Create Product ----------");
        System.out.println(" Category: " + Utils.enumToString(shop.getCategory()));
        System.out.println(" Name: "+ name);
        System.out.println(" ID: "+ID);
        System.out.println(" Price:  "+price);
        System.out.println(" Amount: "+amount);
        System.out.println("---------- <Successfully> ----------");
        String choice = scan.next();
        displayProductMenu(shop);
    }

    public void removeProductMenu(Shop shop){
        String choice;
        Map<String, Integer> goods = shop.getGoods();
        while(true) {
            Utils.clearScreen();
            System.out.println("---------- Remove Product ----------");
            int index = 1;
            for (Map.Entry<String, Integer> entry : goods.entrySet()) {
                Product product = Utils.readProductFile(entry.getKey());
                int amount = entry.getValue();
                System.out.println("(" + index + ") " + product + " - Amount: " + amount);
                index++;
            }
            System.out.println("(0) Exit");
            System.out.println("Choose product to remove: ");
            int selection = scan.nextInt();
            if (selection == 0) {
                break;
            }
            else if(selection>0&&selection<=goods.size()){
                String productID = (String) goods.keySet().toArray()[selection-1];
                if(Utils.deleteFile(productID)) {
                    goods.remove(productID);
                    System.out.println("Delete product successfully!");
                }
                else{
                    System.out.println("Delete product failed");
                }
            }
            else{
                System.out.println("Invalid choice!");
            }
        }
        choice = scan.next();
        displayProductMenu(shop);
    }

    public void displayBillMenu(Shop shop){
        Utils.clearScreen();
        String choice;
        ArrayList<String> billID = shop.getBills();
        ArrayList<Bill> bills = new ArrayList<>();
        for(String id: billID){
            Bill bill = Utils.readBillFile(id);
            bills.add(bill);
        }
        System.out.println("------------- Bill ------------");
        for(Bill bill: bills){
            System.out.println(bill);
            System.out.println("Purchased by: "+bill.printPurchased());
        }
        System.out.println("--- <Press any key to exit> ---");
        choice = scan.next();
        displayShopMenu(shop);
    }

    public void displayShopBalance(Shop shop){
        String choice;
        while(true){
            Utils.clearScreen();
            System.out.println("---------- "+shop.getName()+" Balance ----------");
            System.out.println("Balance: "+shop.getBalance());
            System.out.println("(1) Withdraw money");
            System.out.println("(2) Deposit money");
            System.out.println("(3) Exit");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                displayWithDrawMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("2")){
                displayDepositMenu(shop);
                return;
            }
            if(choice.equalsIgnoreCase("3")){
                displayShopMenu(shop);
                return;
            }
        }
    }

    public void displayWithDrawMenu(Shop shop){
        Utils.clearScreen();
        String value = "";
        double amount;
        while(value.isEmpty() || !Utils.isDouble(value)){
            System.out.println("----- Withdraw from shop -----");
            System.out.println("--- Balance: "+shop.getBalance());
            System.out.println("--- Withdraw: ----------------");
            System.out.println("------------------------------");
            value = scan.next();
        }
        amount = Double.parseDouble(value);
        if(!shop.hasMoney(amount)){
            Utils.clearScreen();
            System.out.println("----- Withdraw from shop -----");
            System.out.println("--- Balance: "+shop.getBalance());
            System.out.println("--- Withdraw: <Invalid amount>");
            System.out.println("------------------------------");
            System.out.println("--- Press any key to exit ----");
        }
        else{
            shop.subtractMoney(amount);
            Utils.clearScreen();
            System.out.println("----- Withdraw from shop -----");
            System.out.println("--- Balance: "+shop.getBalance());
            System.out.println("--- Withdraw: "+amount);
            System.out.println("--- <Deposit successfully> ---");
            System.out.println("--- Press any key to exit ----");
        }
        value = scan.next();
        displayShopBalance(shop);
    }

    public void displayDepositMenu(Shop shop){
        Utils.clearScreen();
        String value = "";
        double amount = -1;
        while(value.isEmpty() || !Utils.isDouble(value)){
            System.out.println("------- Deposit to Shop ------");
            System.out.println("--- Balance: "+shop.getBalance());
            System.out.println("--- Deposit: -----------------");
            System.out.println("------------------------------");
            value = scan.next();
        }
        amount = Double.parseDouble(value);
        if(amount<0){
            Utils.clearScreen();
            System.out.println("------- Deposit to Shop ------");
            System.out.println("--- Shop balance: "+shop.getBalance());
            System.out.println("--- Deposit: <Invalid amount>-");
            System.out.println("------------------------------");
            System.out.println("--- Press any key to exit ----");
        }
        else{
            this.activeSeller.subtractMoney(amount);
            shop.addMoney(amount);
            System.out.println("------- Deposit to Shop ------");
            System.out.println("--- Shop balance: "+shop.getBalance());
            System.out.println("--- Deposit: "+amount);
            System.out.println("--- <Deposit successfully> ---");
            System.out.println("--- Press any key to exit ----");
        }
        value = scan.next();
        displayShopBalance(shop);
    }

    public void displayDeleteShopMenu(Shop shop){
        String choice;
        System.out.println("------------- Shop -------------");
        System.out.println("Name: "+shop.getName());
        System.out.println("ID: "+shop.getID());
        System.out.println("Category: "+shop.getCategory());
        System.out.println("Do you want to delete shop? (Y/N)");
        choice = scan.next();
        if(choice.equalsIgnoreCase("y")){
            activeSeller.deleteShop(shop.getID());
            activeSeller.addMoney(shop.getBalance());
            Utils.deleteFile(shop.getID());
            System.out.println("------------- Shop -------------");
            System.out.println("--- Delete shop successfully! --");
            System.out.println("---- <Press any key to exit> ---");
        }
        else if(choice.equalsIgnoreCase("n")){
            System.out.println("---- <Press any key to exit> ---");
        }
        else{
            System.out.println("------- <Invalid choice> -------");
            System.out.println("---- <Press any key to exit> ---");
        }
        choice = scan.next();
        displayAllShopMenu();
    }

    public void displayNewShopMenu(){
        String ID = Utils.generateID();
        String name = "";
        String category = "";
        Category shopCategory = Category.NONE;
        String choice = "";
        while(choice.isEmpty()){
            Utils.clearScreen();
            System.out.println("-------- Choose category --------");
            System.out.println("(1) Sports");
            System.out.println("(2) Electronics");
            System.out.println("(3) Clothes");
            System.out.println("(4) Foods");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                shopCategory =  Category.SPORTS;
                category = "Sports";
            }
            if(choice.equalsIgnoreCase("2")){
                shopCategory =  Category.ELECTRONICS;
                category = "Electronics";
            }
            if(choice.equalsIgnoreCase("3")){
                shopCategory =  Category.CLOTHES;
                category = "Clothes";
            }
            if(choice.equalsIgnoreCase("4")){
                shopCategory =  Category.FOODS;
                category = "Foods";
            }
        }
        while(name.isEmpty()) {
            Utils.clearScreen();
            System.out.println("---------- Create Shop ----------");
            System.out.println(" Category: " + category);
            System.out.println(" Name:   ------------------------");
            System.out.println(" ID: "+ID);
            System.out.println("--------- <Input Name> ----------");
            name = scan.next();
        }
        Shop shop = new Shop(name, ID, shopCategory, 0);
        shop.setOwnerID(activeSeller.getID());
        shop.setCategory(shopCategory);
        Utils.writeShopFile(shop);

        activeSeller.getShops().add(ID);
        Utils.writeSellerFile(activeSeller);
        Utils.clearScreen();
        System.out.println("----------- Create Shop ----------");
        System.out.println(" Category: " + category);
        System.out.println(" Name: "+name);
        System.out.println(" ID: "+ID);
        System.out.println("--- <Create shop successfully> ---");
        System.out.println("---- <Press any key to exit> -----");
        choice = scan.next();
        displayAfterAuthMenu();
    }

    public void displayInformationMenu(){
        Utils.clearScreen();
        System.out.println("------------ <Seller> ------------");
        System.out.println("ID: "+activeSeller.getID());
        System.out.println("Name: "+activeSeller.getName());
        System.out.println("Balance: "+activeSeller.getBalance());
        System.out.println("Shops: ");
        ArrayList<String> shopID = activeSeller.getShops();
        ArrayList<Shop> shops = new ArrayList<>();
        for(String id: shopID){
            Shop shop = Utils.readShopFile(id);
            shops.add(shop);
        }
        if(shops.isEmpty()){
            System.out.println("<No shop found>");
        }
        else{
            for(Shop shop: shops){
                System.out.println("--- Name: "+shop.getName()+" ID: "+shop.getID()+" Category: "+Utils.enumToString(shop.getCategory()));
            }
        }
        System.out.println("(1) Deposit money");
        System.out.println("(2) Withdraw money");
        System.out.println("(3) Exit");
        System.out.println("----------------------------------");
        String choice = scan.next();
        if(choice.equalsIgnoreCase("1")){
            displayDepositMenu();
            return;
        }
        if(choice.equalsIgnoreCase("2")){
            displayWithDrawMenu();
            return;
        }
        if(choice.equalsIgnoreCase("3")){
            displayAfterAuthMenu();
            return;
        }
    }

    public void displayDepositMenu(){
        String value = "";
        double amount = -1;
        while(value.isEmpty() || !Utils.isDouble(value)){
            Utils.clearScreen();
            System.out.println("---------- Deposit ----------");
            System.out.println("--- Balance: "+activeSeller.getBalance());
            System.out.println("--- Deposit: -----------------");
            System.out.println("------------------------------");
            value = scan.next();
        }
        amount = Double.parseDouble(value);
        if(amount<0){
            Utils.clearScreen();
            System.out.println("---------- Deposit ----------");
            System.out.println("--- Balance: "+activeSeller.getBalance());
            System.out.println("--- Deposit: <Invalid amount>-");
            System.out.println("------------------------------");
            System.out.println("--- Press any key to exit ----");
        }
        else{
            Utils.clearScreen();
            this.activeSeller.addMoney(amount);
            System.out.println("---------- Deposit ----------");
            System.out.println("--- Balance: "+activeSeller.getBalance());
            System.out.println("--- Deposit: "+amount);
            System.out.println("--- <Deposit successfully> ---");
            System.out.println("--- Press any key to exit ----");
        }
        value = scan.next();
        displayInformationMenu();
    }

    public void displayWithDrawMenu(){
        Utils.clearScreen();
        String value;
        System.out.println("---------- Withdraw ----------");
        System.out.println("--- Balance: "+activeSeller.getBalance());
        System.out.println("--- Withdraw: ----------------");
        System.out.println("------------------------------");
        value = scan.next();
        double amount = Double.parseDouble(value);
        if(!activeSeller.hasMoney(amount)){
            Utils.clearScreen();
            System.out.println("---------- Withdraw ----------");
            System.out.println("--- Balance: "+activeSeller.getBalance());
            System.out.println("--- Withdraw: <Invalid amount>");
            System.out.println("------------------------------");
            System.out.println("--- Press any key to exit ----");
        }
        else{
            activeSeller.subtractMoney(amount);
            Utils.clearScreen();
            System.out.println("---------- Withdraw ----------");
            System.out.println("--- Balance: "+activeSeller.getBalance());
            System.out.println("--- Withdraw: "+amount);
            System.out.println("--- <Withdraw successfully> ---");
            System.out.println("--- Press any key to exit ----");
        }
        value = scan.next();
        displayInformationMenu();
    }
}
