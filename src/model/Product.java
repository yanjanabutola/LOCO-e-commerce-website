package model;

public class Product {
    private int id;
    private String productName;
    private String urlSlug;
    private String description;
    private String status;

    public Product(int id, String productName, String urlSlug, String description, String status) {
        this.id = id;
        this.productName = productName;
        this.urlSlug = urlSlug;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public String getProductName() {
        return productName;
    }
    public String getUrlSlug() {
        return urlSlug;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
}
