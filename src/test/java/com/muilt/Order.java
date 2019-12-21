package muilt;

/**
 * @author chenbin
 * @ClassName Order
 * @Description TODO
 * @date 2019/12/6 20:35
 * @Vsersion
 */
public class Order {

    private String id;
    private String name;
    private double price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
