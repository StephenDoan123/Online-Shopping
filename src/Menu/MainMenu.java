package Menu;

import User.Customer;
import User.Seller;

import java.util.Scanner;

public class MainMenu {
    private Scanner scan = new Scanner(System.in);

    public void displayMainMenu(){
        String choice;
        while(true){
            System.out.println("-------- Online Shopping System --------");
            System.out.println("(1) Customer");
            System.out.println("(2) Seller");
            System.out.println("(3) Exit");
            System.out.print("Choose your account: ");
            choice = scan.next();
            if(choice.equalsIgnoreCase("1")){
                Customer customer = new Customer("", "", "");
                CustomerMenu customerMenu = new CustomerMenu(customer);
                customerMenu.displayAuthMenu();
                break;
            }
            else if(choice.equalsIgnoreCase("2")){
                Seller seller = new Seller("", "", "");
                SellerMenu sellerMenu = new SellerMenu(seller);
                sellerMenu.displayAuthMenu();
                break;
            }
            else if(choice.equalsIgnoreCase("3")){
                break;
            }
            else{
                System.out.println("Invalid choice. Please try again!");
                displayMainMenu();
            }
        }
    }
}