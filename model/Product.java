package model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageUrl;

    public Product(int id, String name, String description, double price, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }

    // Main method for testing
    public static void main(String[] args) {
        Product product = new Product(1, "Laptop", "A powerful gaming laptop", 999.99, "Electronics", "laptop.jpg");

        System.out.println("Product ID: " + product.getId());
        System.out.println("Name: " + product.getName());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Price: $" + product.getPrice());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Image URL: " + product.getImageUrl());
    }
}


