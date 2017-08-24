package com.example.job.nguo.model;

/**
 * Created by JOB on 8/24/2017.
 */

public class ProductEntry {

    private String productName;
    private String imageUrl;
    private String txtShopAll;
    private String txtTagMen;
    private String productUrl;
    private String menProductsPage;
    private String txtShopNow;
    private String price;

    public ProductEntry() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTxtShopAll() {
        return txtShopAll;
    }

    public void setTxtShopAll(String txtShopAll) {
        this.txtShopAll = txtShopAll;
    }

    public String getTxtTagMen() {
        return txtTagMen;
    }

    public void setTxtTagMen(String txtTagMen) {
        this.txtTagMen = txtTagMen;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getMenProductsPage() {
        return menProductsPage;
    }

    public void setMenProductsPage(String menProductsPage) {
        this.menProductsPage = menProductsPage;
    }

    public String getTxtShopNow() {
        return txtShopNow;
    }

    public void setTxtShopNow(String txtShopNow) {
        this.txtShopNow = txtShopNow;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    //    "product-name":"MEN'S BETTER THAN NAKED&trade; JACKET",
//            "product-image-url":"http://images.thenorthface.com/is/image/TheNorthFace/236x204_CLR/mens-better-than-naked-jacket-AVMH_LC9_hero.png",
//            "header-top-right-text":"Shop All",
//            "header-top-left-text":"Men's",
//            "product-url":"http://www.thenorthface.com/catalog/sc-gear/men-39-s-better-than-naked-8482-jacket.html",
//            "header-top-right-url":"http://www.thenorthface.com/en_US/shop-mens/",
//            "product-cta-text":"Shop Now",
//            "price":"$60.50"
}
