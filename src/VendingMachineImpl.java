import java.util.Collections;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {

    private Inventory<Coin> cashInventory = new Inventory<Coin>(); // cash/coins inventory inside the VM
    private Inventory<Item> itemInventory = new Inventory<Item>();// products inventory inside the VM
    private long totalSales;//stored cash since the VM is initialized
    private Item currentItem; // selected 'Item' to be purchased
    private long currentBalance; // current amount of money inserted by a user

    public VendingMachineImpl() {
        initialize();
    }

    private void initialize() {
        for (Coin coin : Coin.values()) {
            cashInventory.put(coin, 5);
        }
        for (Item item : Item.values()) {
            itemInventory.put(item, 5);
        }
    }

    @Override
    public long selectItemAndGetPrice(Item item) throws SoldOutException { //SoldOutException
        if (itemInventory.hasItem(item)) {
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold out! Please buy another item!");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getValue();
        cashInventory.add(coin);
    }

    private void updateCashInventory(List change) {
        for (Coin coin : change) {
            cashInventory.decrease(coin);
        }
    }

    private boolean isFullyPaid() {//returns 'true' in case 'currentBalance' is >= currentItem's price, 'false' otherwise
        if (currentBalance >= currentItem.getPrice()) {
            return true;
        } else {
            return false;
        }
    }

    private List<Coin> getChange(long amount) {

        // calculate the amount of money for change

        List<Coin> changes = Collections.EMPTY_LIST;//use an empty List of Coin objects to keep the result
        long balance = amount;
        while (balance > 0) {
            if (balance >= Coin.FIFTY.getValue() && cashInventory.hasItem(Coin.FIFTY)) {
                changes.add(Coin.FIFTY);
                balance = balance - Coin.FIFTY.getValue();
                continue;
            } else if (balance >= Coin.TWENTY.getValue() && cashInventory.hasItem(Coin.TWENTY)) {
                changes.add(Coin.TWENTY);
                balance = balance - Coin.TWENTY.getValue();
                continue;
            } else if (balance >= Coin.TEN.getValue() && cashInventory.hasItem(Coin.TEN)) {
                changes.add(Coin.TEN);
                balance = balance - Coin.TEN.getValue();
                continue;
            } else if (balance >= Coin.FIVE.getValue() && cashInventory.hasItem(Coin.FIVE)) {
                changes.add(Coin.FIVE);
                balance = balance - Coin.FIVE.getValue();
                continue;
            }
            return changes;
        }

        throw new NotSufficientChangeException("Not sufficient change!Please try another product!");

    }

    public boolean hasSufficientChangeForAmount(long amount) {
        boolean hasChange = true;
        try {
            getChange(amount);
        } catch (NotSufficientChangeException e) {
            e.printStackTrace();
            return hasChange = false;
        }
        return hasChange;
    }

    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
    }

    public Item collectItem() throws NotSufficientChangeException, NotFullyPaidException {
        if (isFullyPaid()) {
            if (hasSufficientChange()) {
                itemInventory.decrease(currentItem);
                return currentItem;
            }
            throw new NotSufficientChangeException("Not Sufficient change in Inventory!");
        }
        throw new NotFullyPaidException("Price not full paid, remaining :", currentItem.getPrice() - currentBalance);

    }

    public List<Coin> collectChange() throws NotSufficientChangeException {
        long amount= currentBalance - currentItem.getPrice();
        if (amount != 0) {
            List<Coin> collectChange = getChange(amount);
            updateCashInventory(collectChange);
            currentBalance = 0;
            currentItem = null;
            return collectChange;
        }
        throw new NotSufficientChangeException("Not Sufficient Change Inventory!");

    }

    public PurchaseAndCoins<Item, List<Coin>> collectItemAndChange() {
        totalSales = totalSales + currentItem.getPrice();
        Item item = collectItem();
        List<Coin> list_of_coins = collectChange();
        return new PurchaseAndCoins<Item, List<Coin>>(item, list_of_coins);

    }

    public List<Coin> refund() throws NotSufficientChangeException {
        if (currentBalance != 0) {
            List<Coin> refund = getChange(currentBalance);
            updateCashInventory(refund);
            currentBalance = 0;
            currentItem = null;
            return refund();
        }
        throw new NotSufficientChangeException("Not Sufficient Change Exception in Refund");

    }

    @Override
    public void reset() {
        itemInventory.clear();
        cashInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;
    }

    public void printStats() {//display the current status of the VM: totalSales, current Item inventory, current Cash Inventory

        System.out.println("Total sales: " + getTotalSales());
        System.out.println("Current item inventory: " + itemInventory);
        System.out.println("Current cash inventory: " + cashInventory);

    }

    public long getTotalSales() {
        return totalSales;
    }
}
