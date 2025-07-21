package com.example.demo.exo2.controller;

import com.example.demo.exo2.model.Product;
import com.example.demo.exo2.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/detail")
    public String displayProductDetail(@RequestParam("id") UUID id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/all")
    public String displayAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("isFiltered", false);
        return "products";
    }

    @GetMapping("/filter")
    public String displayFilteredProducts(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            Model model) {

        List<Product> filteredProducts = productService.productWithFilter(category, maxPrice);
        model.addAttribute("products", filteredProducts);
        model.addAttribute("isFiltered", true);
        model.addAttribute("category", category);
        model.addAttribute("maxPrice", maxPrice);
        return "products";
    }

//    @RequestMapping("/products")
//    @ResponseBody
//    public List<Product> displayAllProducts(){
//        return productService.getAllProducts();
//    }
//
//    @RequestMapping("/product/{id}")
//    @ResponseBody
//    public Product displayProduct(@PathVariable("id") UUID id){
//        return productService.getProductById(id);
//    }
//
//    @RequestMapping("/product/filter")
//    @ResponseBody
//    public List<Product> filterProducts(
//            @RequestParam(value = "category", required = false) String category,
//            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice){
//
//        return productService.productWithFilter(category, maxPrice);
//    }
}
