package Labb2;

import java.math.BigDecimal;
import java.util.Objects;

public class Product  {

    private String category;
    private String name;
    private String brand;
    private BigDecimal price;
    private int size;
    private int quantity;
    private int eanCode;



    public int getEanCode() {
        return eanCode;
    }

    public void setEanCode(int eanCode) {
        this.eanCode = eanCode;
    }

    public Product(String category, String name, String brand, BigDecimal price, int size, int quantity, int eanCode) {
        this.category = category;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.eanCode = eanCode;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getSize() == product.getSize() && getQuantity() == product.getQuantity() && getEanCode() == product.getEanCode() && Objects.equals(getCategory(), product.getCategory()) && Objects.equals(getName(), product.getName()) && Objects.equals(getBrand(), product.getBrand()) && Objects.equals(getPrice(), product.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getName(), getBrand(), getPrice(), getSize(), getQuantity(), getEanCode());
    }

    @Override
    public String toString() {
        return "Product{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", size=" + size +
                ", quantity=" + quantity +
                ", eanCode=" + eanCode +
                '}';



    }
}
