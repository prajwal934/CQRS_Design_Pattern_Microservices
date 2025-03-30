package com.praj.test.serviceimpl;

import com.praj.test.dto.ProductEvent;
import com.praj.test.model.Product;
import com.praj.test.repository.ProductRepository;
import com.praj.test.service.ProductService;
import com.praj.test.utility.ResponseStructure;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ResponseStructure<Product> responseStructure;
    private ResponseStructure<List<Product>> rs;

    public ProductServiceImpl(ProductRepository productRepository, ResponseStructure<Product> responseStructure, ResponseStructure<List<Product>> rs) {
        this.productRepository = productRepository;
        this.responseStructure = responseStructure;
        this.rs = rs;
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(rs.setStatuscode(HttpStatus.OK.value())
                .setMessage("Product Data Fetched Successfully!!")
                .setData(productRepository.findAll()));
    }

@KafkaListener(topics = "product-event-topic", groupId = "product-event-group")
public void processProductEvents(ProductEvent productEvent) {
    Product product = productEvent.getProduct();

    if (productEvent.getEventType().equals("SaveProduct")) {
        productRepository.save(product);
    }

    if (productEvent.getEventType().equals("UpdateProduct")) {
        Product existingProduct = productRepository.findById(product.getPId())
                .orElseThrow(() -> new RuntimeException("Product Not Found with ID: " + product.getPId()));
        existingProduct.setPName(product.getPName());
        existingProduct.setPPrice(product.getPPrice());
        existingProduct.setPDescription(product.getPDescription());
        productRepository.save(existingProduct);
    }

    if (productEvent.getEventType().equals("DeleteProduct")) {
        Product existingProduct = productRepository.findById(product.getPId())
                .orElseThrow(() -> new RuntimeException("Product Not Found with ID: " + product.getPId()));
        productRepository.delete(existingProduct);
    }
}


}
