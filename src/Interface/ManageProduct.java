package Interface;
import java.util.HashMap;
import java.util.Map;
import Transaction.Product;

public interface ManageProduct {
    public void addProduct(Map<Product, Integer> list, Product p, int amount);
    public void removeProduct(Map<Product, Integer> list, Product p, int amount);
}