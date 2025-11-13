package com.example.ShowProduct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stream-exercises")
public class ProductRestController {

    private final ProductService ProductService;

    //透過建構子注入ShowProductService コンストラクター
    public ProductRestController(ProductService ProductService){
        this.ProductService = ProductService;
    }

    //呼叫練習1　呼び出し練習１
    @GetMapping("/products-all-dto")
    public List<ProductDto> exercise1_findAll(){
        return ProductService.findAllProducts();
    }

    //呼叫練習2　呼び出し練習２
    @GetMapping("/books-in-stocks")
    public List<ProductDto> exercise2_findBooksInStock(){
        return ProductService.findBooksInStock();
    }

    //呼叫練習3　呼び出し練習３
    @GetMapping("/group-by-category")
    public Map<String,List<Product>> exercise3_groupProductsByCategory(){
        return ProductService.groupProductsByCategory();
    }

    //呼叫練習4:處理optional　呼び出し練習４:optional処理
    @GetMapping("/expensive-products")
    public ResponseEntity<ProductDto> exercise4_findAnyExpensiveProduct(){
                //這是處理optional的一種常見方法:
                //如果optional裡有值,回傳200和DTO。如果沒有值,回傳404 Not Found
        return ProductService.findAnyExpensiveProduct()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //呼叫練習5　呼び出し練習５
    @GetMapping("/books-names-sorted")
    public List<String> exercise5_findSortedBookNames(){
        return ProductService.findSortedBookNames();
    }

    //呼叫練習6　呼び出し練習６
    @GetMapping("/total-stock-value")
    public double exercise6_calculateTotalStockValue(){
        return ProductService.calculateTotalStockValue();
    }

}
