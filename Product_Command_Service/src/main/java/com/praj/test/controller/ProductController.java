package com.praj.test.controller;

import com.praj.test.dto.ProductEvent;
import com.praj.test.model.Product;
import com.praj.test.service.ProductService;
import com.praj.test.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Product>> saveProduct(@RequestBody ProductEvent productEvent) {
        return productService.saveProduct(productEvent);
    }

    @PutMapping(value = "/{pId}")
    public ResponseEntity<ResponseStructure<Product>> updateByProductId(@PathVariable String pId, @RequestBody ProductEvent productEvent) {
        return productService.updateByProductId(pId, productEvent);
    }

    @DeleteMapping("/{pId}")
    public ResponseEntity<ResponseStructure<Product>> deleteByProductId(@PathVariable String pId) {
        return productService.deleteByProductId(pId);
    }
}
