package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductEntity;
import com.example.demo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
	
	List<ProductEntity> findByName(String name);
	List<ProductEntity> findByNameAndPriceBetween(String name,Integer minPrice,Integer maxPrice);
	List<ProductEntity> findByNameAndPriceBetweenAndCreateDateBetween(String name,Integer minPrice,Integer maxPrice,Timestamp minDate,Timestamp maxDate);
	List<ProductEntity> findByNameAndCreateDateBetween(String name,Timestamp minDate,Timestamp maxDate);
	List<ProductEntity> findByPriceBetweenAndCreateDateBetween(Integer minPrice,Integer maxPrice,Timestamp minDate,Timestamp maxDate);
	List<ProductEntity> findByCreateDateBetween(Timestamp minDate,Timestamp maxDate);
	List<ProductEntity> findByPriceBetween(Integer minPrice,Integer maxPrice);

}
