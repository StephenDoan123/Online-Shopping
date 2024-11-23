package Utils;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

import User.Customer;
import User.Seller;
import Transaction.Product;

public class Utils {

    //=================================================== Function ========================================================================
    public static void showCategories(){

    }

    //==================================================  File =======================================================================
    //================================================== Customer ===================================================================
    public static void writeCustomerFile (Customer customer){
        String name = customer.getName();
        String ID = Integer.toString(customer.getID());
        String balance = Double.toString(customer.getBalance());

        String filePath = "Data/Customer/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(balance);
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
                    double balance = Double.parseDouble(line.trim());
                    customer.setName(name);
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
        String balance = Double.toString(seller.getBalance());

        String filePath = "Data/Seller/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
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
                    double balance = Double.parseDouble(line.trim());
                    seller.setName(name);
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
    //================================================== Product ====================================================================
    public static void writeProductFile(Product product){
        String name = product.getName();
        String ID = Integer.toString(product.getID());
        String price = Float.toString(product.getPrice());
        String category = product.getCategory();

        String filePath = "Data/Product/"+ID+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(ID);
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(balance);
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