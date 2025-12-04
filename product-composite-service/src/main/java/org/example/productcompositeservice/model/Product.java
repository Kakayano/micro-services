package org.example.productcompositeservice.model;

public class Product {
    private int productId;
    private String name;
    private int weight;

    public Product(int productId, String name, int weight) {
        this.productId = productId;
        this.name = name;
        this.weight = weight;
    }
    public Product(){}

    public int getProductId() {
        return productId;
    }
    public String getName(){
        return name;
    }

    public int getWeight(){
        return weight;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}






