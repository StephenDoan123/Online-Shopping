package Other;

import java.io.*;
import java.util.*;

import Transaction.Bill;
import Transaction.Shop;
import User.Customer;
import User.Seller;
import Transaction.Product;
import com.google.gson.Gson;

public class Utils {

    //=================================================== Function ========================================================================
    public static void clearScreen(){
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public static boolean isDouble(String value){
        try{
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isInteger(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static String enumToString(Category category){
        return category.name();
    }
    public static Category stringToEnum(String name){
        Category category = Category.valueOf(Category.class, name);
        return category;
    }

    public static boolean hasAccount(String ID, String dataFolder){
        String filePath = dataFolder+ID+".txt";
        File file = new File(filePath);
        return file.exists()&&file.isFile();
    }

    public static String generateID(){
        String alphanum = "0123456789";
        int size = 6;
        StringBuilder rs = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i<size; i++){
            int randomIndex = rand.nextInt(alphanum.length());
            rs.append(alphanum.charAt(randomIndex));
        }
        return rs.toString();
    }
    public static boolean isValidID(String ID){
        if(ID.length()!=6){
            return false;
        }
        for(int i = 0; i<ID.length(); i++){
            if(!Character.isDigit(ID.charAt(i))) return false;
        }
        return true;
    }

    public static String[] allFiles(String target){
        String folderPath = "C://Users//Os//IdeaProjects//OnlineShopping//Data//"+target;

        File folder = new File(folderPath);
        String[] fileNames = new String[0];
        if(folder.isDirectory()){
            fileNames = folder.list();
        }
        return fileNames;
    }

    public static ArrayList<Shop> findCategory(String targetCategory){
        String[] files = allFiles("Shop");
        ArrayList<Shop> foundShops = new ArrayList<>();
        if(files.length == 0){
            return foundShops = new ArrayList<>(0);
        }
        for(String file:files){
            Shop shop = null;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String shopID = reader.readLine();
                String shopName = reader.readLine();
                String category = reader.readLine();

                if (category != null && category.equalsIgnoreCase(targetCategory)) {
                    shop = Utils.readShopFile(shopID);
                    foundShops.add(shop);
                }
            } catch (IOException e) {
                System.out.println("An error occurs when reading  " + file);
                e.printStackTrace();
            }
        }
        return foundShops;
    }

    public static ArrayList<Product> findProduct(String targetProduct){
        String[] files = allFiles("Product");
        ArrayList<Product> foundProducts = new ArrayList<>();
        if(files.length == 0){
            return foundProducts = new ArrayList<>(0);
        }
        for(String file: files){
            Product product = null;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String productID = reader.readLine();
                String productName = reader.readLine();

                if (productName != null && productName.equalsIgnoreCase(targetProduct)) {
                    product = Utils.readProductFile(productID);
                    foundProducts.add(product);
                }
            } catch (IOException e) {
                System.out.println("An error occurs when reading  " + file);
                e.printStackTrace();
            }
        }
        return foundProducts;
    }
    //==================================================  File =======================================================================
    //================================================== Customer ===================================================================
    public static void writeCustomerFile (Customer customer){
        String name = customer.getName();
        String ID = customer.getID();
        String password = customer.getPassword();
        String balance = Double.toString(customer.getBalance());
        Map<String, Integer> cart = customer.getCart();
        Map<String, Integer> purchased = customer.getPurchased();

        String filePath = "Data/Customer/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write(balance);
            writer.newLine();

            writer.write("Cart:");
            writer.newLine();
            for(String id: cart.keySet()){
                writer.write("ID: "+id+", amount: "+Integer.toString(cart.get(id)));
                writer.newLine();
            }
            writer.write("Purchased:");
            writer.newLine();
            for(String id: purchased.keySet()){
                writer.write("ID: "+id+", amount: "+Integer.toString(purchased.get(id)));
                writer.newLine();
            }

            writer.close();
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public static Customer readCustomerFile(String ID){
        String filePath = "Data/Customer/"+ID+".txt";

        Customer customer = new Customer(" ", " "," ");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String id = line.trim();
                    String name = line.trim();
                    String password = line.trim();
                    double balance = Double.parseDouble(line.trim());
                    Map<String, Integer> Cart = new HashMap<>();
                    Map<String, Integer> Purchased = new HashMap<>();

                    if(line.equals("Cart:")){
                        while((line = reader.readLine()) != null && !line.equals("Purchased:")){
                            String[] parts = line.split(", ");
                            String productID = parts[0].split(": ")[1];
                            int amount = Integer.parseInt(parts[1].split(": ")[1]);
                            Cart.put(productID, amount);
                        }

                        while((line = reader.readLine())!=null){
                            String[] parts = line.split(", ");
                            String productID = parts[0].split(": ")[1];
                            int amount = Integer.parseInt(parts[1].split(": ")[1]);
                            Purchased.put(productID, amount);
                        }
                    }
                    customer.setName(name);
                    customer.setPassword(password);
                    customer.setID(id);
                    customer.setBalance(balance);
                    customer.setCart(Cart);
                    customer.setPurchased(Purchased);
                    reader.close();
                }
            }
        } catch(IOException e){
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Error parsing customer ID.");
            e.printStackTrace();
        }
        return customer;
    }
    //================================================== Seller =====================================================================
    public static void writeSellerFile (Seller seller){
        String name = seller.getName();
        String ID = seller.getID();
        String password = seller.getPassword();
        String balance = Double.toString(seller.getBalance());
        ArrayList<String> shops = seller.getShops();
        String filePath = "Data/Seller/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write(balance);
            writer.close();
            writer.write("Shops:");
            writer.newLine();
            for(String id: shops){
                writer.write("ID: "+id);
                writer.newLine();
            }
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
    public static Seller readSellerFile(String ID){
        String filePath = "Data/Seller/"+ID+".txt";
        Seller seller = new Seller(" ", " ", " ", 0);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String id = line.trim();
                    String name = reader.readLine().trim();
                    String password = reader.readLine().trim();
                    double balance = Double.parseDouble(line.trim());
                    seller.setName(name);
                    seller.setPassword(password);
                    seller.setID(id);
                    seller.setBalance(balance);

                    ArrayList<String> shops = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("ID: ")) {
                            String billID = line.substring(4);
                            shops.add(billID);
                        }
                    }
                    reader.close();
                    seller.setShops(shops);
                }

            }
        } catch(IOException e){
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Error parsing seller ID.");
            e.printStackTrace();
        }
        return seller;
    }

    //================================================== Shop =======================================================================
    public static void writeShopFile(Shop shop){
        String name = shop.getName();
        String ID = shop.getID();
        String categoryName = Utils.enumToString(shop.getCategory());
        String balance = Double.toString(shop.getBalance());
        Map<String, Integer> goods = shop.getGoods();
        ArrayList<String> bills = shop.getBills();

        String filePath = "Data/Shop/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(categoryName);
            writer.newLine();
            writer.write(balance);
            writer.write("Goods:");
            writer.newLine();
            for(String id: goods.keySet()){
                writer.write("ID: "+id+", amount: "+Integer.toString(goods.get(id)));
                writer.newLine();
            }
            writer.write("Bills:");
            writer.newLine();
            for(String id: bills){
                writer.write("ID: "+id);
                writer.newLine();
            }

            writer.close();
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
    public static Shop readShopFile(String ID){
        String filePath = "Data/Shop/"+ID+".txt";
        Shop shop = new Shop(" ", " ");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String id = reader.readLine().trim();
            String name = reader.readLine().trim();
            String categoryName = reader.readLine().trim();
            Category category = Utils.stringToEnum(categoryName);
            double balance = Double.parseDouble(reader.readLine());
            shop.setID(id);
            shop.setName(name);
            shop.setCategory(category);
            shop.setBalance(balance);

            Map<String, Integer> goods = new HashMap<>();
            String line = reader.readLine();
            while((line = reader.readLine()) != null && !line.equals("Bills: ")){
                if(line.startsWith("ID: ")){
                    String[] parts = line.split(", ");
                    String productID = parts[0].substring(4);
                    int amount = Integer.parseInt(parts[1].substring(8));
                    goods.put(productID, amount);
                }
            }
            ArrayList<String> bills = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    String billID = line.substring(4);
                    bills.add(billID);
                }
            }
            reader.close();
            shop.setGoods(goods);
            shop.setBills(bills);
        } catch(IOException e){
            System.out.println("An error occurred while reading the file!");
            e.printStackTrace();
        }

        return shop;
    }

    //================================================== Product ====================================================================
    public static void writeProductFile(Product product){
        String name = product.getName();
        String ID = product.getID();
        String price = Double.toString(product.getPrice());
        String shopID = product.getShopID();

        String filePath = "Data/Product/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(price);
            writer.newLine();
            writer.write(shopID);
            writer.close();
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public static Product readProductFile(String ID){
        String filePath = "Data/Product/"+ID+".txt";
        Product product = new Product(" ", " "," ", 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String name = line.trim();
                    String shopID = line.trim();
                    String id = line.trim();
                    double price = Double.parseDouble(line.trim());
                    Map<String, Integer> purchasedBy = new HashMap<>();
                    product.setName(name);
                    product.setID(id);
                    product.setShopID(shopID);
                    product.setPrice(price);
                    reader.close();
                }
            }
        } catch(IOException e){
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Error parsing bill ID.");
            e.printStackTrace();
        }
        return product;
    }

    //================================================== Bill ========================================================================
    public static void writeBillFile(Bill bill){
        String name = bill.getName();
        String ID = bill.getID();
        String price = Double.toString(bill.getPrice());
        String filePath = "Data/Bill/"+ID+".txt";
        Map<String, Integer> purchasedBy = bill.getPurchasedBy();
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(price);
            writer.newLine();

            writer.write("Purchased by:");
            writer.newLine();
            for(String id: purchasedBy.keySet()){
                writer.write("Customer: "+id+", amount: "+Integer.toString(purchasedBy.get(id)));
                writer.newLine();
            }

            writer.close();
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
    public static Bill readBillFile(String ID){
        String filePath = "Data/Bill/"+ID+".txt";

        Bill bill = new Bill(" ", " ",0);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String id = line.trim();
                    String name = line.trim();
                    Double price = Double.parseDouble(line.trim());
                    Map<String, Integer> purchasedBy = new HashMap<>();

                    if(line.equals("Purchased by:")){
                        while((line = reader.readLine())!=null){
                            String[] parts = line.split(", ");
                            String customerID = parts[0].split(": ")[1];
                            int amount = Integer.parseInt(parts[1].split(": ")[1]);
                            purchasedBy.put(customerID, amount);
                        }
                    }
                    reader.close();
                    bill.setName(name);
                    bill.setID(id);
                    bill.setPrice(price);
                    bill.setPurchasedBy(purchasedBy);
                }
            }
        } catch(IOException e){
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Error parsing bill ID.");
            e.printStackTrace();
        }
        return bill;
    }
}