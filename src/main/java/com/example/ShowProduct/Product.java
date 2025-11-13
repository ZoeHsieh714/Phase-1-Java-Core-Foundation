package com.example.ShowProduct;

//假設這是從資料庫撈出來的結構 仮にDBから取得したデータ構造
public class Product {
        private Long id;
        private String name;        //商品名稱　商品名称
        private String category;    //商品類別　カテゴリー
        private double price;       //價格　価格
        private int stock;          //庫存　在庫

        public Product(Long id, String name, String category, double price, int stock){
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }

        //Steam API 會用到這些getters，非常重要***　Steam APIが利用するgetters,とても重要
        public Long getId() {return id;}
        public String getName(){return name;};
        public String getCategory(){return category;}
        public double getPrice(){return price;}
        public int getStock(){return stock;}

    }

