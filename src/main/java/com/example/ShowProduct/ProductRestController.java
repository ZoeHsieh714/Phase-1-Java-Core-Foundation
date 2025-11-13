package com.example.ShowProduct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/stream-exercises")  //將API相關的需求放在/api/stream-exercises路徑下
public class ProductRestController {

    private final ProductService productService;

    //透過建構子注入ShowProductService コンストラクター
    public ProductRestController(ProductService productService){
        this.productService = productService;
    }

    //呼叫練習1　呼び出し練習１
    @GetMapping("/products-all-dto")
    public List<ProductDto> exercise1_findAll(){
        return productService.findAllProducts();
    }

    //呼叫練習2　呼び出し練習２
    @GetMapping("/books-in-stocks")
    public List<ProductDto> exercise2_findBooksInStock(){
        return productService.findBooksInStock();
    }

    //呼叫練習3　呼び出し練習３
    @GetMapping("/group-by-category")
    public Map<String,List<Product>> exercise3_groupProductsByCategory(){
        return productService.groupProductsByCategory();
    }

    //呼叫練習4:處理optional　呼び出し練習４:optional処理
    @GetMapping("/expensive-products")
    public ResponseEntity<ProductDto> exercise4_findAnyExpensiveProduct(){
                //這是處理optional的一種常見方法:
                //如果optional裡有值,回傳200和DTO。如果沒有值,回傳404 Not Found
        return productService.findAnyExpensiveProduct()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //呼叫練習5　呼び出し練習５
    @GetMapping("/books-names-sorted")
    public List<String> exercise5_findSortedBookNames(){
        return productService.findSortedBookNames();
    }

    //呼叫練習6　呼び出し練習６
    @GetMapping("/total-stock-value")
    public double exercise6_calculateTotalStockValue(){
        return productService.calculateTotalStockValue();
    }

    //呼叫Optional練習1: findProductByIdOrThrow  呼び出しOptional練習1:　findProductByIdOrThrow
    @GetMapping("/product/{id}")
    public ProductDto exercise1_findById(@PathVariable Long id) {
        try {
            return productService.findProductByIdOrThrow(id);
        } catch (NoSuchElementException e) {
            //當Service 層拋出自定義異常時，Controller 將其轉換為 HTTP 404 (Not Found)
            //這是Spring Boot 處理業務異常的一種常見方式。
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // 呼叫Optional練習2: findProductByNameOrDefault　呼び出しOptional練習２:findProductByIdOrThrow
    @GetMapping("/search-default")
    public ProductDto exercise2_findOrDefault(@RequestParam String keyword) {
        //Service層已經保證回傳的不是null，而是DTO (可能是預設的 DTO)
        return productService.findProductByNameOrDefault(keyword);
    }

    // 呼叫Optional練習 3: logProductStatus　　呼び出しOptional練習３：logProductStatus
    @PostMapping("/log-status/{id}")
    public ResponseEntity<Void> exercise3_logStatus(@PathVariable Long id) {
        productService.logProductStatus(id);
        //無論Log成功或失敗，都回傳200 OK (或 204 No Content 更佳)
        return ResponseEntity.noContent().build();
    }

}
