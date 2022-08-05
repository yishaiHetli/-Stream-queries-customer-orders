package DL;



import java.text.ParseException;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

public class Product {
    private long ProductId;
    private String name;
    private ProductCategory category;
    private double price;

    public Product(String orderInfo) {
        String[] data = orderInfo.split(" ");
        try {
            setProductId(parseLong(data[1]));
            setName(data[2]);
            setCategory(ProductCategory.valueOf(data[4]));
            setPrice(parseDouble(data[6]));
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
        }
    }

    public Product(long PId, String Pname, ProductCategory Pcategory, double Pprice) {
        setProductId(PId);
        setName(Pname);
        setCategory(Pcategory);
        setPrice(Pprice);

    }


    public String toString() {
        return "Product: " + getProductId() + " " + getName() + " category: " + getCategory() + " price: " + getPrice() + "\n";
    }

    public long getProductId() {
        return ProductId;
    }

    public void setProductId(long productId) {
        ProductId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
