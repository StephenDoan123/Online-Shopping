package Other;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;

import Transaction.Shop;
import User.Customer;
import User.Seller;
import Transaction.Product;

public class Utils {

    //=================================================== Function ========================================================================
    public String enumToString(Shop shop){
        return shop.getCategory().name();
    }
    public Category stringToCategory(String name){
        Category category = Category.valueOf(Category.class, name);
        return category;
    }
    public static void showCategories(){

    }


    //==================================================  File =======================================================================
    //================================================== Customer ===================================================================
    public static void writeCustomerFile (Customer customer){
        String name = customer.getName();
        String ID = Integer.toString(customer.getID());
        String password = customer.getPassword();
        String balance = Double.toString(customer.getBalance());
        Map<Integer, Integer> cart = customer.getCart();
        Map<Integer, Integer> purchased = customer.getPurchased();

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
            for(Integer id: cart.keySet()){
                writer.write("ID: "+Integer.toString(id)+", amount: "+Integer.toString(cart.get(id)));
                writer.newLine();
            }
            writer.write("Purchased:");
            writer.newLine();
            for(Integer id: purchased.keySet()){
                writer.write("ID: "+Integer.toString(id)+", amount: "+Integer.toString(purchased.get(id)));
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

        Customer customer = new Customer();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    int id = Integer.parseInt(line.trim());
                    String name = reader.readLine().trim();
                    String password = reader.readLine().trim();
                    double balance = Double.parseDouble(line.trim());
                    customer.setName(name);
                    customer.setPassword(password);
                    customer.setID(id);
                    customer.setBalance(balance);


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
        String ID = Integer.toString(seller.getID());
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
                    int id = Integer.parseInt(line.trim());
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
        String ID = Integer.toString(shop.getID());
        String categoryName = shop.getCategory().name();
        Map<Integer, Integer> goods = shop.getGoods();
        Map<Integer, Integer> bills = shop.getBills();

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
        String ID = Integer.toString(product.getID());
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

}