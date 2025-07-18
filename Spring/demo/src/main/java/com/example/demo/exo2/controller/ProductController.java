package com.example.demo.exo2.controller;

import com.example.demo.exo2.model.Product;
import com.example.demo.exo2.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products")
    @ResponseBody
    public List<Product> displayAllProducts(){
        return productService.getAllProducts();
    }

    @RequestMapping("/product/{id}")
    @ResponseBody
    public Product displayProduct(@PathVariable("id") UUID id){
        return productService.getProductById(id);
    }

    @RequestMapping("/product/filter")
    @ResponseBody
    public List<Product> filterProducts(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice){

        if (category != null && maxPrice != null) {
            return productService.filterProductsByCategoryAndMaxPrice(category, maxPrice);
        } else if (category != null) {
            return productService.filterProductsByCategory(category);
        } else if (maxPrice != null) {
            return productService.filterProductsByMaxPrice(maxPrice);
        } else {
            return productService.getAllProducts();
        }
    }

}
