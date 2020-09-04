package com.ibm.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.order.model.OrderDetails;

public interface OrderRepository extends JpaRepository<OrderDetails, Integer>{

	List<OrderDetails> findByCustomerName(String customerName);
}
