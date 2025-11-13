package com.example.ShowProduct;

//這是API要回傳給前端的資料結構 APIがフロントエンドに送るデータ構造
public class ProductDto {
    private String productName;
    private String category;
    private double price;

    //專門用於從Product Entity轉換過來的建構子　モデル(エンティティ)から変換されたコンストラクターの利用
    public ProductDto(com.example.ShowProduct.Product product){
        this.productName = product.getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
    }

    //省略了Getter/Setter,但實際專業中通常會有　通常プロジェクトにあるGetter/Setterを省略
    public String getProductName(){return productName;}
    public String getCategory(){return category;}
    public double getPrice(){return price;}
}
