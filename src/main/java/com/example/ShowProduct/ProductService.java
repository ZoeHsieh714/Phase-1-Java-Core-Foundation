package com.example.ShowProduct;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    //模擬資料庫資料,作為Steam API的輸入源  仮にDBから取得したレコードをインプットとしてセット
    private final List<Product> mockDatabase = List.of(
            new Product(1L, "iPhone 15 Pro", "手機",36900.0, 50),
            new Product(2L, "MacBook Pro 14", "電腦",64900.0, 20),
            new Product(3L, "iPad Air 15 M3", "平板",24900.0, 30),
            new Product(4L, "AirPods Pro 2", "耳機",7480.0, 100),
            new Product(5L, "SONY A7 IV", "相機",79980.0, 10),
            new Product(6L, "Dyson V12", "家電",36900.0, 0),
            new Product(7L, "Java核心技術", "書籍",1100.0, 200),
            new Product(8L, "Spring Boot實戰", "書籍",880.0, 150)
            //カテゴリー➡手機：携帯、電腦：ノートパソコン、平板：タブレット、耳機：イヤホン、相機：デジカメ、家電：家電、書籍：本
    );


    /**
     *練習1: 轉換(map) 変換(map)
     *需求:取得所有商品，並轉換成ProductDto列表  全ての商品を取得し、ProductDtoリストに変換
     */
    public List<ProductDto> findAllProducts(){
        //從資料庫取得List<Product>,假設是productRepository.findAll();
        //【傳統for迴圈做法】
        //List<ProductDto> dtoList = new ArrayList<>();
        //for (Product product : products){
        //     dtoList.add(new ProductDto(product));
        //}
        //return dtoList;
        //【Stream API做法】
        return mockDatabase.stream()            //1.將List轉成Stream(流)
                //↓.map(product -> new ProductDto(product))的簡寫
                .map(ProductDto::new)           //2.轉換:將每個Product映射(map)成一個new ProductDto
                .collect(Collectors.toList());  //3.收集:將Stream收集回一個List
    }
    /**
     * 練習2: 過濾(filter)  フィルター(filter)
     * 需求: 找出所有「書籍」類別，且「有庫存」(stock > 0)的商品，並轉成DTO  商品カテゴリーが"本"、かつ在庫ある商品を取得しDTOに変換
     */
    public List<ProductDto> findBooksInStock(){
        return mockDatabase.stream()                                             //1.取得流
                //在這裡使用Product(Model/Entity)裡定義的getCategory(),getStock()
                .filter(product -> "書籍".equals(product.getCategory()))  //2.過濾:只保留類別為"書籍"的元素
                .filter(product -> product.getStock() > 0)               //3.再次過濾:只保留庫存 > 0 的元素
                .map(ProductDto::new)                                           //4.轉化:將剩下的Product轉成ProductDto
                .collect(Collectors.toList());                                  //5.收集成List
    }
    /**
     * 練習3: 彙總(collect + groupingBy)  まとめ(collect + groupingBy)
     * 需求:將所有商品「按類別分組」   全ての商品をカテゴリー別ごとにグループ化する
     * 回傳值會是 Map<String, List<Product>> (key是類別名稱，value是該類別的商品List)
     */
    public Map<String, List<Product>> groupProductsByCategory() {
        return mockDatabase.stream()
               // ↓.collect(Collectors.groupingBy(product -> product.getCategory()));
        .collect(Collectors.groupingBy(Product::getCategory));  //1.收集並分組
     }

     /**
     * 練習4: 查找與Optional(findFirst)  サーチ＆Optional(findFirst)
     * 需求:找出"任一個"價格高於50000元的商品(轉成DTO)   任意一つ価格が50000円以上の商品を探す（DTOに変換）
     */
     public Optional<ProductDto> findAnyExpensiveProduct() {
         return mockDatabase.stream()
                 .filter(product -> product.getPrice() > 50000.0)  //1.過濾:價格 > 50000
                 .map(ProductDto::new)                                    //2.轉換成DTO
                 .findFirst();                                            //3.找到第一個就停止(回傳Optional<ProductDto>)
     }

    /**
     * 練習5: 排序和轉換練習(sorted & map)  並び順＆変換練習(sorted & map)
     * 需求:找出所有「書籍」類別，並依照價格「由高到低」排列   カテゴリーが"本"の商品をピックアップし、価格の降順で並ぶ
     * 最後回傳它們的「商品名稱(String)」列表  "商品名称"(String)リストをリターンする
     */
    public List<String> findSortedBookNames(){
        return mockDatabase.stream()
                //1.中間操作(過濾):只保留「書籍」類別
                .filter(product -> "書籍".equals(product.getCategory()))

                //2.中間操作(排序):
                //Comparator.comparing(Product::getPrice)預設是正序(由低到高)
                //reversed() 將其反轉,變成倒序(由高到低)
                .sorted(Comparator.comparing(Product::getPrice).reversed())

                //3.中間操作(轉換):將Product物件轉換成String(商品名稱)
                .map(Product::getName)

                //4.終端操作(收集):將所有String收集成一個List
                .collect(Collectors.toList());
    }

    /**
     * 練習6: 過濾與彙總練習(filter & sum)  フィルター＆まとめ練習(filter & sum)
     * 需求:計算所有「庫存商品」(stock > 0)的「總價值」   全ての在庫商品(stock > 0)の総価格を計算する
     */
    public double calculateTotalStockValue(){
        return mockDatabase.stream()
                //1.中間操作(過濾):只保留庫存>0的商品
                .filter(product -> product.getStock() > 0)

                //2.中間操作(轉換為DoubleStream):將Stream<Product>轉換為專門處理的DoubleStream,
                //這樣可以避免自動裝箱(boxing)成Double物件,效能更好
                //↓.map(product -> product.getPrice() * product.getStock()) 這樣會回傳一個Steam<Double>,產生很多Double物件
                .mapToDouble(product -> product.getPrice() * product.getStock())

                //3.終端操作(加總):
                //.sum()是DoubleStream才有的專屬方法,直接回傳總和(double),
                // 無collect方法,有sum(),average(),max(),min()等高效數學運算方法
                .sum();
    }


}