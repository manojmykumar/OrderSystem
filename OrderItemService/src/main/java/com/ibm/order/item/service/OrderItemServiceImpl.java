package com.ibm.order.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.order.item.model.OrderItem;
import com.ibm.order.item.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl {

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	/**
	 * Save the Order Item into H2 DB
	 * @param orderItem
	 */
	public void createOrderItem(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	}
	
	/**
	 * Retrieve Order item by its id from H2 DB
	 * @param orderItemId
	 * @return
	 */
	public OrderItem retrieveOrderItem(Integer orderItemId) {
		return orderItemRepository.findById(orderItemId).get();
	}
	
	public OrderItem retrieveOrderItemByProdCode(String prodCode) {
		return orderItemRepository.findByProductCode(prodCode);
	}
	
}
