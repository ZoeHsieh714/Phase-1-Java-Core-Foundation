package com.example.ShowProduct;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository{
    //實務上會是extends JpaRepository<Product, Long>
    //因為是Mock Data
}
