package com.example.demo.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductApprovalQueue;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductApprovalQueueRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ProductApprovalQueueRepository approvalRepository;
	
	public static String SEARCH_QUERY = "SELECT * FROM PRODUCT ";
	
	public ProductEntity createProduct(ProductEntity product) {
		ProductEntity savedProduct = null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if(product.getPrice()> 5000) {
			product.setStatus("pending");
			product.setCreateDate(timestamp);
			savedProduct = repository.save(product);
			ProductApprovalQueue queue = new ProductApprovalQueue();
			queue.setId(savedProduct.getId());
			queue.setCreateDate(timestamp);
			queue.setStatus("pending");
			approvalRepository.save(queue);
		}else {
			product.setStatus("approved");
			product.setCreateDate(timestamp);
			savedProduct = repository.save(product);	
		}
		
		return savedProduct;
		
	}
	
	public List<ProductEntity> getProducts(){
		
		return repository.findAll();
	}
	
   public void deleteProducts(Integer id){
	   ProductEntity product  = repository.getById(id);
	    repository.delete(product);
		
	}
   
   public List<ProductApprovalQueue> getApprovalQueueProducts(){
	   
	   return approvalRepository.getApprovalProducts();
   }
   
   public void updateApprovalQueueProducts(Integer id){
	   ProductApprovalQueue queue = approvalRepository.getById(id);
	   queue.setStatus("approved");
	   approvalRepository.save(queue);
	   ProductEntity product  = repository.getById(id);
	   product.setStatus("approved");
	   repository.save(product);
	   
   }
   
   public void updateRejectQueueProducts(Integer id){
	   ProductApprovalQueue queue = approvalRepository.getById(id);
	   queue.setStatus("rejected");
	   approvalRepository.save(queue);
	   ProductEntity product  = repository.getById(id);
	   product.setStatus("rejected");
	   repository.save(product);
	  
   }
   
   
   public List<ProductEntity> searchProduct(String productName,String minPrice,String maxPrice,
		   String minPostedDate,String maxPostedDate){
	   
	   List<ProductEntity> products = new ArrayList<ProductEntity>();
	   if(productName != null && minPrice != null && maxPrice != null && minPostedDate != null && maxPostedDate != null) {
		   Integer minValue=Integer.parseInt(minPrice);
		   Integer maxValue=Integer.parseInt(maxPrice);
	       Timestamp timestampMinDate = convertStringToTimestamp(minPostedDate);
	       Timestamp timestampMaxDate = convertStringToTimestamp(maxPostedDate);
		   products = repository.findByNameAndPriceBetweenAndCreateDateBetween(productName, minValue, maxValue, timestampMinDate, timestampMaxDate);
	   }else if(productName != null && minPrice != null && maxPrice != null) {
		   Integer minValue=Integer.parseInt(minPrice);
		   Integer maxValue=Integer.parseInt(maxPrice);
		   products = repository.findByNameAndPriceBetween(productName,minValue,maxValue);
	   }else if(productName != null && minPostedDate != null && maxPostedDate != null){
		   Timestamp timestampMinDate = convertStringToTimestamp(minPostedDate);
	       Timestamp timestampMaxDate = convertStringToTimestamp(maxPostedDate);
	       products = repository.findByNameAndCreateDateBetween(productName, timestampMinDate, timestampMaxDate);   
	   }else if(minPrice != null && maxPrice != null && minPostedDate != null && maxPostedDate != null) {
		   Integer minValue=Integer.parseInt(minPrice);
		   Integer maxValue=Integer.parseInt(maxPrice);
		   Timestamp timestampMinDate = convertStringToTimestamp(minPostedDate);
	       Timestamp timestampMaxDate = convertStringToTimestamp(maxPostedDate);
	       repository.findByPriceBetweenAndCreateDateBetween(minValue, maxValue, timestampMinDate, timestampMaxDate);
	   }else if(productName != null) {
		   products = repository.findByName(productName);
	   }else if(minPrice != null && maxPrice != null) {
		   Integer minValue=Integer.parseInt(minPrice);
		   Integer maxValue=Integer.parseInt(maxPrice);
		   products = repository.findByPriceBetween(minValue, maxValue);
	   }else if(minPostedDate != null && maxPostedDate != null) {
		   Timestamp timestampMinDate = convertStringToTimestamp(minPostedDate);
	       Timestamp timestampMaxDate = convertStringToTimestamp(maxPostedDate);
	       products = repository.findByCreateDateBetween(timestampMinDate, timestampMaxDate);
	   }
	   else {
		   products = repository.findAll();
	   }
	   return products;
	   }
   
   public void updateProducts(Integer id,ProductEntity entity) {
	   
	   ProductEntity product  = repository.getById(id);
	   if(entity.getName() != null) {
		   product.setName(entity.getName());
	   }
	   if(entity.getPrice() >0) {
		   if(product.getPrice() * 150/100 <entity.getPrice()) {
			   product.setPrice(entity.getPrice());
			   product.setStatus("pending");
			   repository.save(product);
			   ProductApprovalQueue queue = approvalRepository.getById(id);
			   queue.setStatus("pending");
			   approvalRepository.save(queue);
		   }else {
			   product.setPrice(entity.getPrice());
			   repository.save(product);  
		   }
	   }
	   
   }
   
   public static Timestamp convertStringToTimestamp(String dateString) {
       try {
           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           Date parsedDate = dateFormat.parse(dateString);
           return new Timestamp(parsedDate.getTime());
       } catch (ParseException e) {
           e.printStackTrace();
           return null; // Handle the parsing exception gracefully
       }
   }
   }