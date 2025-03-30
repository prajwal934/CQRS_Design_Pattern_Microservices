package com.praj.test.service;

import com.praj.test.dto.ProductEvent;
import com.praj.test.model.Product;
import com.praj.test.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    public ResponseEntity<ResponseStructure<Product>> saveProduct(ProductEvent productEvent);
    public ResponseEntity<ResponseStructure<Product>> updateByProductId(String pId , ProductEvent productEvent);
    public ResponseEntity<ResponseStructure<Product>> deleteByProductId(String pId);

}
