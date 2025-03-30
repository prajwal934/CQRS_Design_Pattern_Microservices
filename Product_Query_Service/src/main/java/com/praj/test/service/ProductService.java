package com.praj.test.service;

import com.praj.test.model.Product;
import com.praj.test.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

//    public ResponseEntity<ResponseStructure<Product>> createProduct(Product product);
    public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts();
}
