package Interface;
import java.util.HashMap;
import java.util.Map;
import Transaction.Product;

public interface ManageProduct {
    public void addProduct(Map<Integer, Integer> list, int id, int amount);
    public void removeProduct(Map<Integer, Integer> list, int id, int amount);
    public void clearProduct(Map<Integer, Integer> list);
}