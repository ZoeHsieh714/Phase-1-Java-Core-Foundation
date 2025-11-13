package com.example.ShowProduct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web") //將網頁相關的需求放在/web路徑下
public class ProductWebController {
    private final ProductService productService;

    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Thymeleaf 練習：展示練習 1 和 2 的結果  Thymeleafの練習：ディスプレイ練習１＆練習２の結果
     * URL：http://localhost:8080/web/products
     */
    @GetMapping("/products")
    public String showProductsPage(Model model) {

        //呼叫練習1的Stream.map轉換結果
        List<ProductDto> allProducts = productService.findAllProducts();

        //呼叫練習2的Stream.filter過濾結果
        List<ProductDto> inStockBooks = productService.findBooksInStock();

        //將資料物件加入Model，讓Thymeleaf模板可以使用
        model.addAttribute("allProducts", allProducts);
        model.addAttribute("books", inStockBooks);

        //回傳視圖名稱 (Thymeleaf會自動尋找 src/main/resources/templates/products.html)
        return "products";
    }
}
