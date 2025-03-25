package com.sims.product.service;

import com.sims.product.entity.Product;
import com.sims.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean updateStock(Long productId, int quantity) {
        return productRepository.findById(productId)
                .map(product -> {
                    if (quantity >= 0) {
                        product.setStock(quantity);
                        productRepository.save(product);
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }

    public boolean hasStock(Long productId, int requiredQuantity) {
        return productRepository.findById(productId)
                .map(product -> product.getStock() >= requiredQuantity)
                .orElse(false);
    }

    public boolean reduceStock(Long productId, int quantity) {
        return productRepository.findById(productId)
                .map(product -> {
                    if (product.getStock() >= quantity) {
                        product.setStock(product.getStock() - quantity);
                        productRepository.save(product);
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }
}
