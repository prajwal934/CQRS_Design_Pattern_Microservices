package com.praj.test.serviceimpl;

import com.praj.test.dto.ProductEvent;
import com.praj.test.model.Product;
import com.praj.test.repository.ProductRepository;
import com.praj.test.service.ProductService;
import com.praj.test.utility.ResponseStructure;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {


    private KafkaTemplate<String, Object> kafkaTemplate;

    private ProductRepository productRepository;
    private ResponseStructure<Product> responseStructure;

    public ProductServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, ProductRepository productRepository, ResponseStructure<Product> responseStructure) {
        this.kafkaTemplate = kafkaTemplate;
        this.productRepository = productRepository;
        this.responseStructure = responseStructure;
    }

    @Override
    public ResponseEntity<ResponseStructure<Product>> saveProduct(ProductEvent productEvent) {
        Product savedProduct = productRepository.save(mapToProduct(productEvent.getProduct()));
//         Create a ProductEvent
        ProductEvent event = new ProductEvent("SaveProduct", savedProduct);
//        Publish Event to kafka
        kafkaTemplate.send("product-event-topic", event);
        return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
                .setMessage("Product Data Saved Successfully!!")
                .setData(savedProduct));
    }
    /*This is the helper method to save the product data*/
    private Product mapToProduct(Product product) {
        String randomProductId = UUID.randomUUID().toString();
        return Product.builder()
                .pName(product.getPName())
                .pDescription(product.getPDescription())
                .pPrice(product.getPPrice())
                .pId(randomProductId).build();
    }

//    @Transactional
    @Override
    public ResponseEntity<ResponseStructure<Product>> updateByProductId(String pId, ProductEvent productEvent) {
        return productRepository.findById(pId).map(existingProduct -> {
            // Extract updated product details from ProductEvent
            Product newProduct = productEvent.getProduct();
            // Map new product details while keeping the same pId
            Product updateProduct = mapToProduct(newProduct);
            updateProduct.setPId(existingProduct.getPId());
            // Save updated product
            Product updatedProduct = productRepository.save(updateProduct);
            // Publish update event to Kafka
            ProductEvent event = new ProductEvent("UpdateProduct", updatedProduct);
            kafkaTemplate.send("product-event-topic", event);
            return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
                    .setMessage("Product Data Updated Successfully!!")
                    .setData(updatedProduct));
        }).orElseThrow(() -> new RuntimeException("Product Not Found with ID: " + pId));
    }


//    @Transactional
    @Override
    public ResponseEntity<ResponseStructure<Product>> deleteByProductId(String pId) {
        Optional<Product> optional = productRepository.findById(pId);
        return optional.map(product -> {
            // Delete the product from the repository
            productRepository.delete(product);
            // Publish a delete event to Kafka
            ProductEvent event = new ProductEvent("DeleteProduct", product);
            kafkaTemplate.send("product-event-topic", event);
            return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
                    .setMessage("Product Data Deleted Successfully!!"));
        }).orElseThrow(() -> new RuntimeException("Product Not Found with ID: " + pId));
    }

}
