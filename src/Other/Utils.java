package Other;

import java.io.*;
import java.util.*;

import Transaction.Sold;
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
        return Category.valueOf(Category.class, name);
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

    public static boolean deleteFile(String ID, String folderPath){
        File file = new File(folderPath + ID+".txt");
        if (file.exists()){
            return file.delete();
        }
        else{
            return false;
        }
    }

    public static ArrayList<Shop> findShopsByCategory(String targetCategory){
        String folderPath = "Data/Shop";
        File folder = new File(folderPath);
        ArrayList<Shop> matchingShops = new ArrayList<>();
        if(!folder.exists() || !folder.isDirectory()){
            System.out.println("Shop directory not found or is invalid: "+folderPath);
            return matchingShops;
        }

        String [] fileNames = folder.list();
        if(fileNames == null || fileNames.length == 0){
            System.out.println("No shop files found in the directory: "+folderPath);
            return matchingShops;
        }

        for (String fileName: fileNames){
            if(!fileName.endsWith(".txt")) continue;
            String shopID = fileName.substring(0, fileName.length()-4);
            Shop shop = Utils.readShopFile(shopID);
            if(shop.getCategory()!=null && shop.getCategory().name().equalsIgnoreCase(targetCategory)){
                matchingShops.add(shop);
            }
        }
        return matchingShops;
    }

    public static ArrayList<Product> findProduct(String targetName){
        String folderPath = "Data/Product";
        File folder = new File(folderPath);
        ArrayList<Product> matchingProducts = new ArrayList<>();

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Product directory not found or is invalid: " + folderPath);
            return matchingProducts;
        }

        String[] fileNames = folder.list();
        if (fileNames == null || fileNames.length == 0) {
            System.out.println("No product files found in the directory: " + folderPath);
            return matchingProducts;
        }

        for (String fileName : fileNames) {
            if (!fileName.endsWith(".txt")) continue;
            String productID = fileName.substring(0, fileName.length() - 4);
            Product product = Utils.readProductFile(productID);

            if (product.getName() != null && product.getName().equalsIgnoreCase(targetName)) {
                matchingProducts.add(product);
            }
        }

        return matchingProducts;
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
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
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
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public static Customer readCustomerFile(String ID){
        String filePath = "Data/Customer/"+ID+".txt";

        Customer customer = new Customer(" ", " "," ");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String id = reader.readLine();
            String name = reader.readLine();
            String password = reader.readLine();
            double balance = Double.parseDouble(reader.readLine());

            String cartHeader = reader.readLine();
            Map<String, Integer> cart = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null && !line.equals("Purchased:")) {
                if (line.startsWith("ID: ")) {
                    String[] parts = line.split(", amount: ");
                    String productID = parts[0].substring(4);
                    int amount = Integer.parseInt(parts[1]);
                    cart.put(productID, amount);
                }
            }

            Map<String, Integer> purchased = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    String[] parts = line.split(", amount: ");
                    String productID = parts[0].substring(4);
                    int amount = Integer.parseInt(parts[1]);
                    purchased.put(productID, amount);
                }
            }

            customer.setID(ID);
            customer.setName(name);
            customer.setPassword(password);
            customer.setBalance(balance);
            customer.setCart(cart);
            customer.setPurchased(purchased);
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
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write(balance);
            writer.newLine();
            writer.write("Shops:");
            writer.newLine();
            for(String id: shops){
                writer.write("ID: "+id);
                writer.newLine();
            }
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
    public static Seller readSellerFile(String ID){
        String filePath = "Data/Seller/"+ID+".txt";
        Seller seller = new Seller(" ", " ", " ", 0);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String id = reader.readLine();
            String name = reader.readLine();
            String password = reader.readLine();
            double balance = Double.parseDouble(reader.readLine());

            String shopsID = reader.readLine();
            ArrayList<String> shops = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null){
                if(line.startsWith("ID: ")){
                    shops.add(line.substring(4));
                }
            }
            seller.setID(ID);
            seller.setName(name);
            seller.setPassword(password);
            seller.setBalance(balance);
            seller.setShops(shops);
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
        String ownerID = shop.getOwnerID();
        Map<String, Integer> goods = shop.getGoods();
        ArrayList<String> solds = shop.getSolds();

        String filePath = "Data/Shop/"+ID+".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(categoryName);
            writer.newLine();
            writer.write(balance);
            writer.newLine();
            writer.write(ownerID);
            writer.newLine();
            writer.write("Goods:");
            writer.newLine();
            for(String id: goods.keySet()){
                writer.write("ID: "+id+", amount: "+Integer.toString(goods.get(id)));
                writer.newLine();
            }
            writer.write("Solds:");
            writer.newLine();
            for(String id: solds){
                writer.write("ID: "+id);
                writer.newLine();
            }
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
            String ownerID = reader.readLine().trim();
            shop.setID(id);
            shop.setName(name);
            shop.setCategory(category);
            shop.setBalance(balance);
            shop.setOwnerID(ownerID);

            Map<String, Integer> goods = new HashMap<>();
            String line = reader.readLine();
            while((line = reader.readLine()) != null && !line.equals("Solds:")){
                if(line.startsWith("ID: ")){
                    String[] parts = line.split(", ");
                    String productID = parts[0].substring(4);
                    int amount = Integer.parseInt(parts[1].substring(8));
                    goods.put(productID, amount);
                }
            }

            ArrayList<String> solds = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    String soldID = line.substring(4);
                    solds.add(soldID);
                }
            }
            shop.setGoods(goods);
            shop.setSolds(solds);
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
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(price);
            writer.newLine();
            writer.write(shopID);
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public static Product readProductFile(String ID){
        String filePath = "Data/Product/"+ID+".txt";
        Product product = new Product(" ", " "," ", 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String id = reader.readLine();
            String name = reader.readLine();
            double price = Double.parseDouble(reader.readLine());
            String shopID = reader.readLine();
            product.setID(ID);
            product.setName(name);
            product.setPrice(price);
            product.setShopID(shopID);
        } catch(IOException e){
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Error parsing sold's ID.");
            e.printStackTrace();
        }
        return product;
    }

    //================================================== Sold ========================================================================
    public static void writeSoldFile(Sold sold){
        String name = sold.getName();
        String ID = sold.getID();
        String price = Double.toString(sold.getPrice());
        String filePath = "Data/Sold/"+ID+".txt";
        Map<String, Integer> purchasedBy = sold.getPurchasedBy();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
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

        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }


    public static Sold readSoldFile(String ID){
        String filePath = "Data/Sold/"+ID+".txt";

        Sold sold = new Sold(" ", " ",0);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String id = reader.readLine();
            String name = reader.readLine();
            double price = Double.parseDouble(reader.readLine());

            String purchasedByHeader = reader.readLine();
            Map<String, Integer> purchasedBy = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Customer: ")) {
                    String[] parts = line.split(", amount: ");
                    String customerID = parts[0].substring(10);
                    int amount = Integer.parseInt(parts[1]);
                    purchasedBy.put(customerID, amount);
                }
            }
            sold.setName(name);
            sold.setID(ID);
            sold.setPrice(price);
            sold.setPurchasedBy(purchasedBy);
        } catch(IOException e){
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Error parsing sold ID.");
            e.printStackTrace();
        }
        return sold;
    }
}