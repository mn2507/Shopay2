package com.example.shopay;

public class history {

    private String price, product;

    public history()
    {}

    public history(String price, String product) {
        this.price = price;
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
