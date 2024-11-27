package Menu;

import java.sql.SQLOutput;
import java.util.Scanner;

public class MainMenu {
    public Scanner scan = new Scanner(System.in);

    public void displayMainMenu(){
        String choice;
        System.out.println("Welcome to Online Shopping System");
        System.out.println("(1) Customer");
        System.out.println("(2) Seller");
        System.out.print("Choose your account: ");
        choice = scan.nextLine();
        
    }
}