package Interface;
import java.util.HashMap;
import java.util.Map;
import Transaction.Product;

public interface ManageProduct {
    public void addProduct(Map<String, Integer> list, String id, int amount);

    public void removeProduct(Map<String, Integer> list, String id, int amount);

    public void clearProduct(Map<String, Integer> list);

    public void reduceProduct(Map<String, Integer> list, String id, int amount);

    public void increaseProduct(Map<String, Integer> list, String id, int amount);
}