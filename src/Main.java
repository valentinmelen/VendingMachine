import java.util.List;

public class Main {

    public static void main(String[] args) throws SoldOutException {
        VendingMachineImpl vm = new VendingMachineImpl();

        vm.printStats();
        vm.selectItemAndGetPrice(Item.MARS);

        vm.insertCoin(Coin.TEN);
        vm.insertCoin(Coin.TWENTY);

        vm.collectItemAndChange();

        vm.printStats();


    }
}
