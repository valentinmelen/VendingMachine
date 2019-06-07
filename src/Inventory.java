import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {

    private Map<T, Integer> inventory;//T- produse sau monede. Integer- cantitati


    public Inventory() {
        inventory = new HashMap<>();
    }


    public int getQuantity(T item) {// returns quantity for the given item
        Integer quantity = inventory.get(item);
        return quantity == null ? 0 : quantity;
    }

    public void add(T item) {// increase quantity for the given item
        int count = inventory.getOrDefault(item, 0);
        inventory.put(item, count + 1);
    }

    public boolean hasItem(T item) {// returns 'true' in case given item exists inside VM, 'false' otherwise
        return getQuantity(item) > 0;
    }

    public void decrease(T item) {// decrease quantity for given item
        if (hasItem(item)) {
            int count = inventory.get(item);
            inventory.put(item, count - 1);
        }

    }

    public void put(T item, int quantity) {// put a specified quantity from a specified item
        inventory.put(item, quantity);
    }

    public void clear() {//clear inventory
        inventory.clear();
    }
}
