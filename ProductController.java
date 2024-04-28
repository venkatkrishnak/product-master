package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ProductApprovalQueue;
import com.example.demo.entity.ProductEntity;
import com.example.demo.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/api/products")
	public ProductEntity createProduct(@RequestBody ProductEntity product) {
		ProductEntity product1 = productService.createProduct(product);
		return product1;
		
	}
	
	@GetMapping("/api/products")
	public List<ProductEntity> getProducts() {	 
		return productService.getProducts();
		
	}
	
	@DeleteMapping("/api/products/{productId}")
	public String deleteProduct(@PathVariable("productId") Integer id) {
		productService.deleteProducts(id);
		return "product deleted : "+id;
		
	}
	@GetMapping("/api/products/approval-queue")
	public List<ProductApprovalQueue> getQueueProducts() {	 
		return productService.getApprovalQueueProducts();
		
	}
	
	@PutMapping("/api/products/approval-queue/{approvalId}/approve")
	public String upadteQueueProductsApprovals(@PathVariable("approvalId") Integer id) {	 
		productService.updateApprovalQueueProducts(id);
		return "product approved : "+id;
		
	}
	
	@PutMapping("/api/products/approval-queue/{approvalId}/reject")
	public String upadteQueueProductRejects(@PathVariable("approvalId") Integer id) {	 
		productService.updateRejectQueueProducts(id);
		return "product rejected : "+id;
		
	}
	
	@GetMapping("/api/products/search")
	public List<ProductEntity> searchProducts(@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "minPrice", required = false) String minPrice,
			@RequestParam(value = "maxPrice", required = false) String maxPrice,
			@RequestParam(value = "minPostedDate", required = false) String minPostedDate,
			@RequestParam(value = "maxPostedDate", required = false) String maxPostedDate) {
		
		return productService.searchProduct(productName,minPrice,maxPrice,minPostedDate,maxPostedDate);
		
	}
	
	@PutMapping("/api/products/{productId}")
	public String upadteProducts(@PathVariable("productId") Integer id,@RequestBody ProductEntity product) {	 
		productService.updateProducts(id,product);
		return "product updated : "+id;
		
	}
	
}
