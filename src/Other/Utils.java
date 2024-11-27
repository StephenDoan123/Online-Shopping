package Other;

import java.io.*;
import java.util.*;

import Transaction.Bill;
import Transaction.Shop;
import User.Customer;
import User.Seller;
import Transaction.Product;

public class Utils {

    //=================================================== Function ========================================================================
    public static String enumToString(Shop shop){
        return shop.getCategory().name();
    }
    public static Category stringToCategory(String name){
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

    public static void showCategories(){

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
                        while(!(line = reader.readLine()).equals("Purchased:")){
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
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
    public static Seller readSellerFile(String ID){
        String filePath = "Data/Seller/"+ID+".txt";
        Seller seller = new Seller();
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
        String categoryName = shop.getCategory().name();
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
            writer.write("Goods:");
            writer.newLine();


            writer.close();
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
    public static Shop readShopFile(String ID){

    }

    //================================================== Product ====================================================================
    public static void writeProductFile(Product product){
        String name = product.getName();
        String ID = product.getID();
        String price = Float.toString(product.getPrice());

        String filePath = "Data/Product/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(price);
            writer.close();
            System.out.println("Save successfully!");
        } catch(IOException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public static Product readProductFile(String ID){

    }

    //================================================== Bill ========================================================================
    public static void writeBillFile(Bill bill){
        String name = bill.getName();
        String ID = bill.getID();
        String price = Float.toString(bill.getPrice());
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
    public static Bill readBilFile(String ID){
        String filePath = "Data/Bill/"+ID+".txt";

        Bill bill = new Bill(" ", " ",0);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String id = line.trim();
                    String name = line.trim();
                    Float price = Float.parseFloat(line.trim());
                    Map<String, Integer> purchasedBy = new HashMap<>();

                    if(line.equals("Purchased by:")){
                        while((line = reader.readLine())!=null){
                            String[] parts = line.split(", ");
                            String customerID = parts[0].split(": ")[1];
                            int amount = Integer.parseInt(parts[1].split(": ")[1]);
                            purchasedBy.put(customerID, amount);
                        }
                    }

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