package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) throws Exception {
        if (product.getPrice() == null) {
            throw new Exception("Product price cannot be null");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) throws Exception {
        Product updatedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));
        updatedProduct.setProductDescription(product.getProductDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setImageUrl(product.getImageUrl());
        updatedProduct.setCategory(product.getCategory());
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long productId) throws Exception {
        Product deletedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));
        productRepository.delete(deletedProduct);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));
    }
}
