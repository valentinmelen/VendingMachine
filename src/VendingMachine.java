import java.util.List;

public interface VendingMachine {


    long selectItemAndGetPrice(Item item) throws SoldOutException;// return price for given 'Item'     exception: SoldOutException

    void insertCoin(Coin coin);// will add the given coin to 'currentBalance' (which will be defined in the implementation)

    List<Coin> refund() throws NotSufficientChangeException;

    PurchaseAndCoins<Item, List<Coin>> collectItemAndChange() throws NotSufficientChangeException, NotFullyPaidException;

    void reset();

}
