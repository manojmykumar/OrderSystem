package com.ibm.order.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.order.item.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

	OrderItem findByProductCode(String productCode);
}
