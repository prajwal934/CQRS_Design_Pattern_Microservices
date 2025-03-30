package com.praj.test.controller;

import com.praj.test.model.Product;
import com.praj.test.service.ProductService;
import com.praj.test.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

//    public ResponseEntity<ResponseStructure<Product>> createProduct(@RequestBody Product product) {
//        return productService.createProduct(product);
//    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts() {
        return productService.getAllProducts();
    }
}
