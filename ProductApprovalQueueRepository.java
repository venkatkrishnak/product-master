package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductApprovalQueue;
import com.example.demo.entity.ProductEntity;

@Repository
public interface ProductApprovalQueueRepository extends JpaRepository<ProductApprovalQueue, Integer>{
	
	@Query(nativeQuery = true, value = "SELECT * FROM PRODUCT_APPROVAL_QUEUE where status = 'pending'")
	List<ProductApprovalQueue> getApprovalProducts();
	

}
