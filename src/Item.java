public enum Item {

    MARS(25, "MARS"),
    TWIX(35,"TWIX"),
    BOUNTY(45,"BOUNTY");

    private int price;
    private String name;

    Item(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
